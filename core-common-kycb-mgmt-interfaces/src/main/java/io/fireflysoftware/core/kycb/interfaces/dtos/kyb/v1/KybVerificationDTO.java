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


package io.fireflysoftware.core.kycb.interfaces.dtos.kyb.v1;

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
 * DTO for KYB verification data.
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class KybVerificationDTO extends BaseDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID kybVerificationId;

    @FilterableId
    @NotNull(message = "Party ID is required")
    private UUID partyId;

    @NotBlank(message = "Verification status is required")
    @Size(max = 50, message = "Verification status must not exceed 50 characters")
    private String verificationStatus;

    @NotNull(message = "Verification date is required")
    @ValidDateTime
    private LocalDateTime verificationDate;

    @NotNull(message = "Mercantile registry verified flag is required")
    private Boolean mercantileRegistryVerified;

    @NotNull(message = "Deed of incorporation verified flag is required")
    private Boolean deedOfIncorporationVerified;

    @NotNull(message = "Business structure verified flag is required")
    private Boolean businessStructureVerified;

    @NotNull(message = "UBO verified flag is required")
    private Boolean uboVerified;

    @NotNull(message = "Tax ID verified flag is required")
    private Boolean taxIdVerified;

    @NotNull(message = "Operating license verified flag is required")
    private Boolean operatingLicenseVerified;

    @Size(max = 1000, message = "Verification notes must not exceed 1000 characters")
    private String verificationNotes;

    @NotNull(message = "Risk score is required")
    @Min(value = 0, message = "Risk score must be between 0 and 100")
    @Max(value = 100, message = "Risk score must be between 0 and 100")
    private Integer riskScore;

    @NotBlank(message = "Risk level is required")
    @Size(max = 20, message = "Risk level must not exceed 20 characters")
    private String riskLevel;

    @ValidDateTime
    private LocalDateTime nextReviewDate;
}
