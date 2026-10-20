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


package io.fireflysoftware.core.kycb.models.entities.kyb.v1;

import io.fireflysoftware.core.kycb.models.entities.BaseEntity;
import io.fireflysoftware.core.kycb.interfaces.enums.risk.v1.RiskLevelEnum;
import io.fireflysoftware.core.kycb.interfaces.enums.verification.v1.VerificationStatusEnum;
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
 * Entity representing a KYB (Know Your Business) verification record.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("kyb_verification")
public class KybVerification extends BaseEntity {

    @Id
    @Column("kyb_verification_id")
    private UUID kybVerificationId;

    @Column("party_id")
    private UUID partyId;

    @Column("verification_status")
    private VerificationStatusEnum verificationStatus;

    @Column("verification_date")
    private LocalDateTime verificationDate;

    @Column("mercantile_registry_verified")
    private Boolean mercantileRegistryVerified;

    @Column("deed_of_incorporation_verified")
    private Boolean deedOfIncorporationVerified;

    @Column("business_structure_verified")
    private Boolean businessStructureVerified;

    @Column("ubo_verified")
    private Boolean uboVerified;

    @Column("tax_id_verified")
    private Boolean taxIdVerified;

    @Column("operating_license_verified")
    private Boolean operatingLicenseVerified;

    @Column("verification_notes")
    private String verificationNotes;

    @Column("risk_score")
    private Integer riskScore;

    @Column("risk_level")
    private RiskLevelEnum riskLevel;

    @Column("next_review_date")
    private LocalDateTime nextReviewDate;
}
