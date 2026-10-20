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


package io.fireflysoftware.core.kycb.core.services.business.v1;

import org.fireflyframework.core.filters.FilterRequest;
import org.fireflyframework.core.filters.FilterUtils;
import org.fireflyframework.core.queries.PaginationResponse;
import io.fireflysoftware.core.kycb.core.mappers.business.v1.BusinessProfileMapper;
import io.fireflysoftware.core.kycb.interfaces.dtos.business.v1.BusinessProfileDTO;
import io.fireflysoftware.core.kycb.models.entities.business.v1.BusinessProfile;
import io.fireflysoftware.core.kycb.models.repositories.business.v1.BusinessProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;
import java.util.UUID;

/**
 * Implementation of the business profile service.
 */
@Service
@Transactional
public class BusinessProfileServiceImpl implements BusinessProfileService {

    @Autowired
    private BusinessProfileRepository repository;

    @Autowired
    private BusinessProfileMapper mapper;

    @Override
    public Mono<PaginationResponse<BusinessProfileDTO>> findAll(FilterRequest<BusinessProfileDTO> filterRequest) {
        return FilterUtils.createFilter(
                BusinessProfile.class,
                mapper::toDTO
        ).filter(filterRequest);
    }

    @Override
    public Mono<BusinessProfileDTO> create(BusinessProfileDTO dto) {
        BusinessProfile entity = mapper.toEntity(dto);
        return repository.save(entity)
                .map(mapper::toDTO);
    }

    @Override
    public Mono<BusinessProfileDTO> getById(UUID businessProfileId) {
        return repository.findById(businessProfileId)
                .map(mapper::toDTO);
    }

    @Override
    public Mono<BusinessProfileDTO> update(UUID businessProfileId, BusinessProfileDTO dto) {
        return repository.findById(businessProfileId)
                .flatMap(existingEntity -> {
                    BusinessProfile updatedEntity = mapper.toEntity(dto);
                    updatedEntity.setBusinessProfileId(businessProfileId);
                    // Preserve creation date
                    updatedEntity.setDateCreated(existingEntity.getDateCreated());
                    return repository.save(updatedEntity);
                })
                .map(mapper::toDTO);
    }

    @Override
    public Mono<Void> delete(UUID businessProfileId) {
        return repository.deleteById(businessProfileId);
    }
}
