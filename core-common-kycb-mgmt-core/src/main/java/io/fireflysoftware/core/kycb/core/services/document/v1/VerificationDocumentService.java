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


package io.fireflysoftware.core.kycb.core.services.document.v1;

import org.fireflyframework.core.filters.FilterRequest;
import org.fireflyframework.core.queries.PaginationResponse;
import io.fireflysoftware.core.kycb.interfaces.dtos.document.v1.VerificationDocumentDTO;
import reactor.core.publisher.Mono;
import java.util.UUID;

public interface VerificationDocumentService {
    /**
     * Retrieves all verification documents based on specified filter criteria.
     *
     * @param filterRequest The object containing filter criteria to apply for retrieving verification documents.
     * @return A {@link Mono} containing a paginated response of verification document DTOs that match the filter criteria.
     */
    Mono<PaginationResponse<VerificationDocumentDTO>> findAll(FilterRequest<VerificationDocumentDTO> filterRequest);

    /**
     * Creates a new verification document entry based on the provided data transfer object.
     *
     * @param dto The VerificationDocumentDTO containing data to create a new verification document record
     * @return A Mono containing the created VerificationDocumentDTO
     */
    Mono<VerificationDocumentDTO> create(VerificationDocumentDTO dto);

    /**
     * Retrieves a VerificationDocumentDTO by its unique identifier.
     *
     * @param verificationDocumentId The ID of the VerificationDocument to retrieve.
     * @return A Mono containing the VerificationDocumentDTO if found, otherwise an empty mono.
     */
    Mono<VerificationDocumentDTO> getById(UUID verificationDocumentId);

    /**
     * Updates an existing VerificationDocument entry with new data provided in the DTO.
     *
     * @param verificationDocumentId The ID of the VerificationDocument to be updated.
     * @param dto A DTO containing the fields to update for the VerificationDocument.
     * @return A Mono containing the updated VerificationDocumentDTO if successful.
     */
    Mono<VerificationDocumentDTO> update(UUID verificationDocumentId, VerificationDocumentDTO dto);

    /**
     * Deletes a Verification Document by its ID.
     *
     * @param verificationDocumentId The ID of the Verification Document to delete.
     * @return A {@link Mono<Void>} indicating completion of the deletion operation.
     */
    Mono<Void> delete(UUID verificationDocumentId);
}
