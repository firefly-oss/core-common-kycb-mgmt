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


package io.fireflysoftware.core.kycb.models.entities.kyc.v1;

import io.fireflysoftware.core.kycb.models.entities.BaseEntity;
import io.fireflysoftware.core.kycb.interfaces.enums.risk.v1.RiskLevelEnum;
import io.fireflysoftware.core.kycb.interfaces.enums.verification.v1.VerificationMethodEnum;
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
 * Entity representing a KYC verification record.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("kyc_verification")
public class KycVerification extends BaseEntity {

    @Id
    @Column("kyc_verification_id")
    private UUID kycVerificationId;

    @Column("party_id")
    private UUID partyId;

    @Column("verification_status")
    private VerificationStatusEnum verificationStatus;

    @Column("verification_date")
    private LocalDateTime verificationDate;

    @Column("verification_method")
    private VerificationMethodEnum verificationMethod;

    @Column("verification_agent")
    private String verificationAgent;

    @Column("rejection_reason")
    private String rejectionReason;

    @Column("risk_score")
    private Integer riskScore;

    @Column("risk_level")
    private RiskLevelEnum riskLevel;

    @Column("enhanced_due_diligence")
    private Boolean enhancedDueDiligence;

    @Column("next_review_date")
    private LocalDateTime nextReviewDate;
}