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


package io.fireflysoftware.core.kycb.interfaces.dtos.power.v1;

import org.fireflyframework.annotations.ValidAmount;
import org.fireflyframework.annotations.ValidCurrencyCode;
import org.fireflyframework.annotations.ValidDateTime;
import io.fireflysoftware.core.kycb.interfaces.dtos.BaseDTO;
import org.fireflyframework.utils.annotations.FilterableId;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
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
 * DTO for power of attorney data.
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class PowerOfAttorneyDTO extends BaseDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID powerOfAttorneyId;

    @FilterableId
    @NotNull(message = "Corporate document ID is required")
    private UUID corporateDocumentId;

    @FilterableId
    @NotNull(message = "Party ID is required")
    private UUID partyId;

    @FilterableId
    @NotNull(message = "Attorney ID is required")
    private UUID attorneyId;

    @NotBlank(message = "Power type is required")
    @Size(max = 50, message = "Power type must not exceed 50 characters")
    private String powerType;

    @NotBlank(message = "Power scope is required")
    @Size(max = 500, message = "Power scope must not exceed 500 characters")
    private String powerScope;

    @NotNull(message = "Joint signature required flag is required")
    private Boolean jointSignatureRequired;

    @Min(value = 1, message = "Joint signature count must be at least 1")
    private Integer jointSignatureCount;

    @Size(max = 1000, message = "Joint signature notes must not exceed 1000 characters")
    private String jointSignatureNotes;

    @ValidAmount
    private BigDecimal financialLimit;

    @ValidCurrencyCode
    private String currency;

    @NotNull(message = "Effective date is required")
    @ValidDateTime
    private LocalDateTime effectiveDate;

    @ValidDateTime
    private LocalDateTime expiryDate;

    @NotNull(message = "Verified status is required")
    private Boolean isVerified;

    @NotNull(message = "POA completed flag is required")
    private Boolean isPoaCompleted;

    @Size(max = 50, message = "Verification method must not exceed 50 characters")
    private String verificationMethod;

    @ValidDateTime
    private LocalDateTime verificationDate;

    @Size(max = 200, message = "Verifying legal counsel must not exceed 200 characters")
    private String verifyingLegalCounsel;

    @Email(message = "Email must be a valid email address")
    @Size(max = 255, message = "Email must not exceed 255 characters")
    private String email;

    private Boolean signingAuthorized;

    /**
     * Quick-capture PEP flag at signer registration. The canonical PEP record
     * lives in core-common-customer-mgmt.PoliticallyExposedPerson — this flag
     * is for fast triage during onboarding.
     */
    private Boolean isPep;
}
