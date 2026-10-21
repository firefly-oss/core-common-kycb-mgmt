/*
 * Copyright 2025 Firefly Software Foundation
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


package io.fireflysoftware.core.kycb.core.services.corporate.v1;

import org.fireflyframework.core.filters.FilterRequest;
import org.fireflyframework.core.filters.FilterUtils;
import org.fireflyframework.core.queries.PaginationResponse;
import io.fireflysoftware.core.kycb.core.mappers.corporate.v1.CorporateStructureMapper;
import io.fireflysoftware.core.kycb.interfaces.dtos.corporate.v1.CorporateStructureDTO;
import io.fireflysoftware.core.kycb.models.entities.corporate.v1.CorporateStructure;
import io.fireflysoftware.core.kycb.models.repositories.corporate.v1.CorporateStructureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * Implementation of the corporate structure service.
 */
@Service
@Transactional
public class CorporateStructureServiceImpl implements CorporateStructureService {

    @Autowired
    private CorporateStructureRepository repository;

    @Autowired
    private CorporateStructureMapper mapper;

    @Override
    public Mono<PaginationResponse<CorporateStructureDTO>> findAll(FilterRequest<CorporateStructureDTO> filterRequest) {
        return FilterUtils.createFilter(
                CorporateStructure.class,
                mapper::toDTO
        ).filter(filterRequest);
    }

    @Override
    public Mono<CorporateStructureDTO> create(CorporateStructureDTO dto) {
        CorporateStructure entity = mapper.toEntity(dto);
        if (entity.getStartDate() == null) {
            entity.setStartDate(LocalDateTime.now());
        }
        return validateEdge(entity)
                .flatMap(repository::save)
                .map(mapper::toDTO);
    }

    @Override
    public Mono<CorporateStructureDTO> getById(UUID structureId) {
        return repository.findById(structureId)
                .map(mapper::toDTO);
    }

    @Override
    public Mono<CorporateStructureDTO> update(UUID structureId, CorporateStructureDTO dto) {
        return repository.findById(structureId)
                .flatMap(existingEntity -> {
                    CorporateStructure updatedEntity = mapper.toEntity(dto);
                    updatedEntity.setCorporateStructureId(structureId);
                    if (existingEntity.getPartyId() != null
                            && !existingEntity.getPartyId().equals(updatedEntity.getPartyId())) {
                        return Mono.error(new IllegalStateException(
                                "Structure relationship " + structureId + " does not belong to party "
                                        + updatedEntity.getPartyId()));
                    }
                    updatedEntity.setDateCreated(existingEntity.getDateCreated());
                    if (existingEntity.getIsVerified() != null && existingEntity.getIsVerified()
                            && updatedEntity.getIsVerified() == null) {
                        updatedEntity.setIsVerified(existingEntity.getIsVerified());
                        updatedEntity.setVerificationDate(existingEntity.getVerificationDate());
                    }
                    return validateEdge(updatedEntity).flatMap(repository::save);
                })
                .map(mapper::toDTO);
    }

    @Override
    public Mono<Void> delete(UUID structureId) {
        return repository.deleteById(structureId);
    }

    private Mono<CorporateStructure> validateEdge(CorporateStructure edge) {
        BigDecimal percentage = edge.getOwnershipPercentage();
        if (percentage != null
                && (percentage.signum() < 0 || percentage.compareTo(new BigDecimal("100")) > 0)) {
            return Mono.error(new IllegalStateException(
                    "Ownership percentage must be between 0 and 100"));
        }
        if (edge.getPartyId() == null || edge.getParentEntityId() == null) {
            return Mono.error(new IllegalStateException(
                    "A structure edge needs the party and its direct parent"));
        }
        if (edge.getPartyId().equals(edge.getParentEntityId())) {
            return Mono.error(new IllegalStateException("A party cannot be its own parent"));
        }
        return rejectsCycle(edge.getParentEntityId(), edge.getPartyId(), new HashSet<>())
                .thenReturn(edge);
    }

    private Mono<Void> rejectsCycle(UUID current, UUID childId, Set<UUID> seen) {
        if (current == null) {
            return Mono.empty();
        }
        if (current.equals(childId) || !seen.add(current)) {
            return Mono.error(new IllegalStateException("Corporate structure cannot contain a cycle"));
        }
        return repository.findByPartyId(current)
                .map(CorporateStructure::getParentEntityId)
                .concatMap(parent -> rejectsCycle(parent, childId, seen))
                .then();
    }
}
