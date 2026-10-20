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


package io.fireflysoftware.core.kycb.core.services.kyb.v1;

import org.fireflyframework.core.filters.FilterRequest;
import org.fireflyframework.core.queries.PaginationResponse;
import io.fireflysoftware.core.kycb.interfaces.dtos.kyb.v1.KybVerificationDTO;
import reactor.core.publisher.Mono;
import java.util.UUID;

public interface KybVerificationService {
    /**
     * Retrieves all KYB verification records based on specified filter criteria.
     *
     * @param filterRequest The object containing filter criteria to apply for retrieving KYB verifications.
     * @return A {@link Mono} containing a paginated response of KYB verification DTOs that match the filter criteria.
     */
    Mono<PaginationResponse<KybVerificationDTO>> findAll(FilterRequest<KybVerificationDTO> filterRequest);

    /**
     * Creates a new KYB verification entry based on the provided data transfer object.
     *
     * @param dto The KybVerificationDTO containing data to create a new KYB verification record
     * @return A Mono containing the created KybVerificationDTO
     */
    Mono<KybVerificationDTO> create(KybVerificationDTO dto);

    /**
     * Retrieves a KybVerificationDTO by its unique identifier.
     *
     * @param kybVerificationId The ID of the KybVerification to retrieve.
     * @return A Mono containing the KybVerificationDTO if found, otherwise an empty mono.
     */
    Mono<KybVerificationDTO> getById(UUID kybVerificationId);

    /**
     * Updates an existing KybVerification entry with new data provided in the DTO.
     *
     * @param kybVerificationId The ID of the KybVerification to be updated.
     * @param dto A DTO containing the fields to update for the KybVerification.
     * @return A Mono containing the updated KybVerificationDTO if successful.
     */
    Mono<KybVerificationDTO> update(UUID kybVerificationId, KybVerificationDTO dto);

    /**
     * Deletes a KYB Verification by its ID.
     *
     * @param kybVerificationId The ID of the KYB Verification to delete.
     * @return A {@link Mono<Void>} indicating completion of the deletion operation.
     */
    Mono<Void> delete(UUID kybVerificationId);

}
