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
import io.fireflysoftware.core.kycb.interfaces.dtos.document.v1.CorporateDocumentDTO;
import reactor.core.publisher.Mono;
import java.util.UUID;

/**
 * Service interface for corporate document operations.
 */
public interface CorporateDocumentService {
    /**
     * Retrieves all corporate documents based on specified filter criteria.
     *
     * @param filterRequest The object containing filter criteria to apply for retrieving corporate documents.
     * @return A {@link Mono} containing a paginated response of corporate document DTOs that match the filter criteria.
     */
    Mono<PaginationResponse<CorporateDocumentDTO>> findAll(FilterRequest<CorporateDocumentDTO> filterRequest);

    /**
     * Creates a new corporate document entry based on the provided data transfer object.
     *
     * @param dto The CorporateDocumentDTO containing data to create a new corporate document record
     * @return A Mono containing the created CorporateDocumentDTO
     */
    Mono<CorporateDocumentDTO> create(CorporateDocumentDTO dto);

    /**
     * Retrieves a CorporateDocumentDTO by its unique identifier.
     *
     * @param documentId The ID of the CorporateDocument to retrieve.
     * @return A Mono containing the CorporateDocumentDTO if found, otherwise an empty mono.
     */
    Mono<CorporateDocumentDTO> getById(UUID documentId);

    /**
     * Updates an existing CorporateDocument entry with new data provided in the DTO.
     *
     * @param documentId The ID of the CorporateDocument to be updated.
     * @param dto A DTO containing the fields to update for the CorporateDocument.
     * @return A Mono containing the updated CorporateDocumentDTO if successful.
     */
    Mono<CorporateDocumentDTO> update(UUID documentId, CorporateDocumentDTO dto);

    /**
     * Deletes a Corporate Document by its ID.
     *
     * @param documentId The ID of the Corporate Document to delete.
     * @return A {@link Mono<Void>} indicating completion of the deletion operation.
     */
    Mono<Void> delete(UUID documentId);
}
