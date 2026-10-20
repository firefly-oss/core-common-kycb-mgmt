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


package io.fireflysoftware.core.kycb.core.services.location.v1;

import org.fireflyframework.core.filters.FilterRequest;
import org.fireflyframework.core.filters.FilterUtils;
import org.fireflyframework.core.queries.PaginationResponse;
import io.fireflysoftware.core.kycb.core.mappers.location.v1.BusinessLocationMapper;
import io.fireflysoftware.core.kycb.interfaces.dtos.location.v1.BusinessLocationDTO;
import io.fireflysoftware.core.kycb.models.entities.location.v1.BusinessLocation;
import io.fireflysoftware.core.kycb.models.repositories.location.v1.BusinessLocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;
import java.util.UUID;

/**
 * Implementation of the business location service.
 */
@Service
@Transactional
public class BusinessLocationServiceImpl implements BusinessLocationService {

    @Autowired
    private BusinessLocationRepository repository;

    @Autowired
    private BusinessLocationMapper mapper;

    @Override
    public Mono<PaginationResponse<BusinessLocationDTO>> findAll(FilterRequest<BusinessLocationDTO> filterRequest) {
        return FilterUtils.createFilter(
                BusinessLocation.class,
                mapper::toDTO
        ).filter(filterRequest);
    }

    @Override
    public Mono<BusinessLocationDTO> create(BusinessLocationDTO dto) {
        BusinessLocation entity = mapper.toEntity(dto);

        // If this is set as primary, we need to ensure no other location for this party is primary
        if (Boolean.TRUE.equals(entity.getIsPrimary())) {
            return repository.findByPartyIdAndIsPrimaryTrue(entity.getPartyId())
                    .flatMap(existingPrimary -> {
                        existingPrimary.setIsPrimary(false);
                        return repository.save(existingPrimary);
                    })
                    .then(repository.save(entity).map(mapper::toDTO))
                    .switchIfEmpty(repository.save(entity).map(mapper::toDTO));
        }

        return repository.save(entity)
                .map(mapper::toDTO);
    }

    @Override
    public Mono<BusinessLocationDTO> getById(UUID locationId) {
        return repository.findById(locationId)
                .map(mapper::toDTO);
    }

    @Override
    public Mono<BusinessLocationDTO> update(UUID locationId, BusinessLocationDTO dto) {
        return repository.findById(locationId)
                .flatMap(existingEntity -> {
                    BusinessLocation updatedEntity = mapper.toEntity(dto);
                    updatedEntity.setBusinessLocationId(locationId);
                    // Preserve creation date
                    updatedEntity.setDateCreated(existingEntity.getDateCreated());

                    // If this is being set as primary, we need to ensure no other location for this party is primary
                    if (Boolean.TRUE.equals(updatedEntity.getIsPrimary()) && !Boolean.TRUE.equals(existingEntity.getIsPrimary())) {
                        return repository.findByPartyIdAndIsPrimaryTrue(updatedEntity.getPartyId())
                                .flatMap(existingPrimary -> {
                                    existingPrimary.setIsPrimary(false);
                                    return repository.save(existingPrimary);
                                })
                                .then(repository.save(updatedEntity).map(mapper::toDTO))
                                .switchIfEmpty(repository.save(updatedEntity).map(mapper::toDTO));
                    }

                    // Preserve verification data if not changing
                    if (existingEntity.getIsVerified() && updatedEntity.getIsVerified() == null) {
                        updatedEntity.setIsVerified(existingEntity.getIsVerified());
                        updatedEntity.setVerificationDate(existingEntity.getVerificationDate());
                        updatedEntity.setVerificationMethod(existingEntity.getVerificationMethod());
                    }

                    return repository.save(updatedEntity)
                            .map(mapper::toDTO);
                });
    }

    @Override
    public Mono<Void> delete(UUID locationId) {
        return repository.deleteById(locationId);
    }
}
