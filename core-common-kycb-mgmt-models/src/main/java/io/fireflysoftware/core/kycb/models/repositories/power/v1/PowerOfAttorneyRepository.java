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


package io.fireflysoftware.core.kycb.models.repositories.power.v1;

import io.fireflysoftware.core.kycb.models.entities.power.v1.PowerOfAttorney;
import io.fireflysoftware.core.kycb.interfaces.enums.power.v1.PowerTypeEnum;
import io.fireflysoftware.core.kycb.models.repositories.BaseRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Repository for power of attorney operations.
 */
@Repository
public interface PowerOfAttorneyRepository extends BaseRepository<PowerOfAttorney, UUID> {

    /**
     * Find powers of attorney by party ID.
     *
     * @param partyId The ID of the party
     * @return A flux of powers of attorney
     */
    Flux<PowerOfAttorney> findByPartyId(UUID partyId);

    /**
     * Find powers of attorney by attorney ID.
     *
     * @param attorneyId The ID of the attorney
     * @return A flux of powers of attorney
     */
    Flux<PowerOfAttorney> findByAttorneyId(UUID attorneyId);

    /**
     * Find powers of attorney by corporate document ID.
     *
     * @param corporateDocumentId The ID of the corporate document
     * @return A flux of powers of attorney
     */
    Flux<PowerOfAttorney> findByCorporateDocumentId(UUID corporateDocumentId);

    /**
     * Find powers of attorney by power type.
     *
     * @param powerTypeEnum The type of power
     * @return A flux of powers of attorney
     */
    Flux<PowerOfAttorney> findByPowerType(PowerTypeEnum powerTypeEnum);

    /**
     * Find powers of attorney by verification status.
     *
     * @param isVerified The verification status
     * @return A flux of powers of attorney
     */
    Flux<PowerOfAttorney> findByIsVerified(Boolean isVerified);

    /**
     * Find powers of attorney by POA completion status.
     *
     * @param isPoaCompleted The POA completion status
     * @return A flux of powers of attorney
     */
    Flux<PowerOfAttorney> findByIsPoaCompleted(Boolean isPoaCompleted);

    /**
     * Find powers of attorney that are about to expire.
     *
     * @param expiryDate The expiry date threshold
     * @return A flux of powers of attorney
     */
    Flux<PowerOfAttorney> findByExpiryDateBefore(LocalDateTime expiryDate);

    /**
     * Find active powers of attorney (not expired).
     *
     * @param currentDate The current date
     * @return A flux of powers of attorney
     */
    Flux<PowerOfAttorney> findByExpiryDateAfterOrExpiryDateIsNull(LocalDateTime currentDate);
}
