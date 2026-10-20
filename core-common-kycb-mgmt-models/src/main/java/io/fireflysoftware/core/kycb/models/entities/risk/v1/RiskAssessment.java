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


package io.fireflysoftware.core.kycb.models.entities.risk.v1;

import io.fireflysoftware.core.kycb.models.entities.BaseEntity;
import io.fireflysoftware.core.kycb.interfaces.enums.assessment.v1.AssessmentTypeEnum;
import io.fireflysoftware.core.kycb.interfaces.enums.risk.v1.RiskCategoryEnum;
import io.fireflysoftware.core.kycb.interfaces.enums.risk.v1.RiskLevelEnum;
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
 * Entity representing a risk assessment.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("risk_assessment")
public class RiskAssessment extends BaseEntity {

    @Id
    @Column("risk_assessment_id")
    private UUID riskAssessmentId;

    @Column("party_id")
    private UUID partyId;

    @Column("assessment_type")
    private AssessmentTypeEnum assessmentType;

    @Column("assessment_date")
    private LocalDateTime assessmentDate;

    @Column("risk_category")
    private RiskCategoryEnum riskCategory;

    @Column("risk_score")
    private Integer riskScore;

    @Column("risk_level")
    private RiskLevelEnum riskLevel;

    @Column("risk_factors")
    private String riskFactors;

    @Column("assessment_notes")
    private String assessmentNotes;

    @Column("assessment_agent")
    private String assessmentAgent;

    @Column("next_assessment_date")
    private LocalDateTime nextAssessmentDate;
}