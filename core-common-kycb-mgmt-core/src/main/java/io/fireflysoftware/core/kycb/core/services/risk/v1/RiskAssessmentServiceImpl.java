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


package io.fireflysoftware.core.kycb.core.services.risk.v1;

import org.fireflyframework.core.filters.FilterRequest;
import org.fireflyframework.core.filters.FilterUtils;
import org.fireflyframework.core.queries.PaginationResponse;
import io.fireflysoftware.core.kycb.core.mappers.risk.v1.RiskAssessmentMapper;
import io.fireflysoftware.core.kycb.interfaces.dtos.risk.v1.RiskAssessmentDTO;
import io.fireflysoftware.core.kycb.models.entities.risk.v1.RiskAssessment;
import io.fireflysoftware.core.kycb.models.repositories.risk.v1.RiskAssessmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;
import java.util.UUID;

/**
 * Implementation of the risk assessment service.
 */
@Service
@Transactional
public class RiskAssessmentServiceImpl implements RiskAssessmentService {

    @Autowired
    private RiskAssessmentRepository repository;

    @Autowired
    private RiskAssessmentMapper mapper;

    @Override
    public Mono<PaginationResponse<RiskAssessmentDTO>> findAll(FilterRequest<RiskAssessmentDTO> filterRequest) {
        return FilterUtils.createFilter(
                RiskAssessment.class,
                mapper::toDTO
        ).filter(filterRequest);
    }


    @Override
    public Mono<RiskAssessmentDTO> create(RiskAssessmentDTO dto) {
        RiskAssessment entity = mapper.toEntity(dto);
        return repository.save(entity)
                .map(mapper::toDTO);
    }

    @Override
    public Mono<RiskAssessmentDTO> getById(UUID riskAssessmentId) {
        return repository.findById(riskAssessmentId)
                .map(mapper::toDTO);
    }

    @Override
    public Mono<RiskAssessmentDTO> update(UUID riskAssessmentId, RiskAssessmentDTO dto) {
        return repository.findById(riskAssessmentId)
                .flatMap(existingEntity -> {
                    RiskAssessment updatedEntity = mapper.toEntity(dto);
                    updatedEntity.setRiskAssessmentId(riskAssessmentId);
                    // Preserve creation date
                    updatedEntity.setDateCreated(existingEntity.getDateCreated());
                    return repository.save(updatedEntity);
                })
                .map(mapper::toDTO);
    }

    @Override
    public Mono<Void> delete(UUID riskAssessmentId) {
        return repository.deleteById(riskAssessmentId);
    }
}
