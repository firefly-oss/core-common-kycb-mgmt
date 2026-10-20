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


package io.fireflysoftware.core.kycb.interfaces.dtos.aml.v1;

import org.fireflyframework.annotations.ValidAmount;
import org.fireflyframework.annotations.ValidDateTime;
import io.fireflysoftware.core.kycb.interfaces.dtos.BaseDTO;
import org.fireflyframework.utils.annotations.FilterableId;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * DTO for AML match data.
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class AmlMatchDTO extends BaseDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID amlMatchId;

    @FilterableId
    @NotNull(message = "AML screening ID is required")
    private UUID amlScreeningId;

    @NotBlank(message = "List type is required")
    @Size(max = 50, message = "List type must not exceed 50 characters")
    private String listType;

    @NotBlank(message = "List source is required")
    @Size(max = 100, message = "List source must not exceed 100 characters")
    private String listSource;

    @NotBlank(message = "Matched name is required")
    @Size(max = 200, message = "Matched name must not exceed 200 characters")
    private String matchedName;

    @NotNull(message = "Match score is required")
    @DecimalMin(value = "0.0", message = "Match score must be between 0.0 and 1.0")
    @DecimalMax(value = "1.0", message = "Match score must be between 0.0 and 1.0")
    @ValidAmount
    private BigDecimal matchScore;

    @Size(max = 1000, message = "Match details must not exceed 1000 characters")
    private String matchDetails;

    @NotBlank(message = "Resolution status is required")
    @Size(max = 50, message = "Resolution status must not exceed 50 characters")
    private String resolutionStatus;

    @Size(max = 1000, message = "Resolution notes must not exceed 1000 characters")
    private String resolutionNotes;

    @Size(max = 100, message = "Resolution agent must not exceed 100 characters")
    private String resolutionAgent;

    @ValidDateTime
    private LocalDateTime resolutionDate;
}
