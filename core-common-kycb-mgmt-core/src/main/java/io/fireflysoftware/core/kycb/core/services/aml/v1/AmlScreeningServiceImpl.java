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


package io.fireflysoftware.core.kycb.core.services.aml.v1;

import org.fireflyframework.core.filters.FilterRequest;
import org.fireflyframework.core.filters.FilterUtils;
import org.fireflyframework.core.queries.PaginationResponse;
import io.fireflysoftware.core.kycb.core.mappers.aml.v1.AmlScreeningMapper;
import io.fireflysoftware.core.kycb.interfaces.dtos.aml.v1.AmlScreeningDTO;
import io.fireflysoftware.core.kycb.models.entities.aml.v1.AmlScreening;
import io.fireflysoftware.core.kycb.models.repositories.aml.v1.AmlScreeningRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;
import java.util.UUID;

/**
 * Implementation of the AML screening service.
 */
@Service
@Transactional
public class AmlScreeningServiceImpl implements AmlScreeningService {

    @Autowired
    private AmlScreeningRepository repository;

    @Autowired
    private AmlScreeningMapper mapper;

    @Override
    public Mono<PaginationResponse<AmlScreeningDTO>> findAll(FilterRequest<AmlScreeningDTO> filterRequest) {
        return FilterUtils.createFilter(
                AmlScreening.class,
                mapper::toDTO
        ).filter(filterRequest);
    }

    @Override
    public Mono<AmlScreeningDTO> create(AmlScreeningDTO dto) {
        AmlScreening entity = mapper.toEntity(dto);
        return repository.save(entity)
                .map(mapper::toDTO);
    }

    @Override
    public Mono<AmlScreeningDTO> getById(UUID amlScreeningId) {
        return repository.findById(amlScreeningId)
                .map(mapper::toDTO);
    }

    @Override
    public Mono<AmlScreeningDTO> update(UUID amlScreeningId, AmlScreeningDTO dto) {
        return repository.findById(amlScreeningId)
                .flatMap(existingEntity -> {
                    AmlScreening updatedEntity = mapper.toEntity(dto);
                    updatedEntity.setAmlScreeningId(amlScreeningId);
                    // Preserve creation date
                    updatedEntity.setDateCreated(existingEntity.getDateCreated());
                    return repository.save(updatedEntity);
                })
                .map(mapper::toDTO);
    }

    @Override
    public Mono<Void> delete(UUID amlScreeningId) {
        return repository.deleteById(amlScreeningId);
    }
}
