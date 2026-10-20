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


package io.fireflysoftware.core.kycb.models.entities.aml.v1;

import io.fireflysoftware.core.kycb.models.entities.BaseEntity;
import io.fireflysoftware.core.kycb.interfaces.enums.aml.v1.ListTypeEnum;
import io.fireflysoftware.core.kycb.interfaces.enums.resolution.v1.ResolutionStatusEnum;
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
 * Entity representing a match from an AML screening.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("aml_match")
public class AmlMatch extends BaseEntity {

    @Id
    @Column("aml_match_id")
    private UUID amlMatchId;

    @Column("aml_screening_id")
    private UUID amlScreeningId;

    @Column("list_type")
    private ListTypeEnum listType;

    @Column("list_source")
    private String listSource;

    @Column("matched_name")
    private String matchedName;

    @Column("match_score")
    private BigDecimal matchScore;

    @Column("match_details")
    private String matchDetails;

    @Column("resolution_status")
    private ResolutionStatusEnum resolutionStatus;

    @Column("resolution_notes")
    private String resolutionNotes;

    @Column("resolution_agent")
    private String resolutionAgent;

    @Column("resolution_date")
    private LocalDateTime resolutionDate;
}