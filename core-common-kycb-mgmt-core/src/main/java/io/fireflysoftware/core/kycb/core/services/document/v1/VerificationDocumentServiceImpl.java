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


package io.fireflysoftware.core.kycb.core.services.document.v1;

import org.fireflyframework.core.filters.FilterRequest;
import org.fireflyframework.core.filters.FilterUtils;
import org.fireflyframework.core.queries.PaginationResponse;
import io.fireflysoftware.core.kycb.core.mappers.document.v1.VerificationDocumentMapper;
import io.fireflysoftware.core.kycb.interfaces.dtos.document.v1.VerificationDocumentDTO;
import io.fireflysoftware.core.kycb.models.entities.document.v1.VerificationDocument;
import io.fireflysoftware.core.kycb.models.repositories.document.v1.VerificationDocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;
import java.util.UUID;

@Service
@Transactional
public class VerificationDocumentServiceImpl implements VerificationDocumentService {

    @Autowired
    private VerificationDocumentRepository repository;

    @Autowired
    private VerificationDocumentMapper mapper;

    @Override
    public Mono<PaginationResponse<VerificationDocumentDTO>> findAll(FilterRequest<VerificationDocumentDTO> filterRequest) {
        return FilterUtils.createFilter(
                VerificationDocument.class,
                mapper::toDTO
        ).filter(filterRequest);
    }

    @Override
    public Mono<VerificationDocumentDTO> create(VerificationDocumentDTO dto) {
        VerificationDocument entity = mapper.toEntity(dto);
        return repository.save(entity)
                .map(mapper::toDTO);
    }

    @Override
    public Mono<VerificationDocumentDTO> getById(UUID verificationDocumentId) {
        return repository.findById(verificationDocumentId)
                .map(mapper::toDTO);
    }

    @Override
    public Mono<VerificationDocumentDTO> update(UUID verificationDocumentId, VerificationDocumentDTO dto) {
        return repository.findById(verificationDocumentId)
                .flatMap(existingEntity -> {
                    VerificationDocument updatedEntity = mapper.toEntity(dto);
                    updatedEntity.setVerificationDocumentId(verificationDocumentId);
                    // Preserve creation date
                    updatedEntity.setDateCreated(existingEntity.getDateCreated());
                    return repository.save(updatedEntity);
                })
                .map(mapper::toDTO);
    }

    @Override
    public Mono<Void> delete(UUID verificationDocumentId) {
        return repository.deleteById(verificationDocumentId);
    }
}
