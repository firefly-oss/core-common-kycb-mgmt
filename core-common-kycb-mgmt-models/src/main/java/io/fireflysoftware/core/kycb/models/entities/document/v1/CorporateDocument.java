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
import io.fireflysoftware.core.kycb.interfaces.enums.document.v1.CorporateDocumentTypeEnum;
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
 * Entity representing a corporate document.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("corporate_document")
public class CorporateDocument extends BaseEntity {

    @Id
    @Column("corporate_document_id")
    private UUID corporateDocumentId;

    @Column("party_id")
    private UUID partyId;

    @Column("document_type")
    private CorporateDocumentTypeEnum documentType;

    @Column("document_reference")
    private String documentReference;

    @Column("document_system_id")
    private String documentSystemId;

    @Column("notary_name")
    private String notaryName;

    @Column("notary_division_code")
    private String notaryDivisionCode;

    @Column("commercial_registry")
    private String commercialRegistry;

    @Column("registry_entry")
    private String registryEntry;

    @Column("issue_date")
    private LocalDateTime issueDate;

    @Column("registry_date")
    private LocalDateTime registryDate;

    @Column("expiry_date")
    private LocalDateTime expiryDate;

    @Column("is_verified")
    private Boolean isVerified;

    @Column("verification_notes")
    private String verificationNotes;

    @Column("verification_date")
    private LocalDateTime verificationDate;

    @Column("verification_agent")
    private String verificationAgent;
}
