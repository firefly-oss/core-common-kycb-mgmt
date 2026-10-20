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


package io.fireflysoftware.core.kycb.interfaces.dtos.source.v1;

import org.fireflyframework.annotations.ValidAmount;
import org.fireflyframework.annotations.ValidCurrencyCode;
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

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * DTO for source of funds data.
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class SourceOfFundsDTO extends BaseDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID sourceOfFundsId;

    @FilterableId
    @NotNull(message = "Party ID is required")
    private UUID partyId;

    @NotBlank(message = "Source type is required")
    @Size(max = 50, message = "Source type must not exceed 50 characters")
    private String sourceType;

    @NotBlank(message = "Source description is required")
    @Size(max = 500, message = "Source description must not exceed 500 characters")
    private String sourceDescription;

    @NotNull(message = "Estimated annual amount is required")
    @ValidAmount
    private BigDecimal estimatedAnnualAmount;

    @NotBlank(message = "Currency is required")
    @ValidCurrencyCode
    private String currency;

    @NotNull(message = "Verified status is required")
    private Boolean isVerified;

    @Size(max = 50, message = "Verification method must not exceed 50 characters")
    private String verificationMethod;

    @Size(max = 500, message = "Supporting documents must not exceed 500 characters")
    private String supportingDocuments;

    @ValidDateTime
    private LocalDateTime verificationDate;
}
