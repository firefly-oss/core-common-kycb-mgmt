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


package io.fireflysoftware.core.kycb.models.entities.compliance.v1;

import io.fireflysoftware.core.kycb.models.entities.BaseEntity;
import io.fireflysoftware.core.kycb.interfaces.enums.compliance.v1.CasePriorityEnum;
import io.fireflysoftware.core.kycb.interfaces.enums.compliance.v1.CaseStatusEnum;
import io.fireflysoftware.core.kycb.interfaces.enums.compliance.v1.CaseTypeEnum;
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
 * Entity representing a compliance case.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("compliance_case")
public class ComplianceCase extends BaseEntity {

    @Id
    @Column("compliance_case_id")
    private UUID complianceCaseId;

    @Column("party_id")
    private UUID partyId;

    @Column("case_type")
    private CaseTypeEnum caseType;

    @Column("case_status")
    private CaseStatusEnum caseStatus;

    @Column("case_priority")
    private CasePriorityEnum casePriority;

    @Column("case_reference")
    private String caseReference;

    @Column("case_summary")
    private String caseSummary;

    @Column("assigned_to")
    private String assignedTo;

    @Column("due_date")
    private LocalDateTime dueDate;

    @Column("resolution_date")
    private LocalDateTime resolutionDate;

    @Column("resolution_notes")
    private String resolutionNotes;

    @Column("report_to_sepblac_required")
    private Boolean reportToSepblacRequired;
}