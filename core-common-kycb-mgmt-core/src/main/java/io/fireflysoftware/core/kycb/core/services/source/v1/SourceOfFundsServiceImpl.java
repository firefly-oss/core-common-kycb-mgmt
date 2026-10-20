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


package io.fireflysoftware.core.kycb.core.services.source.v1;

import org.fireflyframework.core.filters.FilterRequest;
import org.fireflyframework.core.filters.FilterUtils;
import org.fireflyframework.core.queries.PaginationResponse;
import io.fireflysoftware.core.kycb.core.mappers.source.v1.SourceOfFundsMapper;
import io.fireflysoftware.core.kycb.interfaces.dtos.source.v1.SourceOfFundsDTO;
import io.fireflysoftware.core.kycb.models.entities.source.v1.SourceOfFunds;
import io.fireflysoftware.core.kycb.models.repositories.source.v1.SourceOfFundsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;
import java.util.UUID;

/**
 * Implementation of the source of funds service.
 */
@Service
@Transactional
public class SourceOfFundsServiceImpl implements SourceOfFundsService {

    @Autowired
    private SourceOfFundsRepository repository;

    @Autowired
    private SourceOfFundsMapper mapper;

    @Override
    public Mono<PaginationResponse<SourceOfFundsDTO>> findAll(FilterRequest<SourceOfFundsDTO> filterRequest) {
        return FilterUtils.createFilter(
                SourceOfFunds.class,
                mapper::toDTO
        ).filter(filterRequest);
    }

    @Override
    public Mono<SourceOfFundsDTO> create(SourceOfFundsDTO dto) {
        SourceOfFunds entity = mapper.toEntity(dto);
        return repository.save(entity)
                .map(mapper::toDTO);
    }

    @Override
    public Mono<SourceOfFundsDTO> getById(UUID sourceId) {
        return repository.findById(sourceId)
                .map(mapper::toDTO);
    }

    @Override
    public Mono<SourceOfFundsDTO> update(UUID sourceId, SourceOfFundsDTO dto) {
        return repository.findById(sourceId)
                .flatMap(existingEntity -> {
                    SourceOfFunds updatedEntity = mapper.toEntity(dto);
                    updatedEntity.setSourceOfFundsId(sourceId);
                    // Preserve creation date
                    updatedEntity.setDateCreated(existingEntity.getDateCreated());
                    // Preserve verification data if not changing
                    if (existingEntity.getIsVerified() && updatedEntity.getIsVerified() == null) {
                        updatedEntity.setIsVerified(existingEntity.getIsVerified());
                        updatedEntity.setVerificationDate(existingEntity.getVerificationDate());
                        updatedEntity.setVerificationMethod(existingEntity.getVerificationMethod());
                    }
                    return repository.save(updatedEntity);
                })
                .map(mapper::toDTO);
    }

    @Override
    public Mono<Void> delete(UUID sourceId) {
        return repository.deleteById(sourceId);
    }
}
