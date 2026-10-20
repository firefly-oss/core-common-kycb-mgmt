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


package io.fireflysoftware.core.kycb.core.services.industry.v1;

import org.fireflyframework.core.filters.FilterRequest;
import org.fireflyframework.core.filters.FilterUtils;
import org.fireflyframework.core.queries.PaginationResponse;
import io.fireflysoftware.core.kycb.core.mappers.industry.v1.IndustryRiskMapper;
import io.fireflysoftware.core.kycb.interfaces.dtos.industry.v1.IndustryRiskDTO;
import io.fireflysoftware.core.kycb.models.entities.industry.v1.IndustryRisk;
import io.fireflysoftware.core.kycb.models.repositories.industry.v1.IndustryRiskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Implementation of the industry risk service.
 */
@Service
@Transactional
public class IndustryRiskServiceImpl implements IndustryRiskService {

    @Autowired
    private IndustryRiskRepository repository;

    @Autowired
    private IndustryRiskMapper mapper;

    @Override
    public Mono<PaginationResponse<IndustryRiskDTO>> findAll(FilterRequest<IndustryRiskDTO> filterRequest) {
        return FilterUtils.createFilter(
                IndustryRisk.class,
                mapper::toDTO
        ).filter(filterRequest);
    }

    @Override
    public Mono<IndustryRiskDTO> create(IndustryRiskDTO dto) {
        IndustryRisk entity = mapper.toEntity(dto);
        // Set assessment date to now if not provided
        if (entity.getAssessmentDate() == null) {
            entity.setAssessmentDate(LocalDateTime.now());
        }
        return repository.save(entity)
                .map(mapper::toDTO);
    }

    @Override
    public Mono<IndustryRiskDTO> getById(UUID industryRiskId) {
        return repository.findById(industryRiskId)
                .map(mapper::toDTO);
    }

    @Override
    public Mono<IndustryRiskDTO> update(UUID industryRiskId, IndustryRiskDTO dto) {
        return repository.findById(industryRiskId)
                .flatMap(existingEntity -> {
                    IndustryRisk updatedEntity = mapper.toEntity(dto);
                    updatedEntity.setIndustryRiskId(industryRiskId);
                    // Preserve creation date
                    updatedEntity.setDateCreated(existingEntity.getDateCreated());
                    return repository.save(updatedEntity);
                })
                .map(mapper::toDTO);
    }

    @Override
    public Mono<Void> delete(UUID industryRiskId) {
        return repository.deleteById(industryRiskId);
    }
}