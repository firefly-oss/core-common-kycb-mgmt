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


package io.fireflysoftware.core.kycb.interfaces.dtos.location.v1;

import org.fireflyframework.annotations.ValidDateTime;
import org.fireflyframework.annotations.ValidPhoneNumber;
import io.fireflysoftware.core.kycb.interfaces.dtos.BaseDTO;
import org.fireflyframework.utils.annotations.FilterableId;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * DTO for business location data.
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class BusinessLocationDTO extends BaseDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID businessLocationId;

    @FilterableId
    @NotNull(message = "Party ID is required")
    private UUID partyId;

    @NotBlank(message = "Location type code is required")
    @Size(max = 20, message = "Location type code must not exceed 20 characters")
    private String locationTypeCode;

    @NotNull(message = "Primary location flag is required")
    private Boolean isPrimary;

    @NotBlank(message = "Address line 1 is required")
    @Size(max = 200, message = "Address line 1 must not exceed 200 characters")
    private String addressLine1;

    @Size(max = 200, message = "Address line 2 must not exceed 200 characters")
    private String addressLine2;

    @Size(max = 20, message = "Postal code must not exceed 20 characters")
    @Pattern(regexp = "^[A-Za-z0-9\\s-]*$", message = "Postal code contains invalid characters")
    private String postalCode;

    @NotBlank(message = "City is required")
    @Size(max = 100, message = "City must not exceed 100 characters")
    private String city;

    @Size(max = 10, message = "Division code must not exceed 10 characters")
    private String divisionCode;

    @NotBlank(message = "Country ISO code is required")
    @Size(min = 2, max = 3, message = "Country ISO code must be 2 or 3 characters")
    @Pattern(regexp = "^[A-Z]{2,3}$", message = "Country ISO code must be uppercase letters")
    private String countryIsoCode;

    @ValidPhoneNumber
    private String phoneNumber;

    @Email(message = "Email must be valid")
    private String email;

    @Min(value = 0, message = "Employee count must be non-negative")
    @Max(value = 1000000, message = "Employee count must be reasonable")
    private Integer employeeCount;

    @Size(max = 1000, message = "Activities conducted must not exceed 1000 characters")
    private String activitiesConducted;

    @NotNull(message = "Verified status is required")
    private Boolean isVerified;

    @Size(max = 50, message = "Verification method must not exceed 50 characters")
    private String verificationMethod;

    @ValidDateTime
    private LocalDateTime verificationDate;
}
