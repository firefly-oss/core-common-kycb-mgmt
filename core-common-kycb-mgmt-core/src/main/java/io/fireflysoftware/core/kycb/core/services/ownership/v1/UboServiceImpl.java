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


package io.fireflysoftware.core.kycb.core.services.ownership.v1;

import org.fireflyframework.core.filters.FilterRequest;
import org.fireflyframework.core.filters.FilterUtils;
import org.fireflyframework.core.queries.PaginationResponse;
import io.fireflysoftware.core.kycb.core.mappers.ownership.v1.UboMapper;
import io.fireflysoftware.core.kycb.interfaces.dtos.ownership.v1.UboDTO;
import io.fireflysoftware.core.kycb.interfaces.enums.ownership.v1.OwnershipTypeEnum;
import io.fireflysoftware.core.kycb.models.entities.ownership.v1.Ubo;
import io.fireflysoftware.core.kycb.models.repositories.ownership.v1.UboRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Implementation of the UBO service.
 */
@Service
@Transactional
public class UboServiceImpl implements UboService {

    @Autowired
    private UboRepository repository;

    @Autowired
    private UboMapper mapper;

    @Override
    public Mono<PaginationResponse<UboDTO>> findAll(FilterRequest<UboDTO> filterRequest) {
        return FilterUtils.createFilter(
                Ubo.class,
                mapper::toDTO
        ).filter(filterRequest);
    }

    @Override
    public Mono<UboDTO> create(UboDTO dto) {
        Ubo entity = mapper.toEntity(dto);
        // Set start date to now if not provided
        if (entity.getStartDate() == null) {
            entity.setStartDate(LocalDateTime.now());
        }
        return repository.save(entity)
                .map(mapper::toDTO);
    }

    @Override
    public Mono<UboDTO> getById(UUID uboId) {
        return repository.findById(uboId)
                .map(mapper::toDTO);
    }

    @Override
    public Mono<UboDTO> update(UUID uboId, UboDTO dto) {
        return repository.findById(uboId)
                .flatMap(existingEntity -> {
                    Ubo updatedEntity = mapper.toEntity(dto);
                    updatedEntity.setUboId(uboId);
                    // Preserve creation date
                    updatedEntity.setDateCreated(existingEntity.getDateCreated());
                    // Preserve verification data if not changing
                    if (existingEntity.getIsVerified() && updatedEntity.getIsVerified() == null) {
                        updatedEntity.setIsVerified(existingEntity.getIsVerified());
                        updatedEntity.setVerificationDate(existingEntity.getVerificationDate());
                        updatedEntity.setVerificationMethod(existingEntity.getVerificationMethod());
                        updatedEntity.setBeneficialOwnershipDocument(existingEntity.getBeneficialOwnershipDocument());
                    }
                    return repository.save(updatedEntity);
                })
                .map(mapper::toDTO);
    }

    @Override
    public Mono<Void> delete(UUID uboId) {
        return repository.deleteById(uboId);
    }
}