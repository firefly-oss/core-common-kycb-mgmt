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


package io.fireflysoftware.core.kycb.interfaces.dtos.expected.v1;

import org.fireflyframework.annotations.ValidAmount;
import org.fireflyframework.annotations.ValidCurrencyCode;
import io.fireflysoftware.core.kycb.interfaces.dtos.BaseDTO;
import org.fireflyframework.utils.annotations.FilterableId;
import com.fasterxml.jackson.annotation.JsonProperty;
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
import java.util.UUID;

/**
 * DTO for expected activity data.
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ExpectedActivityDTO extends BaseDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID expectedActivityId;

    @FilterableId
    @NotNull(message = "Party ID is required")
    private UUID partyId;

    @NotBlank(message = "Activity type code is required")
    @Size(max = 20, message = "Activity type code must not exceed 20 characters")
    private String activityTypeCode;

    @NotNull(message = "Expected monthly volume is required")
    @ValidAmount
    private BigDecimal expectedMonthlyVolume;

    @NotNull(message = "Expected annual volume is required")
    @ValidAmount
    private BigDecimal expectedAnnualVolume;

    @NotNull(message = "Expected transaction count is required")
    @Min(value = 0, message = "Expected transaction count must be non-negative")
    private Integer expectedTransactionCount;

    @NotBlank(message = "Currency ISO code is required")
    @ValidCurrencyCode
    private String currencyIsoCode;

    @NotBlank(message = "Transaction frequency code is required")
    @Size(max = 20, message = "Transaction frequency code must not exceed 20 characters")
    private String transactionFreqCode;

    @Size(max = 500, message = "Anticipated countries must not exceed 500 characters")
    private String anticipatedCountries;

    @NotNull(message = "High value flag is required")
    private Boolean isHighValue;

    @Size(max = 1000, message = "High value details must not exceed 1000 characters")
    private String highValueDetails;

    @Size(max = 1000, message = "Expected counterparties must not exceed 1000 characters")
    private String expectedCounterparties;

    @NotNull(message = "Cash intensive flag is required")
    private Boolean cashIntensive;

    @NotNull(message = "Tax haven transactions flag is required")
    private Boolean taxHavenTransactions;

    @Size(max = 20, message = "Declaration model code must not exceed 20 characters")
    private String declarationModelCode;

    @Size(max = 1000, message = "Declaration details must not exceed 1000 characters")
    private String declarationDetails;

    @Size(max = 1000, message = "Verification notes must not exceed 1000 characters")
    private String verificationNotes;
}
