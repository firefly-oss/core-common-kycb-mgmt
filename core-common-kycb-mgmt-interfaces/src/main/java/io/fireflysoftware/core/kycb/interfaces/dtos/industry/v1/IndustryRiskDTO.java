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


package io.fireflysoftware.core.kycb.interfaces.dtos.industry.v1;

import org.fireflyframework.annotations.ValidDateTime;
import io.fireflysoftware.core.kycb.interfaces.dtos.BaseDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
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
 * DTO for industry risk data.
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class IndustryRiskDTO extends BaseDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID industryRiskId;

    @NotBlank(message = "Activity code is required")
    @Size(max = 20, message = "Activity code must not exceed 20 characters")
    private String activityCode;

    @NotBlank(message = "Industry name is required")
    @Size(max = 200, message = "Industry name must not exceed 200 characters")
    private String industryName;

    @NotBlank(message = "Inherent risk level is required")
    @Size(max = 20, message = "Inherent risk level must not exceed 20 characters")
    private String inherentRiskLevel;

    @NotNull(message = "Risk score is required")
    @Min(value = 0, message = "Risk score must be between 0 and 100")
    @Max(value = 100, message = "Risk score must be between 0 and 100")
    private Integer riskScore;

    @Size(max = 1000, message = "Risk factors must not exceed 1000 characters")
    private String riskFactors;

    @Size(max = 1000, message = "Mitigating factors must not exceed 1000 characters")
    private String mitigatingFactors;

    @NotNull(message = "SEPBLAC high risk flag is required")
    private Boolean sepblacHighRisk;

    @NotNull(message = "EU high risk flag is required")
    private Boolean euHighRisk;

    @NotNull(message = "FATF high risk flag is required")
    private Boolean fatfHighRisk;

    @NotNull(message = "Cash intensive flag is required")
    private Boolean cashIntensive;

    @NotNull(message = "Complex structures flag is required")
    private Boolean complexStructures;

    @NotNull(message = "Assessment date is required")
    @ValidDateTime
    private LocalDateTime assessmentDate;

    @NotBlank(message = "Assessed by is required")
    @Size(max = 100, message = "Assessed by must not exceed 100 characters")
    private String assessedBy;

    @ValidDateTime
    private LocalDateTime nextAssessmentDate;

    @NotNull(message = "Requires EDD flag is required")
    private Boolean requiresEdd;
}
