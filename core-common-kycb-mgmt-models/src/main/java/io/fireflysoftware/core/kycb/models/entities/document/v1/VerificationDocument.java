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


package io.fireflysoftware.core.kycb.models.entities.document.v1;

import io.fireflysoftware.core.kycb.models.entities.BaseEntity;
import io.fireflysoftware.core.kycb.interfaces.enums.document.v1.DocumentTypeEnum;
import io.fireflysoftware.core.kycb.interfaces.enums.verification.v1.VerificationPurposeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Entity representing a document used for verification.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("verification_document")
public class VerificationDocument extends BaseEntity {

    @Id
    @Column("verification_document_id")
    private UUID verificationDocumentId;

    @Column("kyc_verification_id")
    private UUID kycVerificationId;

    @Column("identity_document_id")
    private UUID identityDocumentId;

    @Column("document_type")
    private DocumentTypeEnum documentType;

    @Column("verification_purpose")
    private VerificationPurposeEnum verificationPurpose;

    @Column("document_reference")
    private String documentReference;

    @Column("document_system_id")
    private String documentSystemId;

    @Column("is_verified")
    private Boolean isVerified;

    @Column("verification_notes")
    private String verificationNotes;

    @Column("expiry_date")
    private LocalDateTime expiryDate;
}