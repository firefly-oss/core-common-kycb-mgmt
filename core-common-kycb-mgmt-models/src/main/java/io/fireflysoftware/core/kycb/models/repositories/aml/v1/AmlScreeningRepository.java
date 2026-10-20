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

import io.fireflysoftware.core.kycb.models.entities.aml.v1.AmlScreening;
import io.fireflysoftware.core.kycb.interfaces.enums.screening.v1.ScreeningResultEnum;
import io.fireflysoftware.core.kycb.interfaces.enums.screening.v1.ScreeningTypeEnum;
import io.fireflysoftware.core.kycb.models.repositories.BaseRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Repository for AML screening operations.
 */
@Repository
public interface AmlScreeningRepository extends BaseRepository<AmlScreening, UUID> {

    /**
     * Find AML screenings by party ID.
     *
     * @param partyId The ID of the party
     * @return A flux of AML screenings
     */
    Flux<AmlScreening> findByPartyId(UUID partyId);

    /**
     * Find AML screenings by screening type.
     *
     * @param screeningTypeEnum The type of screening
     * @return A flux of AML screenings
     */
    Flux<AmlScreening> findByScreeningType(ScreeningTypeEnum screeningTypeEnum);

    /**
     * Find AML screenings by screening result.
     *
     * @param screeningResultEnum The result of screening
     * @return A flux of AML screenings
     */
    Flux<AmlScreening> findByScreeningResult(ScreeningResultEnum screeningResultEnum);

    /**
     * Find AML screenings by matches found flag.
     *
     * @param matchesFound The matches found flag
     * @return A flux of AML screenings
     */
    Flux<AmlScreening> findByMatchesFound(Boolean matchesFound);

    /**
     * Find AML screenings by screening date range.
     *
     * @param startDate The start date
     * @param endDate The end date
     * @return A flux of AML screenings
     */
    Flux<AmlScreening> findByScreeningDateBetween(LocalDateTime startDate, LocalDateTime endDate);

    /**
     * Find AML screenings that need to be rescreened.
     *
     * @param currentDate The current date
     * @return A flux of AML screenings
     */
    Flux<AmlScreening> findByNextScreeningDateBefore(LocalDateTime currentDate);

    /**
     * Find the latest AML screening for a party.
     *
     * @param partyId The ID of the party
     * @return A mono with the latest AML screening
     */
    Mono<AmlScreening> findFirstByPartyIdOrderByScreeningDateDesc(UUID partyId);
}