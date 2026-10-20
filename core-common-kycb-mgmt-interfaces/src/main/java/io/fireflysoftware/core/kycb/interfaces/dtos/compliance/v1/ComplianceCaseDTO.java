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
 * DTO for compliance case data.
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ComplianceCaseDTO extends BaseDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID complianceCaseId;

    @FilterableId
    @NotNull(message = "Party ID is required")
    private UUID partyId;

    @NotBlank(message = "Case type is required")
    @Size(max = 50, message = "Case type must not exceed 50 characters")
    private String caseType;

    @NotBlank(message = "Case status is required")
    @Size(max = 50, message = "Case status must not exceed 50 characters")
    private String caseStatus;

    @NotBlank(message = "Case priority is required")
    @Size(max = 20, message = "Case priority must not exceed 20 characters")
    private String casePriority;

    @NotBlank(message = "Case reference is required")
    @Size(max = 100, message = "Case reference must not exceed 100 characters")
    private String caseReference;

    @NotBlank(message = "Case summary is required")
    @Size(max = 2000, message = "Case summary must not exceed 2000 characters")
    private String caseSummary;

    @NotBlank(message = "Assigned to is required")
    @Size(max = 100, message = "Assigned to must not exceed 100 characters")
    private String assignedTo;

    @NotNull(message = "Due date is required")
    @ValidDateTime
    private LocalDateTime dueDate;

    @ValidDateTime
    private LocalDateTime resolutionDate;

    @Size(max = 2000, message = "Resolution notes must not exceed 2000 characters")
    private String resolutionNotes;

    @NotNull(message = "Report to SEPBLAC required flag is required")
    private Boolean reportToSepblacRequired;
}
