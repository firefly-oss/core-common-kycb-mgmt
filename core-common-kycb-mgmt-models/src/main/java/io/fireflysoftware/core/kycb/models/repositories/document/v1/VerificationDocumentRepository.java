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

import io.fireflysoftware.core.kycb.models.entities.document.v1.VerificationDocument;
import io.fireflysoftware.core.kycb.interfaces.enums.document.v1.DocumentTypeEnum;
import io.fireflysoftware.core.kycb.models.repositories.BaseRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import java.util.UUID;

/**
 * Repository for verification document operations.
 */
@Repository
public interface VerificationDocumentRepository extends BaseRepository<VerificationDocument, UUID> {

    /**
     * Find verification documents by KYC verification ID.
     *
     * @param kycVerificationId The ID of the KYC verification
     * @return A flux of verification documents
     */
    Flux<VerificationDocument> findByKycVerificationId(UUID kycVerificationId);

    /**
     * Find verification documents by document type.
     *
     * @param documentTypeEnum The type of document
     * @return A flux of verification documents
     */
    Flux<VerificationDocument> findByDocumentType(DocumentTypeEnum documentTypeEnum);

    /**
     * Find verification documents by KYC verification ID and document type.
     *
     * @param kycVerificationId The ID of the KYC verification
     * @param documentTypeEnum The type of document
     * @return A flux of verification documents
     */
    Flux<VerificationDocument> findByKycVerificationIdAndDocumentType(UUID kycVerificationId, DocumentTypeEnum documentTypeEnum);

    /**
     * Find verification documents by verification status.
     *
     * @param isVerified The verification status
     * @return A flux of verification documents
     */
    Flux<VerificationDocument> findByIsVerified(Boolean isVerified);
}