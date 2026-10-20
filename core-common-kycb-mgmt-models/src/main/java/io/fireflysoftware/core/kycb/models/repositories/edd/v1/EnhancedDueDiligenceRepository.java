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


package io.fireflysoftware.core.kycb.models.repositories.edd.v1;

import io.fireflysoftware.core.kycb.models.entities.edd.v1.EnhancedDueDiligence;
import io.fireflysoftware.core.kycb.interfaces.enums.edd.v1.EddReasonEnum;
import io.fireflysoftware.core.kycb.interfaces.enums.edd.v1.EddStatusEnum;
import io.fireflysoftware.core.kycb.models.repositories.BaseRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Repository for enhanced due diligence operations.
 */
@Repository
public interface EnhancedDueDiligenceRepository extends BaseRepository<EnhancedDueDiligence, UUID> {

    /**
     * Find enhanced due diligence records by KYC verification ID.
     *
     * @param kycVerificationId The ID of the KYC verification
     * @return A flux of enhanced due diligence records
     */
    Flux<EnhancedDueDiligence> findByKycVerificationId(UUID kycVerificationId);

    /**
     * Find enhanced due diligence records by EDD reason.
     *
     * @param eddReasonEnum The reason for EDD
     * @return A flux of enhanced due diligence records
     */
    Flux<EnhancedDueDiligence> findByEddReason(EddReasonEnum eddReasonEnum);

    /**
     * Find enhanced due diligence records by EDD status.
     *
     * @param eddStatusEnum The status of EDD
     * @return A flux of enhanced due diligence records
     */
    Flux<EnhancedDueDiligence> findByEddStatus(EddStatusEnum eddStatusEnum);

    /**
     * Find enhanced due diligence records by approving authority.
     *
     * @param approvingAuthority The approving authority
     * @return A flux of enhanced due diligence records
     */
    Flux<EnhancedDueDiligence> findByApprovingAuthority(String approvingAuthority);

    /**
     * Find enhanced due diligence records by internal committee approval status.
     *
     * @param internalCommitteeApproval The internal committee approval status
     * @return A flux of enhanced due diligence records
     */
    Flux<EnhancedDueDiligence> findByInternalCommitteeApproval(Boolean internalCommitteeApproval);

    /**
     * Find enhanced due diligence records by approval date range.
     *
     * @param startDate The start date
     * @param endDate The end date
     * @return A flux of enhanced due diligence records
     */
    Flux<EnhancedDueDiligence> findByApprovalDateBetween(LocalDateTime startDate, LocalDateTime endDate);

    /**
     * Find enhanced due diligence records by completion date range.
     *
     * @param startDate The start date
     * @param endDate The end date
     * @return A flux of enhanced due diligence records
     */
    Flux<EnhancedDueDiligence> findByCompletionDateBetween(LocalDateTime startDate, LocalDateTime endDate);

    /**
     * Find enhanced due diligence records by completed by.
     *
     * @param completedBy The user who completed the EDD
     * @return A flux of enhanced due diligence records
     */
    Flux<EnhancedDueDiligence> findByCompletedBy(String completedBy);

    /**
     * Find enhanced due diligence records by KYC verification ID and EDD status.
     *
     * @param kycVerificationId The ID of the KYC verification
     * @param eddStatusEnum The status of EDD
     * @return A flux of enhanced due diligence records
     */
    Flux<EnhancedDueDiligence> findByKycVerificationIdAndEddStatus(UUID kycVerificationId, EddStatusEnum eddStatusEnum);

    /**
     * Find the latest enhanced due diligence record for a KYC verification.
     *
     * @param kycVerificationId The ID of the KYC verification
     * @return A mono with the latest enhanced due diligence record
     */
    Mono<EnhancedDueDiligence> findFirstByKycVerificationIdOrderByDateCreatedDesc(UUID kycVerificationId);
}