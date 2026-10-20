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


package io.fireflysoftware.core.kycb.models.repositories.risk.v1;

import io.fireflysoftware.core.kycb.models.entities.risk.v1.RiskAssessment;
import io.fireflysoftware.core.kycb.interfaces.enums.assessment.v1.AssessmentTypeEnum;
import io.fireflysoftware.core.kycb.interfaces.enums.risk.v1.RiskCategoryEnum;
import io.fireflysoftware.core.kycb.interfaces.enums.risk.v1.RiskLevelEnum;
import io.fireflysoftware.core.kycb.models.repositories.BaseRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Repository for risk assessment operations.
 */
@Repository
public interface RiskAssessmentRepository extends BaseRepository<RiskAssessment, UUID> {

    /**
     * Find risk assessments by party ID.
     *
     * @param partyId The ID of the party
     * @return A flux of risk assessments
     */
    Flux<RiskAssessment> findByPartyId(UUID partyId);

    /**
     * Find risk assessments by assessment type.
     *
     * @param assessmentTypeEnum The type of assessment
     * @return A flux of risk assessments
     */
    Flux<RiskAssessment> findByAssessmentType(AssessmentTypeEnum assessmentTypeEnum);

    /**
     * Find risk assessments by risk category.
     *
     * @param riskCategoryEnum The risk category
     * @return A flux of risk assessments
     */
    Flux<RiskAssessment> findByRiskCategory(RiskCategoryEnum riskCategoryEnum);

    /**
     * Find risk assessments by risk level.
     *
     * @param riskLevelEnum The risk level
     * @return A flux of risk assessments
     */
    Flux<RiskAssessment> findByRiskLevel(RiskLevelEnum riskLevelEnum);

    /**
     * Find risk assessments by assessment date range.
     *
     * @param startDate The start date
     * @param endDate The end date
     * @return A flux of risk assessments
     */
    Flux<RiskAssessment> findByAssessmentDateBetween(LocalDateTime startDate, LocalDateTime endDate);

    /**
     * Find risk assessments by assessment agent.
     *
     * @param assessmentAgent The agent who performed the assessment
     * @return A flux of risk assessments
     */
    Flux<RiskAssessment> findByAssessmentAgent(String assessmentAgent);

    /**
     * Find risk assessments that need to be reassessed.
     *
     * @param currentDate The current date
     * @return A flux of risk assessments
     */
    Flux<RiskAssessment> findByNextAssessmentDateBefore(LocalDateTime currentDate);

    /**
     * Find risk assessments with a score above a threshold.
     *
     * @param threshold The score threshold
     * @return A flux of risk assessments
     */
    Flux<RiskAssessment> findByRiskScoreGreaterThanEqual(Integer threshold);

    /**
     * Find the latest risk assessment for a party.
     *
     * @param partyId The ID of the party
     * @return A mono with the latest risk assessment
     */
    Mono<RiskAssessment> findFirstByPartyIdOrderByAssessmentDateDesc(UUID partyId);
}