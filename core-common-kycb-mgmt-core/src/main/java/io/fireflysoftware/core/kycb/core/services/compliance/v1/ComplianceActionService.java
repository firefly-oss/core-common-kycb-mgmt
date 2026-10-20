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
import io.fireflysoftware.core.kycb.interfaces.dtos.compliance.v1.ComplianceActionDTO;
import reactor.core.publisher.Mono;
import java.util.UUID;

/**
 * Service interface for compliance action operations.
 */
public interface ComplianceActionService {
    /**
     * Retrieves all compliance actions based on specified filter criteria.
     *
     * @param filterRequest The object containing filter criteria to apply for retrieving compliance actions.
     * @return A {@link Mono} containing a paginated response of compliance action DTOs that match the filter criteria.
     */
    Mono<PaginationResponse<ComplianceActionDTO>> findAll(FilterRequest<ComplianceActionDTO> filterRequest);

    /**
     * Creates a new compliance action entry based on the provided data transfer object.
     *
     * @param dto The ComplianceActionDTO containing data to create a new compliance action record
     * @return A Mono containing the created ComplianceActionDTO
     */
    Mono<ComplianceActionDTO> create(ComplianceActionDTO dto);

    /**
     * Retrieves a ComplianceActionDTO by its unique identifier.
     *
     * @param actionId The ID of the ComplianceAction to retrieve.
     * @return A Mono containing the ComplianceActionDTO if found, otherwise an empty mono.
     */
    Mono<ComplianceActionDTO> getById(UUID actionId);

    /**
     * Updates an existing ComplianceAction entry with new data provided in the DTO.
     *
     * @param actionId The ID of the ComplianceAction to be updated.
     * @param dto A DTO containing the fields to update for the ComplianceAction.
     * @return A Mono containing the updated ComplianceActionDTO if successful.
     */
    Mono<ComplianceActionDTO> update(UUID actionId, ComplianceActionDTO dto);

    /**
     * Deletes a Compliance Action by its ID.
     *
     * @param actionId The ID of the Compliance Action to delete.
     * @return A {@link Mono<Void>} indicating completion of the deletion operation.
     */
    Mono<Void> delete(UUID actionId);
}
