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


package io.fireflysoftware.core.kycb.core.services.compliance.v1;

import org.fireflyframework.core.filters.FilterRequest;
import org.fireflyframework.core.queries.PaginationResponse;
import io.fireflysoftware.core.kycb.interfaces.dtos.compliance.v1.ComplianceCaseDTO;
import reactor.core.publisher.Mono;
import java.util.UUID;

/**
 * Service interface for compliance case operations.
 */
public interface ComplianceCaseService {
    /**
     * Retrieves all compliance cases based on specified filter criteria.
     *
     * @param filterRequest The object containing filter criteria to apply for retrieving compliance cases.
     * @return A {@link Mono} containing a paginated response of compliance case DTOs that match the filter criteria.
     */
    Mono<PaginationResponse<ComplianceCaseDTO>> findAll(FilterRequest<ComplianceCaseDTO> filterRequest);

    /**
     * Creates a new compliance case entry based on the provided data transfer object.
     *
     * @param dto The ComplianceCaseDTO containing data to create a new compliance case record
     * @return A Mono containing the created ComplianceCaseDTO
     */
    Mono<ComplianceCaseDTO> create(ComplianceCaseDTO dto);

    /**
     * Retrieves a ComplianceCaseDTO by its unique identifier.
     *
     * @param caseId The ID of the ComplianceCase to retrieve.
     * @return A Mono containing the ComplianceCaseDTO if found, otherwise an empty mono.
     */
    Mono<ComplianceCaseDTO> getById(UUID caseId);

    /**
     * Updates an existing ComplianceCase entry with new data provided in the DTO.
     *
     * @param caseId The ID of the ComplianceCase to be updated.
     * @param dto A DTO containing the fields to update for the ComplianceCase.
     * @return A Mono containing the updated ComplianceCaseDTO if successful.
     */
    Mono<ComplianceCaseDTO> update(UUID caseId, ComplianceCaseDTO dto);

    /**
     * Deletes a Compliance Case by its ID.
     *
     * @param caseId The ID of the Compliance Case to delete.
     * @return A {@link Mono<Void>} indicating completion of the deletion operation.
     */
    Mono<Void> delete(UUID caseId);
}
