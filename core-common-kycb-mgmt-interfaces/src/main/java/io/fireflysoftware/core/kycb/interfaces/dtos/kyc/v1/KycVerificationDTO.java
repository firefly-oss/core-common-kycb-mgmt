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


package io.fireflysoftware.core.kycb.interfaces.dtos.kyc.v1;

import org.fireflyframework.annotations.ValidDateTime;
import io.fireflysoftware.core.kycb.interfaces.dtos.BaseDTO;
import org.fireflyframework.utils.annotations.FilterableId;
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
 * DTO for KYC verification data.
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class KycVerificationDTO extends BaseDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID kycVerificationId;

    @FilterableId
    @NotNull(message = "Party ID is required")
    private UUID partyId;

    @NotBlank(message = "Verification status is required")
    @Size(max = 50, message = "Verification status must not exceed 50 characters")
    private String verificationStatus;

    @NotNull(message = "Verification date is required")
    @ValidDateTime
    private LocalDateTime verificationDate;

    @NotBlank(message = "Verification method is required")
    @Size(max = 50, message = "Verification method must not exceed 50 characters")
    private String verificationMethod;

    @NotBlank(message = "Verification agent is required")
    @Size(max = 100, message = "Verification agent must not exceed 100 characters")
    private String verificationAgent;

    @Size(max = 500, message = "Rejection reason must not exceed 500 characters")
    private String rejectionReason;

    @NotNull(message = "Risk score is required")
    @Min(value = 0, message = "Risk score must be between 0 and 100")
    @Max(value = 100, message = "Risk score must be between 0 and 100")
    private Integer riskScore;

    @NotBlank(message = "Risk level is required")
    @Size(max = 20, message = "Risk level must not exceed 20 characters")
    private String riskLevel;

    @NotNull(message = "Enhanced due diligence flag is required")
    private Boolean enhancedDueDiligence;

    @ValidDateTime
    private LocalDateTime nextReviewDate;
}
