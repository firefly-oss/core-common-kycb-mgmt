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


package io.fireflysoftware.core.kycb.interfaces.dtos.compliance.v1;

import org.fireflyframework.annotations.ValidDateTime;
import io.fireflysoftware.core.kycb.interfaces.dtos.BaseDTO;
import org.fireflyframework.utils.annotations.FilterableId;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * DTO for compliance action data.
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ComplianceActionDTO extends BaseDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID complianceActionId;

    @FilterableId
    @NotNull(message = "Compliance case ID is required")
    private UUID complianceCaseId;

    @NotBlank(message = "Action type is required")
    @Size(max = 50, message = "Action type must not exceed 50 characters")
    private String actionType;

    @NotBlank(message = "Action status is required")
    @Size(max = 50, message = "Action status must not exceed 50 characters")
    private String actionStatus;

    @NotBlank(message = "Action description is required")
    @Size(max = 1000, message = "Action description must not exceed 1000 characters")
    private String actionDescription;

    @NotBlank(message = "Action agent is required")
    @Size(max = 100, message = "Action agent must not exceed 100 characters")
    private String actionAgent;

    @NotNull(message = "Due date is required")
    @ValidDateTime
    private LocalDateTime dueDate;

    @ValidDateTime
    private LocalDateTime completionDate;

    @Size(max = 1000, message = "Result must not exceed 1000 characters")
    private String result;
}
