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


package io.fireflysoftware.core.kycb.models.repositories.kyb.v1;

import io.fireflysoftware.core.kycb.models.entities.kyb.v1.KybVerification;
import io.fireflysoftware.core.kycb.interfaces.enums.risk.v1.RiskLevelEnum;
import io.fireflysoftware.core.kycb.interfaces.enums.verification.v1.VerificationStatusEnum;
import io.fireflysoftware.core.kycb.models.repositories.BaseRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Repository for KYB verification operations.
 */
@Repository
public interface KybVerificationRepository extends BaseRepository<KybVerification, UUID> {

    /**
     * Find KYB verifications by party ID.
     *
     * @param partyId The ID of the party
     * @return A flux of KYB verifications
     */
    Flux<KybVerification> findByPartyId(UUID partyId);

    /**
     * Find KYB verifications by verification status.
     *
     * @param verificationStatusEnum The verification status
     * @return A flux of KYB verifications
     */
    Flux<KybVerification> findByVerificationStatus(VerificationStatusEnum verificationStatusEnum);

    /**
     * Find KYB verifications by risk level.
     *
     * @param riskLevelEnum The risk level
     * @return A flux of KYB verifications
     */
    Flux<KybVerification> findByRiskLevel(RiskLevelEnum riskLevelEnum);

    /**
     * Find KYB verifications with risk score above a threshold.
     *
     * @param threshold The risk score threshold
     * @return A flux of KYB verifications
     */
    Flux<KybVerification> findByRiskScoreGreaterThanEqual(Integer threshold);

    /**
     * Find KYB verifications by verification date range.
     *
     * @param startDate The start date
     * @param endDate The end date
     * @return A flux of KYB verifications
     */
    Flux<KybVerification> findByVerificationDateBetween(LocalDateTime startDate, LocalDateTime endDate);

    /**
     * Find KYB verifications that need review.
     *
     * @param currentDate The current date
     * @return A flux of KYB verifications
     */
    Flux<KybVerification> findByNextReviewDateBefore(LocalDateTime currentDate);

    /**
     * Find KYB verifications by mercantile registry verification status.
     *
     * @param verified The verification status
     * @return A flux of KYB verifications
     */
    Flux<KybVerification> findByMercantileRegistryVerified(Boolean verified);

    /**
     * Find KYB verifications by deed of incorporation verification status.
     *
     * @param verified The verification status
     * @return A flux of KYB verifications
     */
    Flux<KybVerification> findByDeedOfIncorporationVerified(Boolean verified);

    /**
     * Find KYB verifications by business structure verification status.
     *
     * @param verified The verification status
     * @return A flux of KYB verifications
     */
    Flux<KybVerification> findByBusinessStructureVerified(Boolean verified);

    /**
     * Find KYB verifications by UBO verification status.
     *
     * @param verified The verification status
     * @return A flux of KYB verifications
     */
    Flux<KybVerification> findByUboVerified(Boolean verified);

    /**
     * Find the latest KYB verification for a party.
     *
     * @param partyId The ID of the party
     * @return A mono with the latest KYB verification
     */
    Mono<KybVerification> findFirstByPartyIdOrderByVerificationDateDesc(UUID partyId);
}
