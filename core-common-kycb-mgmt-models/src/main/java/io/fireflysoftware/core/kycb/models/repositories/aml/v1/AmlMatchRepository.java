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


package io.fireflysoftware.core.kycb.models.repositories.aml.v1;

import io.fireflysoftware.core.kycb.models.entities.aml.v1.AmlMatch;
import io.fireflysoftware.core.kycb.interfaces.enums.aml.v1.ListTypeEnum;
import io.fireflysoftware.core.kycb.interfaces.enums.resolution.v1.ResolutionStatusEnum;
import io.fireflysoftware.core.kycb.models.repositories.BaseRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Repository for AML match operations.
 */
@Repository
public interface AmlMatchRepository extends BaseRepository<AmlMatch, UUID> {

    /**
     * Find AML matches by AML screening ID.
     *
     * @param amlScreeningId The ID of the AML screening
     * @return A flux of AML matches
     */
    Flux<AmlMatch> findByAmlScreeningId(UUID amlScreeningId);

    /**
     * Find AML matches by list type.
     *
     * @param listTypeEnum The type of list
     * @return A flux of AML matches
     */
    Flux<AmlMatch> findByListType(ListTypeEnum listTypeEnum);

    /**
     * Find AML matches by resolution status.
     *
     * @param resolutionStatusEnum The status of resolution
     * @return A flux of AML matches
     */
    Flux<AmlMatch> findByResolutionStatus(ResolutionStatusEnum resolutionStatusEnum);

    /**
     * Find AML matches by resolution agent.
     *
     * @param resolutionAgent The agent who resolved the match
     * @return A flux of AML matches
     */
    Flux<AmlMatch> findByResolutionAgent(String resolutionAgent);

    /**
     * Find AML matches by resolution date range.
     *
     * @param startDate The start date
     * @param endDate The end date
     * @return A flux of AML matches
     */
    Flux<AmlMatch> findByResolutionDateBetween(LocalDateTime startDate, LocalDateTime endDate);

    /**
     * Find AML matches with a score above a threshold.
     *
     * @param threshold The score threshold
     * @return A flux of AML matches
     */
    Flux<AmlMatch> findByMatchScoreGreaterThanEqual(BigDecimal threshold);

    /**
     * Find unresolved AML matches.
     *
     * @return A flux of AML matches
     */
    Flux<AmlMatch> findByResolutionStatusIsNull();

    /**
     * Find AML matches by AML screening ID and list type.
     *
     * @param amlScreeningId The ID of the AML screening
     * @param listTypeEnum The type of list
     * @return A flux of AML matches
     */
    Flux<AmlMatch> findByAmlScreeningIdAndListType(UUID amlScreeningId, ListTypeEnum listTypeEnum);
}