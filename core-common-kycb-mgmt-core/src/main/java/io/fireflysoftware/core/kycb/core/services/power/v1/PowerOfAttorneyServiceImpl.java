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


package io.fireflysoftware.core.kycb.core.services.power.v1;

import org.fireflyframework.core.filters.FilterRequest;
import org.fireflyframework.core.filters.FilterUtils;
import org.fireflyframework.core.queries.PaginationResponse;
import io.fireflysoftware.core.kycb.core.mappers.power.v1.PowerOfAttorneyMapper;
import io.fireflysoftware.core.kycb.interfaces.dtos.power.v1.PowerOfAttorneyDTO;
import io.fireflysoftware.core.kycb.models.entities.power.v1.PowerOfAttorney;
import io.fireflysoftware.core.kycb.models.repositories.power.v1.PowerOfAttorneyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;
import java.util.UUID;

/**
 * Implementation of the power of attorney service.
 */
@Service
@Transactional
public class PowerOfAttorneyServiceImpl implements PowerOfAttorneyService {

    @Autowired
    private PowerOfAttorneyRepository repository;

    @Autowired
    private PowerOfAttorneyMapper mapper;

    @Override
    public Mono<PaginationResponse<PowerOfAttorneyDTO>> findAll(FilterRequest<PowerOfAttorneyDTO> filterRequest) {
        return FilterUtils.createFilter(
                PowerOfAttorney.class,
                mapper::toDTO
        ).filter(filterRequest);
    }

    @Override
    public Mono<PowerOfAttorneyDTO> create(PowerOfAttorneyDTO dto) {
        PowerOfAttorney entity = mapper.toEntity(dto);
        return repository.save(entity)
                .map(mapper::toDTO);
    }

    @Override
    public Mono<PowerOfAttorneyDTO> getById(UUID powerId) {
        return repository.findById(powerId)
                .map(mapper::toDTO);
    }

    @Override
    public Mono<PowerOfAttorneyDTO> update(UUID powerId, PowerOfAttorneyDTO dto) {
        return repository.findById(powerId)
                .flatMap(existingEntity -> {
                    PowerOfAttorney updatedEntity = mapper.toEntity(dto);
                    updatedEntity.setPowerOfAttorneyId(powerId);
                    // Preserve creation date
                    updatedEntity.setDateCreated(existingEntity.getDateCreated());
                    // Preserve verification data if not changing
                    if (existingEntity.getIsVerified() && updatedEntity.getIsVerified() == null) {
                        updatedEntity.setIsVerified(existingEntity.getIsVerified());
                        updatedEntity.setVerificationDate(existingEntity.getVerificationDate());
                        updatedEntity.setVerificationMethod(existingEntity.getVerificationMethod());
                        updatedEntity.setVerifyingLegalCounsel(existingEntity.getVerifyingLegalCounsel());
                    }
                    return repository.save(updatedEntity);
                })
                .map(mapper::toDTO);
    }

    @Override
    public Mono<Void> delete(UUID powerId) {
        return repository.deleteById(powerId);
    }
}
