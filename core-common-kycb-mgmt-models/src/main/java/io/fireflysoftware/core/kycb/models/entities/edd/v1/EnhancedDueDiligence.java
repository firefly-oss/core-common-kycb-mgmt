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


package io.fireflysoftware.core.kycb.models.entities.edd.v1;

import io.fireflysoftware.core.kycb.models.entities.BaseEntity;
import io.fireflysoftware.core.kycb.interfaces.enums.edd.v1.EddReasonEnum;
import io.fireflysoftware.core.kycb.interfaces.enums.edd.v1.EddStatusEnum;
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
 * Entity representing an enhanced due diligence process.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("enhanced_due_diligence")
public class EnhancedDueDiligence extends BaseEntity {

    @Id
    @Column("edd_id")
    private UUID eddId;

    @Column("kyc_verification_id")
    private UUID kycVerificationId;

    @Column("edd_reason")
    private EddReasonEnum eddReason;

    @Column("edd_status")
    private EddStatusEnum eddStatus;

    @Column("edd_description")
    private String eddDescription;

    @Column("approving_authority")
    private String approvingAuthority;

    @Column("approval_date")
    private LocalDateTime approvalDate;

    @Column("edd_notes")
    private String eddNotes;

    @Column("internal_committee_approval")
    private Boolean internalCommitteeApproval;

    @Column("committee_approval_date")
    private LocalDateTime committeeApprovalDate;

    @Column("completion_date")
    private LocalDateTime completionDate;

    @Column("completed_by")
    private String completedBy;
}