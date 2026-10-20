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


package io.fireflysoftware.core.kycb.core.services.kyb.v1;

import org.fireflyframework.core.filters.FilterRequest;
import org.fireflyframework.core.filters.FilterUtils;
import org.fireflyframework.core.queries.PaginationRequest;
import org.fireflyframework.core.queries.PaginationResponse;
import io.fireflysoftware.core.kycb.core.mappers.kyb.v1.KybVerificationMapper;
import io.fireflysoftware.core.kycb.interfaces.dtos.kyb.v1.KybVerificationDTO;
import io.fireflysoftware.core.kycb.interfaces.enums.verification.v1.VerificationStatusEnum;
import io.fireflysoftware.core.kycb.models.entities.kyb.v1.KybVerification;
import io.fireflysoftware.core.kycb.models.repositories.kyb.v1.KybVerificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@Transactional
public class KybVerificationServiceImpl implements KybVerificationService {

    @Autowired
    private KybVerificationRepository repository;

    @Autowired
    private KybVerificationMapper mapper;

    @Override
    public Mono<PaginationResponse<KybVerificationDTO>> findAll(FilterRequest<KybVerificationDTO> filterRequest) {
        return FilterUtils.createFilter(
                KybVerification.class,
                mapper::toDTO
        ).filter(filterRequest);
    }

    @Override
    public Mono<KybVerificationDTO> create(KybVerificationDTO dto) {
        KybVerification entity = mapper.toEntity(dto);
        return repository.save(entity)
                .map(mapper::toDTO);
    }

    @Override
    public Mono<KybVerificationDTO> getById(UUID kybVerificationId) {
        return repository.findById(kybVerificationId)
                .map(mapper::toDTO);
    }

    @Override
    public Mono<KybVerificationDTO> update(UUID kybVerificationId, KybVerificationDTO dto) {
        return repository.findById(kybVerificationId)
                .flatMap(existingEntity -> {
                    KybVerification updatedEntity = mapper.toEntity(dto);
                    updatedEntity.setKybVerificationId(kybVerificationId);
                    // Preserve creation date
                    updatedEntity.setDateCreated(existingEntity.getDateCreated());
                    return repository.save(updatedEntity);
                })
                .map(mapper::toDTO);
    }

    @Override
    public Mono<Void> delete(UUID kybVerificationId) {
        return repository.deleteById(kybVerificationId);
    }

}