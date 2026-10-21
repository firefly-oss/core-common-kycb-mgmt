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


package io.fireflysoftware.core.kycb.interfaces.dtos.ownership.v1;

import org.fireflyframework.annotations.ValidDateTime;
import org.fireflyframework.annotations.ValidInterestRate;
import io.fireflysoftware.core.kycb.interfaces.dtos.BaseDTO;
import org.fireflyframework.utils.annotations.FilterableId;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
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
 * DTO for Ultimate Beneficial Owner data.
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class UboDTO extends BaseDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID uboId;

    @FilterableId
    @NotNull(message = "Party ID is required")
    private UUID partyId;

    @FilterableId
    @NotNull(message = "Natural person ID is required")
    private UUID naturalPersonId;

    @NotNull(message = "Ownership percentage is required")
    @ValidInterestRate
    private BigDecimal ownershipPercentage;

    @NotBlank(message = "Ownership type is required")
    @Size(max = 50, message = "Ownership type must not exceed 50 characters")
    private String ownershipType;

    @Size(max = 500, message = "Control structure must not exceed 500 characters")
    private String controlStructure;

    @NotNull(message = "Verified status is required")
    private Boolean isVerified;

    @Size(max = 50, message = "Verification method must not exceed 50 characters")
    private String verificationMethod;

    @Size(max = 200, message = "Beneficial ownership document must not exceed 200 characters")
    private String beneficialOwnershipDocument;

    @ValidDateTime
    private LocalDateTime verificationDate;

    @NotNull(message = "Start date is required")
    @ValidDateTime
    private LocalDateTime startDate;

    @ValidDateTime
    private LocalDateTime endDate;

    @Email(message = "Email must be a valid email address")
    @Size(max = 255, message = "Email must not exceed 255 characters")
    private String email;
}
