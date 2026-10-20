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


package io.fireflysoftware.core.kycb.models.repositories.document.v1;

import io.fireflysoftware.core.kycb.models.entities.document.v1.CorporateDocument;
import io.fireflysoftware.core.kycb.interfaces.enums.document.v1.DocumentTypeEnum;
import io.fireflysoftware.core.kycb.models.repositories.BaseRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Repository for corporate document operations.
 */
@Repository
public interface CorporateDocumentRepository extends BaseRepository<CorporateDocument, UUID> {

    /**
     * Find corporate documents by party ID.
     *
     * @param partyId The ID of the party
     * @return A flux of corporate documents
     */
    Flux<CorporateDocument> findByPartyId(UUID partyId);

    /**
     * Find corporate documents by document type.
     *
     * @param documentTypeEnum The type of document
     * @return A flux of corporate documents
     */
    Flux<CorporateDocument> findByDocumentType(DocumentTypeEnum documentTypeEnum);

    /**
     * Find corporate documents by party ID and document type.
     *
     * @param partyId The ID of the party
     * @param documentTypeEnum The type of document
     * @return A flux of corporate documents
     */
    Flux<CorporateDocument> findByPartyIdAndDocumentType(UUID partyId, DocumentTypeEnum documentTypeEnum);

    /**
     * Find corporate documents by verification status.
     *
     * @param isVerified The verification status
     * @return A flux of corporate documents
     */
    Flux<CorporateDocument> findByIsVerified(Boolean isVerified);

    /**
     * Find corporate documents that are about to expire.
     *
     * @param expiryDate The expiry date threshold
     * @return A flux of corporate documents
     */
    Flux<CorporateDocument> findByExpiryDateBefore(LocalDateTime expiryDate);
}
