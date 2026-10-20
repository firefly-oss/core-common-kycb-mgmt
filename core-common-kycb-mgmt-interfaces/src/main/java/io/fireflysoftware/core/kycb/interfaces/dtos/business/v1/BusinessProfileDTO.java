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


package io.fireflysoftware.core.kycb.interfaces.dtos.business.v1;

import org.fireflyframework.annotations.ValidAmount;
import org.fireflyframework.annotations.ValidCurrencyCode;
import io.fireflysoftware.core.kycb.interfaces.dtos.BaseDTO;
import org.fireflyframework.utils.annotations.FilterableId;
import com.fasterxml.jackson.annotation.JsonProperty;
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

import java.math.BigDecimal;
import java.util.UUID;

/**
 * DTO for business profile data.
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class BusinessProfileDTO extends BaseDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID businessProfileId;

    @FilterableId
    @NotNull(message = "Party ID is required")
    private UUID partyId;

    @NotBlank(message = "Legal form code is required")
    @Size(max = 20, message = "Legal form code must not exceed 20 characters")
    private String legalFormCode;

    @NotBlank(message = "Business description is required")
    @Size(max = 1000, message = "Business description must not exceed 1000 characters")
    private String businessDescription;

    @Pattern(regexp = "^https?://.*", message = "Website URL must be a valid HTTP or HTTPS URL")
    @Size(max = 255, message = "Website URL must not exceed 255 characters")
    private String websiteUrl;

    @Min(value = 1800, message = "Incorporation year must be 1800 or later")
    @Max(value = 2100, message = "Incorporation year must be 2100 or earlier")
    private Integer incorporationYear;

    @Min(value = 0, message = "Employee count must be non-negative")
    @Max(value = 10000000, message = "Employee count must be reasonable")
    private Integer employeeCount;

    @Size(max = 20, message = "Employee range code must not exceed 20 characters")
    private String employeeRangeCode;

    @ValidAmount
    private BigDecimal annualRevenue;

    @ValidCurrencyCode
    private String currencyIsoCode;

    @Size(max = 20, message = "Revenue range code must not exceed 20 characters")
    private String revenueRangeCode;

    @Size(max = 100, message = "Stock exchange must not exceed 100 characters")
    private String stockExchange;

    @Size(max = 20, message = "Stock symbol must not exceed 20 characters")
    private String stockSymbol;

    @NotNull(message = "Regulated status is required")
    private Boolean isRegulated;

    @Size(max = 200, message = "Regulatory authority must not exceed 200 characters")
    private String regulatoryAuthority;

    @Size(max = 20, message = "Company status code must not exceed 20 characters")
    private String companyStatusCode;

    @Size(max = 20, message = "Company size code must not exceed 20 characters")
    private String companySizeCode;

    @NotNull(message = "Public entity status is required")
    private Boolean isPublicEntity;

    @NotBlank(message = "Registration number is required")
    @Size(max = 50, message = "Registration number must not exceed 50 characters")
    private String registrationNumber;
}
