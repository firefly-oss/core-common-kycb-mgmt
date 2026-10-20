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


package io.fireflysoftware.core.kycb.models.entities.power.v1;

import io.fireflysoftware.core.kycb.models.entities.BaseEntity;
import io.fireflysoftware.core.kycb.interfaces.enums.power.v1.PowerTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Entity representing a power of attorney.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("power_of_attorney")
public class PowerOfAttorney extends BaseEntity {

    @Id
    @Column("power_of_attorney_id")
    private UUID powerOfAttorneyId;

    @Column("corporate_document_id")
    private UUID corporateDocumentId;

    @Column("party_id")
    private UUID partyId;

    @Column("attorney_id")
    private UUID attorneyId;

    @Column("power_type")
    private PowerTypeEnum powerType;

    @Column("power_scope")
    private String powerScope;

    @Column("joint_signature_required")
    private Boolean jointSignatureRequired;

    @Column("joint_signature_count")
    private Integer jointSignatureCount;

    @Column("joint_signature_notes")
    private String jointSignatureNotes;

    @Column("financial_limit")
    private BigDecimal financialLimit;

    @Column("currency")
    private String currency;

    @Column("effective_date")
    private LocalDateTime effectiveDate;

    @Column("expiry_date")
    private LocalDateTime expiryDate;

    @Column("is_verified")
    private Boolean isVerified;

    @Column("is_poa_completed")
    private Boolean isPoaCompleted;

    @Column("verification_method")
    private String verificationMethod;

    @Column("verification_date")
    private LocalDateTime verificationDate;

    @Column("verifying_legal_counsel")
    private String verifyingLegalCounsel;

    @Column("email")
    private String email;

    @Column("signing_authorized")
    private Boolean signingAuthorized;

    /**
     * Quick-capture PEP flag at signer registration. The canonical PEP record
     * lives in core-common-customer-mgmt.PoliticallyExposedPerson — this flag
     * is for fast triage during onboarding.
     */
    @Column("is_pep")
    private Boolean isPep;
}
