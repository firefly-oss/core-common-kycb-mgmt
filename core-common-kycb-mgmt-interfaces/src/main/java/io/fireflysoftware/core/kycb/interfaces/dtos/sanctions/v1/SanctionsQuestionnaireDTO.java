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


package io.fireflysoftware.core.kycb.interfaces.dtos.sanctions.v1;

import org.fireflyframework.annotations.ValidDateTime;
import io.fireflysoftware.core.kycb.interfaces.dtos.BaseDTO;
import io.fireflysoftware.core.kycb.interfaces.enums.sanctions.v1.EntitySanctionsQuestionnaireTypeEnum;
import org.fireflyframework.utils.annotations.FilterableId;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * DTO for sanctions and embargo questionnaire.
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class SanctionsQuestionnaireDTO extends BaseDTO {

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private UUID sanctionsQuestionnaireId;

    @FilterableId
    @NotNull(message = "Party ID is required")
    private UUID partyId;

    /**
     * Defines for which part of the entity the user answers the questionnaire.
     */
    @NotNull(message = "Entity sanctions questionnaire type is required")
    private EntitySanctionsQuestionnaireTypeEnum entitySanctionsQuestionnaire;

    /**
     * Whether the entity and its business units, subsidiaries, and joint ventures have a commercial activity outside the European Union.
     */
    @NotNull(message = "Activity outside EU flag is required")
    private Boolean activityOutsideEu;

    /**
     * Whether the entity, its subsidiaries, entities, employees, directors, beneficial owners, or joint ventures are subject to Economic Sanctions.
     */
    @NotNull(message = "Economic sanctions flag is required")
    private Boolean economicSanctions;

    /**
     * Whether the entity, its subsidiaries, or joint ventures, located within or operating from any countries or territories are subject to Comprehensive Sanctions.
     */
    @NotNull(message = "Resident countries sanctions flag is required")
    private Boolean residentCountriesSanctions;

    /**
     * Whether the entity, its subsidiaries, or joint ventures, are engaged in transactions, investments, business, or other dealings that directly or indirectly involve or benefit:
     * Any countries or territories subject to Comprehensive Sanctions, or
     * Any person or entity which is the target of any Sanctions ("Sanctioned Targets").
     */
    @NotNull(message = "Involved sanctions flag is required")
    private Boolean involvedSanctions;

    /**
     * The date when the questionnaire was completed.
     */
    @NotNull(message = "Questionnaire date is required")
    @ValidDateTime
    private LocalDateTime questionnaireDate;
}
