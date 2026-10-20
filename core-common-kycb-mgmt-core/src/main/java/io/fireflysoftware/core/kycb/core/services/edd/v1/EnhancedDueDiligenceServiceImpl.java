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


package io.fireflysoftware.core.kycb.core.services.edd.v1;

import org.fireflyframework.core.filters.FilterRequest;
import org.fireflyframework.core.filters.FilterUtils;
import org.fireflyframework.core.queries.PaginationResponse;
import io.fireflysoftware.core.kycb.core.mappers.edd.v1.EnhancedDueDiligenceMapper;
import io.fireflysoftware.core.kycb.interfaces.dtos.edd.v1.EnhancedDueDiligenceDTO;
import io.fireflysoftware.core.kycb.interfaces.enums.edd.v1.EddStatusEnum;
import io.fireflysoftware.core.kycb.models.entities.edd.v1.EnhancedDueDiligence;
import io.fireflysoftware.core.kycb.models.repositories.edd.v1.EnhancedDueDiligenceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;
import java.util.UUID;

/**
 * Implementation of the enhanced due diligence service.
 */
@Service
@Transactional
public class EnhancedDueDiligenceServiceImpl implements EnhancedDueDiligenceService {

    @Autowired
    private EnhancedDueDiligenceRepository repository;

    @Autowired
    private EnhancedDueDiligenceMapper mapper;

    @Override
    public Mono<PaginationResponse<EnhancedDueDiligenceDTO>> findAll(FilterRequest<EnhancedDueDiligenceDTO> filterRequest) {
        return FilterUtils.createFilter(
                EnhancedDueDiligence.class,
                mapper::toDTO
        ).filter(filterRequest);
    }

    @Override
    public Mono<EnhancedDueDiligenceDTO> create(EnhancedDueDiligenceDTO dto) {
        EnhancedDueDiligence entity = mapper.toEntity(dto);
        // Set default status to PENDING if not provided
        if (entity.getEddStatus() == null) {
            entity.setEddStatus(EddStatusEnum.PENDING);
        }
        return repository.save(entity)
                .map(mapper::toDTO);
    }

    @Override
    public Mono<EnhancedDueDiligenceDTO> getById(UUID eddId) {
        return repository.findById(eddId)
                .map(mapper::toDTO);
    }

    @Override
    public Mono<EnhancedDueDiligenceDTO> update(UUID eddId, EnhancedDueDiligenceDTO dto) {
        return repository.findById(eddId)
                .flatMap(existingEntity -> {
                    EnhancedDueDiligence updatedEntity = mapper.toEntity(dto);
                    updatedEntity.setEddId(eddId);
                    // Preserve creation date
                    updatedEntity.setDateCreated(existingEntity.getDateCreated());
                    return repository.save(updatedEntity);
                })
                .map(mapper::toDTO);
    }

    @Override
    public Mono<Void> delete(UUID eddId) {
        return repository.deleteById(eddId);
    }
}
