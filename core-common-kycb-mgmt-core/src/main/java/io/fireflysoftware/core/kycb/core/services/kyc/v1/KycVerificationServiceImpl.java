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


package io.fireflysoftware.core.kycb.core.services.kyc.v1;

import org.fireflyframework.core.filters.FilterRequest;
import org.fireflyframework.core.filters.FilterUtils;
import org.fireflyframework.core.queries.PaginationRequest;
import org.fireflyframework.core.queries.PaginationResponse;
import io.fireflysoftware.core.kycb.core.mappers.kyc.v1.KycVerificationMapper;
import io.fireflysoftware.core.kycb.interfaces.dtos.kyc.v1.KycVerificationDTO;
import io.fireflysoftware.core.kycb.interfaces.enums.verification.v1.VerificationStatusEnum;
import io.fireflysoftware.core.kycb.models.entities.kyc.v1.KycVerification;
import io.fireflysoftware.core.kycb.models.repositories.kyc.v1.KycVerificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@Transactional
public class KycVerificationServiceImpl implements KycVerificationService {

    @Autowired
    private KycVerificationRepository repository;

    @Autowired
    private KycVerificationMapper mapper;

    @Override
    public Mono<PaginationResponse<KycVerificationDTO>> findAll(FilterRequest<KycVerificationDTO> filterRequest) {
        return FilterUtils.createFilter(
                KycVerification.class,
                mapper::toDTO
        ).filter(filterRequest);
    }

    @Override
    public Mono<KycVerificationDTO> create(KycVerificationDTO dto) {
        KycVerification entity = mapper.toEntity(dto);
        return repository.save(entity)
                .map(mapper::toDTO);
    }

    @Override
    public Mono<KycVerificationDTO> getById(UUID kycVerificationId) {
        return repository.findById(kycVerificationId)
                .map(mapper::toDTO);
    }

    @Override
    public Mono<KycVerificationDTO> update(UUID kycVerificationId, KycVerificationDTO dto) {
        return repository.findById(kycVerificationId)
                .flatMap(existingEntity -> {
                    KycVerification updatedEntity = mapper.toEntity(dto);
                    updatedEntity.setKycVerificationId(kycVerificationId);
                    // Preserve creation date
                    updatedEntity.setDateCreated(existingEntity.getDateCreated());
                    return repository.save(updatedEntity);
                })
                .map(mapper::toDTO);
    }

    @Override
    public Mono<Void> delete(UUID kycVerificationId) {
        return repository.deleteById(kycVerificationId);
    }

}
