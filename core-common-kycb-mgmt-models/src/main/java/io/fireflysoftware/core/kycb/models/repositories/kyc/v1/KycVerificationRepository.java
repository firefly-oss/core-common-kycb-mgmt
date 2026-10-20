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


package io.fireflysoftware.core.kycb.models.repositories.kyc.v1;

import io.fireflysoftware.core.kycb.models.entities.kyc.v1.KycVerification;
import io.fireflysoftware.core.kycb.models.repositories.BaseRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.UUID;

/**
 * Repository for KYC verification operations.
 */
@Repository
public interface KycVerificationRepository extends BaseRepository<KycVerification, UUID> {

    /**
     * Find KYC verifications by party ID.
     *
     * @param partyId The ID of the party
     * @return A flux of KYC verifications
     */
    Flux<KycVerification> findByPartyId(UUID partyId);

    /**
     * Find the latest KYC verification for a party.
     *
     * @param partyId The ID of the party
     * @return A mono with the latest KYC verification
     */
    Mono<KycVerification> findFirstByPartyIdOrderByVerificationDateDesc(UUID partyId);
}