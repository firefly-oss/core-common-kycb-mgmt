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
import io.fireflysoftware.core.kycb.interfaces.enums.action.v1.ActionStatusEnum;
import io.fireflysoftware.core.kycb.interfaces.enums.action.v1.ActionTypeEnum;
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
 * Entity representing a compliance action within a case.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("compliance_action")
public class ComplianceAction extends BaseEntity {

    @Id
    @Column("compliance_action_id")
    private UUID complianceActionId;

    @Column("compliance_case_id")
    private UUID complianceCaseId;

    @Column("action_type")
    private ActionTypeEnum actionType;

    @Column("action_status")
    private ActionStatusEnum actionStatus;

    @Column("action_description")
    private String actionDescription;

    @Column("action_agent")
    private String actionAgent;

    @Column("due_date")
    private LocalDateTime dueDate;

    @Column("completion_date")
    private LocalDateTime completionDate;

    @Column("result")
    private String result;
}