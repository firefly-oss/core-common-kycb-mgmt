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


package io.fireflysoftware.core.kycb.models.repositories.economic.v1;

import io.fireflysoftware.core.kycb.models.entities.economic.v1.EconomicActivity;
import io.fireflysoftware.core.kycb.models.repositories.BaseRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.UUID;

/**
 * Repository for economic activity operations.
 */
@Repository
public interface EconomicActivityRepository extends BaseRepository<EconomicActivity, UUID> {

    /**
     * Find economic activities by party ID.
     *
     * @param partyId The ID of the party
     * @return A flux of economic activities
     */
    Flux<EconomicActivity> findByPartyId(UUID partyId);

    /**
     * Find economic activities by activity code.
     *
     * @param activityCode The activity code
     * @return A flux of economic activities
     */
    Flux<EconomicActivity> findByActivityCode(String activityCode);

    /**
     * Find primary economic activities.
     *
     * @param isPrimary The primary flag
     * @return A flux of economic activities
     */
    Flux<EconomicActivity> findByIsPrimary(Boolean isPrimary);

    /**
     * Find economic activities by sector code.
     *
     * @param sectorCode The sector code
     * @return A flux of economic activities
     */
    Flux<EconomicActivity> findBySectorCode(String sectorCode);

    /**
     * Find economic activities by subsector.
     *
     * @param subsector The subsector
     * @return A flux of economic activities
     */
    Flux<EconomicActivity> findBySubsector(String subsector);

    /**
     * Find high-risk economic activities.
     *
     * @param highRiskActivity The high-risk flag
     * @return A flux of economic activities
     */
    Flux<EconomicActivity> findByHighRiskActivity(Boolean highRiskActivity);

    /**
     * Find economic activities by geographic scope code.
     *
     * @param geographicScopeCode The geographic scope code
     * @return A flux of economic activities
     */
    Flux<EconomicActivity> findByGeographicScopeCode(String geographicScopeCode);

    /**
     * Find economic activities by export markets containing a specific country.
     *
     * @param countryCode The country code to search for
     * @return A flux of economic activities
     */
    Flux<EconomicActivity> findByExportMarketsContaining(String countryCode);

    /**
     * Find economic activities by import markets containing a specific country.
     *
     * @param countryCode The country code to search for
     * @return A flux of economic activities
     */
    Flux<EconomicActivity> findByImportMarketsContaining(String countryCode);

    /**
     * Find regulated economic activities.
     *
     * @param regulatedActivity The regulated activity flag
     * @return A flux of economic activities
     */
    Flux<EconomicActivity> findByRegulatedActivity(Boolean regulatedActivity);

    /**
     * Find the primary economic activity for a party.
     *
     * @param partyId The ID of the party
     * @return A mono with the primary economic activity
     */
    Mono<EconomicActivity> findByPartyIdAndIsPrimaryTrue(UUID partyId);
}
