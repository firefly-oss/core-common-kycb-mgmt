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


package io.fireflysoftware.core.kycb.models.entities.aml.v1;

import io.fireflysoftware.core.kycb.models.entities.BaseEntity;
import io.fireflysoftware.core.kycb.interfaces.enums.screening.v1.ScreeningResultEnum;
import io.fireflysoftware.core.kycb.interfaces.enums.screening.v1.ScreeningTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Entity representing an AML screening record.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("aml_screening")
public class AmlScreening extends BaseEntity {

    @Id
    @Column("aml_screening_id")
    private UUID amlScreeningId;

    @Column("party_id")
    private UUID partyId;

    @Column("screening_date")
    private LocalDateTime screeningDate;

    @Column("screening_type")
    private ScreeningTypeEnum screeningType;

    @Column("matches_found")
    private Boolean matchesFound;

    @Column("match_count")
    private Integer matchCount;

    @Column("screening_provider")
    private String screeningProvider;

    @Column("reference_id")
    private String referenceId;

    @Column("screening_result")
    private ScreeningResultEnum screeningResult;

    @Column("next_screening_date")
    private LocalDateTime nextScreeningDate;
}