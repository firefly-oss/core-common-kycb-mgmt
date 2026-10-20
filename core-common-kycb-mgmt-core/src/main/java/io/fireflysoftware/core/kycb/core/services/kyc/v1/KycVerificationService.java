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


package io.fireflysoftware.core.kycb.core.services.kyc.v1;

import org.fireflyframework.core.filters.FilterRequest;
import org.fireflyframework.core.queries.PaginationResponse;
import io.fireflysoftware.core.kycb.interfaces.dtos.kyc.v1.KycVerificationDTO;
import reactor.core.publisher.Mono;
import java.util.UUID;

public interface KycVerificationService {
    /**
     * Retrieves all KYC verification records based on specified filter criteria.
     *
     * @param filterRequest The object containing filter criteria to apply for retrieving KYC verifications.
     * @return A {@link Mono} containing a paginated response of KYC verification DTOs that match the filter criteria.
     */
    Mono<PaginationResponse<KycVerificationDTO>> findAll(FilterRequest<KycVerificationDTO> filterRequest);
    /**
     * Creates a new KYC verification entry based on the provided data transfer object.
     *
     * @param dto The KycVerificationDTO containing data to create a new KYC verification record
     * @return A Mono containing the created KycVerificationDTO
     */
    Mono<KycVerificationDTO> create(KycVerificationDTO dto);
    /**
     * Retrieves a KycVerificationDTO by its unique identifier.
     *
     * @param kycVerificationId The ID of the KycVerification to retrieve.
     * @return A Mono containing the KycVerificationDTO if found, otherwise an empty mono.
     */
    Mono<KycVerificationDTO> getById(UUID kycVerificationId);
    /**
     * Updates an existing KycVerification entry with new data provided in the DTO.
     *
     * @param kycVerificationId The ID of the KycVerification to be updated.
     * @param dto A DTO containing the fields to update for the KycVerification.
     * @return A Mono containing the updated KycVerificationDTO if successful.
     */
    Mono<KycVerificationDTO> update(UUID kycVerificationId, KycVerificationDTO dto);
    /**
     * Deletes a KYC Verification by its ID.
     *
     * @param kycVerificationId The ID of the KYC Verification to delete.
     * @return A {@link Mono<Void>} indicating completion of the deletion operation.
     */
    Mono<Void> delete(UUID kycVerificationId);

}
