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


package io.fireflysoftware.core.kycb.core.services.expected.v1;

import org.fireflyframework.core.filters.FilterRequest;
import org.fireflyframework.core.filters.FilterUtils;
import org.fireflyframework.core.queries.PaginationResponse;
import io.fireflysoftware.core.kycb.core.mappers.expected.v1.ExpectedActivityMapper;
import io.fireflysoftware.core.kycb.interfaces.dtos.expected.v1.ExpectedActivityDTO;
import io.fireflysoftware.core.kycb.models.entities.expected.v1.ExpectedActivity;
import io.fireflysoftware.core.kycb.models.repositories.expected.v1.ExpectedActivityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;
import java.util.UUID;

/**
 * Implementation of the expected activity service.
 */
@Service
@Transactional
public class ExpectedActivityServiceImpl implements ExpectedActivityService {

    @Autowired
    private ExpectedActivityRepository repository;

    @Autowired
    private ExpectedActivityMapper mapper;

    @Override
    public Mono<PaginationResponse<ExpectedActivityDTO>> findAll(FilterRequest<ExpectedActivityDTO> filterRequest) {
        return FilterUtils.createFilter(
                ExpectedActivity.class,
                mapper::toDTO
        ).filter(filterRequest);
    }

    @Override
    public Mono<ExpectedActivityDTO> create(ExpectedActivityDTO dto) {
        ExpectedActivity entity = mapper.toEntity(dto);
        return repository.save(entity)
                .map(mapper::toDTO);
    }

    @Override
    public Mono<ExpectedActivityDTO> getById(UUID activityId) {
        return repository.findById(activityId)
                .map(mapper::toDTO);
    }

    @Override
    public Mono<ExpectedActivityDTO> update(UUID activityId, ExpectedActivityDTO dto) {
        return repository.findById(activityId)
                .flatMap(existingEntity -> {
                    ExpectedActivity updatedEntity = mapper.toEntity(dto);
                    updatedEntity.setExpectedActivityId(activityId);
                    // Preserve creation date
                    updatedEntity.setDateCreated(existingEntity.getDateCreated());
                    return repository.save(updatedEntity);
                })
                .map(mapper::toDTO);
    }

    @Override
    public Mono<Void> delete(UUID activityId) {
        return repository.deleteById(activityId);
    }
}