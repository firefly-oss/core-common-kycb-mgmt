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


package io.fireflysoftware.core.kycb.interfaces.dtos.economic.v1;

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
import java.util.UUID;

/**
 * DTO for economic activity data.
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class EconomicActivityDTO extends BaseDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID economicActivityId;

    @FilterableId
    @NotNull(message = "Party ID is required")
    private UUID partyId;

    @NotBlank(message = "Activity code is required")
    @Size(max = 20, message = "Activity code must not exceed 20 characters")
    private String activityCode;

    @NotNull(message = "Primary activity flag is required")
    private Boolean isPrimary;

    @NotBlank(message = "Sector code is required")
    @Size(max = 20, message = "Sector code must not exceed 20 characters")
    private String sectorCode;

    @Size(max = 100, message = "Subsector must not exceed 100 characters")
    private String subsector;

    @NotNull(message = "High risk activity flag is required")
    private Boolean highRiskActivity;

    @Size(max = 1000, message = "Activity details must not exceed 1000 characters")
    private String activityDetails;

    @Size(max = 20, message = "Geographic scope code must not exceed 20 characters")
    private String geographicScopeCode;

    @Size(max = 500, message = "Export markets must not exceed 500 characters")
    private String exportMarkets;

    @Size(max = 500, message = "Import markets must not exceed 500 characters")
    private String importMarkets;

    @NotNull(message = "Regulated activity flag is required")
    private Boolean regulatedActivity;

    @Size(max = 1000, message = "Regulatory details must not exceed 1000 characters")
    private String regulatoryDetails;
}
