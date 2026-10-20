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

import java.time.LocalDateTime;
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
        // Set start date to now if not provided
        if (entity.getStartDate() == null) {
            entity.setStartDate(LocalDateTime.now());
        }
        return repository.save(entity)
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
                    // Preserve creation date
                    updatedEntity.setDateCreated(existingEntity.getDateCreated());
                    // Preserve verification data if not changing
                    if (existingEntity.getIsVerified() && updatedEntity.getIsVerified() == null) {
                        updatedEntity.setIsVerified(existingEntity.getIsVerified());
                        updatedEntity.setVerificationDate(existingEntity.getVerificationDate());
                    }
                    return repository.save(updatedEntity);
                })
                .map(mapper::toDTO);
    }

    @Override
    public Mono<Void> delete(UUID structureId) {
        return repository.deleteById(structureId);
    }
}
