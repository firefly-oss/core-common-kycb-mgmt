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


package io.fireflysoftware.core.kycb.core.services.compliance.v1;

import org.fireflyframework.core.filters.FilterRequest;
import org.fireflyframework.core.filters.FilterUtils;
import org.fireflyframework.core.queries.PaginationResponse;
import io.fireflysoftware.core.kycb.core.mappers.compliance.v1.ComplianceCaseMapper;
import io.fireflysoftware.core.kycb.interfaces.dtos.compliance.v1.ComplianceCaseDTO;
import io.fireflysoftware.core.kycb.models.entities.compliance.v1.ComplianceCase;
import io.fireflysoftware.core.kycb.models.repositories.compliance.v1.ComplianceCaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;
import java.util.UUID;

/**
 * Implementation of the compliance case service.
 */
@Service
@Transactional
public class ComplianceCaseServiceImpl implements ComplianceCaseService {

    @Autowired
    private ComplianceCaseRepository repository;

    @Autowired
    private ComplianceCaseMapper mapper;

    @Override
    public Mono<PaginationResponse<ComplianceCaseDTO>> findAll(FilterRequest<ComplianceCaseDTO> filterRequest) {
        return FilterUtils.createFilter(
                ComplianceCase.class,
                mapper::toDTO
        ).filter(filterRequest);
    }

    @Override
    public Mono<ComplianceCaseDTO> create(ComplianceCaseDTO dto) {
        ComplianceCase entity = mapper.toEntity(dto);
        return repository.save(entity)
                .map(mapper::toDTO);
    }

    @Override
    public Mono<ComplianceCaseDTO> getById(UUID caseId) {
        return repository.findById(caseId)
                .map(mapper::toDTO);
    }

    @Override
    public Mono<ComplianceCaseDTO> update(UUID caseId, ComplianceCaseDTO dto) {
        return repository.findById(caseId)
                .flatMap(existingEntity -> {
                    ComplianceCase updatedEntity = mapper.toEntity(dto);
                    updatedEntity.setComplianceCaseId(caseId);
                    // Preserve creation date
                    updatedEntity.setDateCreated(existingEntity.getDateCreated());
                    return repository.save(updatedEntity);
                })
                .map(mapper::toDTO);
    }

    @Override
    public Mono<Void> delete(UUID caseId) {
        return repository.deleteById(caseId);
    }
}
