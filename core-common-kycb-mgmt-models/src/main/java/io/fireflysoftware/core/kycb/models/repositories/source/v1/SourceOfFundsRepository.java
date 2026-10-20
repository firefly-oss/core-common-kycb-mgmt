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


package io.fireflysoftware.core.kycb.models.repositories.source.v1;

import io.fireflysoftware.core.kycb.models.entities.source.v1.SourceOfFunds;
import io.fireflysoftware.core.kycb.interfaces.enums.source.v1.SourceTypeEnum;
import io.fireflysoftware.core.kycb.models.repositories.BaseRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Repository for source of funds operations.
 */
@Repository
public interface SourceOfFundsRepository extends BaseRepository<SourceOfFunds, UUID> {

    /**
     * Find sources of funds by party ID.
     *
     * @param partyId The ID of the party
     * @return A flux of sources of funds
     */
    Flux<SourceOfFunds> findByPartyId(UUID partyId);

    /**
     * Find sources of funds by source type.
     *
     * @param sourceTypeEnum The type of source
     * @return A flux of sources of funds
     */
    Flux<SourceOfFunds> findBySourceType(SourceTypeEnum sourceTypeEnum);

    /**
     * Find sources of funds by verification status.
     *
     * @param isVerified The verification status
     * @return A flux of sources of funds
     */
    Flux<SourceOfFunds> findByIsVerified(Boolean isVerified);

    /**
     * Find sources of funds with estimated annual amount above a threshold.
     *
     * @param threshold The amount threshold
     * @return A flux of sources of funds
     */
    Flux<SourceOfFunds> findByEstimatedAnnualAmountGreaterThanEqual(BigDecimal threshold);

    /**
     * Find sources of funds by verification date range.
     *
     * @param startDate The start date
     * @param endDate The end date
     * @return A flux of sources of funds
     */
    Flux<SourceOfFunds> findByVerificationDateBetween(LocalDateTime startDate, LocalDateTime endDate);

    /**
     * Find sources of funds by currency.
     *
     * @param currency The currency code
     * @return A flux of sources of funds
     */
    Flux<SourceOfFunds> findByCurrency(String currency);

    /**
     * Find sources of funds by party ID and source type.
     *
     * @param partyId The ID of the party
     * @param sourceTypeEnum The type of source
     * @return A flux of sources of funds
     */
    Flux<SourceOfFunds> findByPartyIdAndSourceType(UUID partyId, SourceTypeEnum sourceTypeEnum);

    /**
     * Find the latest source of funds for a party.
     *
     * @param partyId The ID of the party
     * @return A mono with the latest source of funds
     */
    Mono<SourceOfFunds> findFirstByPartyIdOrderByDateCreatedDesc(UUID partyId);
}