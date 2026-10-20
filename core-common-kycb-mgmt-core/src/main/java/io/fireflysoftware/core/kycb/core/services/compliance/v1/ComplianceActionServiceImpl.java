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
import io.fireflysoftware.core.kycb.core.mappers.compliance.v1.ComplianceActionMapper;
import io.fireflysoftware.core.kycb.interfaces.dtos.compliance.v1.ComplianceActionDTO;
import io.fireflysoftware.core.kycb.models.entities.compliance.v1.ComplianceAction;
import io.fireflysoftware.core.kycb.models.repositories.compliance.v1.ComplianceActionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;
import java.util.UUID;

/**
 * Implementation of the compliance action service.
 */
@Service
@Transactional
public class ComplianceActionServiceImpl implements ComplianceActionService {

    @Autowired
    private ComplianceActionRepository repository;

    @Autowired
    private ComplianceActionMapper mapper;

    @Override
    public Mono<PaginationResponse<ComplianceActionDTO>> findAll(FilterRequest<ComplianceActionDTO> filterRequest) {
        return FilterUtils.createFilter(
                ComplianceAction.class,
                mapper::toDTO
        ).filter(filterRequest);
    }

    @Override
    public Mono<ComplianceActionDTO> create(ComplianceActionDTO dto) {
        ComplianceAction entity = mapper.toEntity(dto);
        return repository.save(entity)
                .map(mapper::toDTO);
    }

    @Override
    public Mono<ComplianceActionDTO> getById(UUID actionId) {
        return repository.findById(actionId)
                .map(mapper::toDTO);
    }

    @Override
    public Mono<ComplianceActionDTO> update(UUID actionId, ComplianceActionDTO dto) {
        return repository.findById(actionId)
                .flatMap(existingEntity -> {
                    ComplianceAction updatedEntity = mapper.toEntity(dto);
                    updatedEntity.setComplianceActionId(actionId);
                    // Preserve creation date
                    updatedEntity.setDateCreated(existingEntity.getDateCreated());
                    return repository.save(updatedEntity);
                })
                .map(mapper::toDTO);
    }

    @Override
    public Mono<Void> delete(UUID actionId) {
        return repository.deleteById(actionId);
    }
}
