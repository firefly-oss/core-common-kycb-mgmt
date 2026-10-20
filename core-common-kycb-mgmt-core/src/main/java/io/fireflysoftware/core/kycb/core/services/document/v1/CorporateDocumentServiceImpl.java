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
import io.fireflysoftware.core.kycb.core.mappers.document.v1.CorporateDocumentMapper;
import io.fireflysoftware.core.kycb.interfaces.dtos.document.v1.CorporateDocumentDTO;
import io.fireflysoftware.core.kycb.models.entities.document.v1.CorporateDocument;
import io.fireflysoftware.core.kycb.models.repositories.document.v1.CorporateDocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;
import java.util.UUID;

/**
 * Implementation of the corporate document service.
 */
@Service
@Transactional
public class CorporateDocumentServiceImpl implements CorporateDocumentService {

    @Autowired
    private CorporateDocumentRepository repository;

    @Autowired
    private CorporateDocumentMapper mapper;

    @Override
    public Mono<PaginationResponse<CorporateDocumentDTO>> findAll(FilterRequest<CorporateDocumentDTO> filterRequest) {
        return FilterUtils.createFilter(
                CorporateDocument.class,
                mapper::toDTO
        ).filter(filterRequest);
    }

    @Override
    public Mono<CorporateDocumentDTO> create(CorporateDocumentDTO dto) {
        CorporateDocument entity = mapper.toEntity(dto);
        return repository.save(entity)
                .map(mapper::toDTO);
    }

    @Override
    public Mono<CorporateDocumentDTO> getById(UUID documentId) {
        return repository.findById(documentId)
                .map(mapper::toDTO);
    }

    @Override
    public Mono<CorporateDocumentDTO> update(UUID documentId, CorporateDocumentDTO dto) {
        return repository.findById(documentId)
                .flatMap(existingEntity -> {
                    CorporateDocument updatedEntity = mapper.toEntity(dto);
                    updatedEntity.setCorporateDocumentId(documentId);
                    // Preserve creation date
                    updatedEntity.setDateCreated(existingEntity.getDateCreated());
                    // Preserve verification data if not changing
                    if (existingEntity.getIsVerified() && updatedEntity.getIsVerified() == null) {
                        updatedEntity.setIsVerified(existingEntity.getIsVerified());
                        updatedEntity.setVerificationDate(existingEntity.getVerificationDate());
                        updatedEntity.setVerificationNotes(existingEntity.getVerificationNotes());
                        updatedEntity.setVerificationAgent(existingEntity.getVerificationAgent());
                    }
                    return repository.save(updatedEntity);
                })
                .map(mapper::toDTO);
    }

    @Override
    public Mono<Void> delete(UUID documentId) {
        return repository.deleteById(documentId);
    }
}
