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

import org.fireflyframework.annotations.ValidDateTime;
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

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * DTO for AML screening data.
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class AmlScreeningDTO extends BaseDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID amlScreeningId;

    @FilterableId
    @NotNull(message = "Party ID is required")
    private UUID partyId;

    @NotNull(message = "Screening date is required")
    @ValidDateTime
    private LocalDateTime screeningDate;

    @NotBlank(message = "Screening type is required")
    @Size(max = 50, message = "Screening type must not exceed 50 characters")
    private String screeningType;

    @NotNull(message = "Matches found flag is required")
    private Boolean matchesFound;

    @Min(value = 0, message = "Match count must be non-negative")
    private Integer matchCount;

    @NotBlank(message = "Screening provider is required")
    @Size(max = 100, message = "Screening provider must not exceed 100 characters")
    private String screeningProvider;

    @FilterableId
    @Size(max = 100, message = "Reference ID must not exceed 100 characters")
    private String referenceId;

    @NotBlank(message = "Screening result is required")
    @Size(max = 50, message = "Screening result must not exceed 50 characters")
    private String screeningResult;

    @ValidDateTime
    private LocalDateTime nextScreeningDate;
}
