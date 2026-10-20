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


package io.fireflysoftware.core.kycb.core.services.aml.v1;

import org.fireflyframework.core.filters.FilterRequest;
import org.fireflyframework.core.queries.PaginationResponse;
import io.fireflysoftware.core.kycb.interfaces.dtos.aml.v1.AmlMatchDTO;
import reactor.core.publisher.Mono;
import java.util.UUID;

/**
 * Service interface for AML match operations.
 */
public interface AmlMatchService {
    /**
     * Retrieves all AML matches based on specified filter criteria.
     *
     * @param filterRequest The object containing filter criteria to apply for retrieving AML matches.
     * @return A {@link Mono} containing a paginated response of AML match DTOs that match the filter criteria.
     */
    Mono<PaginationResponse<AmlMatchDTO>> findAll(FilterRequest<AmlMatchDTO> filterRequest);

    /**
     * Creates a new AML match entry based on the provided data transfer object.
     *
     * @param dto The AmlMatchDTO containing data to create a new AML match record
     * @return A Mono containing the created AmlMatchDTO
     */
    Mono<AmlMatchDTO> create(AmlMatchDTO dto);

    /**
     * Retrieves an AmlMatchDTO by its unique identifier.
     *
     * @param amlMatchId The ID of the AmlMatch to retrieve.
     * @return A Mono containing the AmlMatchDTO if found, otherwise an empty mono.
     */
    Mono<AmlMatchDTO> getById(UUID amlMatchId);

    /**
     * Updates an existing AmlMatch entry with new data provided in the DTO.
     *
     * @param amlMatchId The ID of the AmlMatch to be updated.
     * @param dto A DTO containing the fields to update for the AmlMatch.
     * @return A Mono containing the updated AmlMatchDTO if successful.
     */
    Mono<AmlMatchDTO> update(UUID amlMatchId, AmlMatchDTO dto);

    /**
     * Deletes an AML Match by its ID.
     *
     * @param amlMatchId The ID of the AML Match to delete.
     * @return A {@link Mono<Void>} indicating completion of the deletion operation.
     */
    Mono<Void> delete(UUID amlMatchId);
}
