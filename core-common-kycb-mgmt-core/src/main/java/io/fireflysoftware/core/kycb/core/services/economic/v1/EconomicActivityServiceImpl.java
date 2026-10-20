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


package io.fireflysoftware.core.kycb.core.services.economic.v1;

import org.fireflyframework.core.filters.FilterRequest;
import org.fireflyframework.core.filters.FilterUtils;
import org.fireflyframework.core.queries.PaginationResponse;
import io.fireflysoftware.core.kycb.core.mappers.economic.v1.EconomicActivityMapper;
import io.fireflysoftware.core.kycb.interfaces.dtos.economic.v1.EconomicActivityDTO;
import io.fireflysoftware.core.kycb.models.entities.economic.v1.EconomicActivity;
import io.fireflysoftware.core.kycb.models.repositories.economic.v1.EconomicActivityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;
import java.util.UUID;

/**
 * Implementation of the economic activity service.
 */
@Service
@Transactional
public class EconomicActivityServiceImpl implements EconomicActivityService {

    @Autowired
    private EconomicActivityRepository repository;

    @Autowired
    private EconomicActivityMapper mapper;

    @Override
    public Mono<PaginationResponse<EconomicActivityDTO>> findAll(FilterRequest<EconomicActivityDTO> filterRequest) {
        return FilterUtils.createFilter(
                EconomicActivity.class,
                mapper::toDTO
        ).filter(filterRequest);
    }

    @Override
    public Mono<EconomicActivityDTO> create(EconomicActivityDTO dto) {
        EconomicActivity entity = mapper.toEntity(dto);

        // If this is set as primary, we need to ensure no other activity for this party is primary
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
    public Mono<EconomicActivityDTO> getById(UUID activityId) {
        return repository.findById(activityId)
                .map(mapper::toDTO);
    }

    @Override
    public Mono<EconomicActivityDTO> update(UUID activityId, EconomicActivityDTO dto) {
        return repository.findById(activityId)
                .flatMap(existingEntity -> {
                    EconomicActivity updatedEntity = mapper.toEntity(dto);
                    updatedEntity.setEconomicActivityId(activityId);
                    // Preserve creation date
                    updatedEntity.setDateCreated(existingEntity.getDateCreated());

                    // If this is being set as primary, we need to ensure no other activity for this party is primary
                    if (Boolean.TRUE.equals(updatedEntity.getIsPrimary()) && !Boolean.TRUE.equals(existingEntity.getIsPrimary())) {
                        return repository.findByPartyIdAndIsPrimaryTrue(updatedEntity.getPartyId())
                                .flatMap(existingPrimary -> {
                                    existingPrimary.setIsPrimary(false);
                                    return repository.save(existingPrimary);
                                })
                                .then(repository.save(updatedEntity).map(mapper::toDTO))
                                .switchIfEmpty(repository.save(updatedEntity).map(mapper::toDTO));
                    }

                    return repository.save(updatedEntity)
                            .map(mapper::toDTO);
                });
    }

    @Override
    public Mono<Void> delete(UUID activityId) {
        return repository.deleteById(activityId);
    }
}
