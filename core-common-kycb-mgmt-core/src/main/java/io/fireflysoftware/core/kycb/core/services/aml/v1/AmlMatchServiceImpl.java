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
import io.fireflysoftware.core.kycb.core.mappers.aml.v1.AmlMatchMapper;
import io.fireflysoftware.core.kycb.interfaces.dtos.aml.v1.AmlMatchDTO;
import io.fireflysoftware.core.kycb.models.entities.aml.v1.AmlMatch;
import io.fireflysoftware.core.kycb.models.repositories.aml.v1.AmlMatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;
import java.util.UUID;

/**
 * Implementation of the AML match service.
 */
@Service
@Transactional
public class AmlMatchServiceImpl implements AmlMatchService {

    @Autowired
    private AmlMatchRepository repository;

    @Autowired
    private AmlMatchMapper mapper;

    @Override
    public Mono<PaginationResponse<AmlMatchDTO>> findAll(FilterRequest<AmlMatchDTO> filterRequest) {
        return FilterUtils.createFilter(
                AmlMatch.class,
                mapper::toDTO
        ).filter(filterRequest);
    }

    @Override
    public Mono<AmlMatchDTO> create(AmlMatchDTO dto) {
        AmlMatch entity = mapper.toEntity(dto);
        return repository.save(entity)
                .map(mapper::toDTO);
    }

    @Override
    public Mono<AmlMatchDTO> getById(UUID amlMatchId) {
        return repository.findById(amlMatchId)
                .map(mapper::toDTO);
    }

    @Override
    public Mono<AmlMatchDTO> update(UUID amlMatchId, AmlMatchDTO dto) {
        return repository.findById(amlMatchId)
                .flatMap(existingEntity -> {
                    AmlMatch updatedEntity = mapper.toEntity(dto);
                    updatedEntity.setAmlMatchId(amlMatchId);
                    // Preserve creation date
                    updatedEntity.setDateCreated(existingEntity.getDateCreated());
                    return repository.save(updatedEntity);
                })
                .map(mapper::toDTO);
    }

    @Override
    public Mono<Void> delete(UUID amlMatchId) {
        return repository.deleteById(amlMatchId);
    }
}
