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
import io.fireflysoftware.core.kycb.interfaces.dtos.aml.v1.AmlScreeningDTO;
import reactor.core.publisher.Mono;
import java.util.UUID;

/**
 * Service interface for AML screening operations.
 */
public interface AmlScreeningService {
    /**
     * Retrieves all AML screenings based on specified filter criteria.
     *
     * @param filterRequest The object containing filter criteria to apply for retrieving AML screenings.
     * @return A {@link Mono} containing a paginated response of AML screening DTOs that match the filter criteria.
     */
    Mono<PaginationResponse<AmlScreeningDTO>> findAll(FilterRequest<AmlScreeningDTO> filterRequest);

    /**
     * Creates a new AML screening entry based on the provided data transfer object.
     *
     * @param dto The AmlScreeningDTO containing data to create a new AML screening record
     * @return A Mono containing the created AmlScreeningDTO
     */
    Mono<AmlScreeningDTO> create(AmlScreeningDTO dto);

    /**
     * Retrieves an AmlScreeningDTO by its unique identifier.
     *
     * @param amlScreeningId The ID of the AmlScreening to retrieve.
     * @return A Mono containing the AmlScreeningDTO if found, otherwise an empty mono.
     */
    Mono<AmlScreeningDTO> getById(UUID amlScreeningId);

    /**
     * Updates an existing AmlScreening entry with new data provided in the DTO.
     *
     * @param amlScreeningId The ID of the AmlScreening to be updated.
     * @param dto A DTO containing the fields to update for the AmlScreening.
     * @return A Mono containing the updated AmlScreeningDTO if successful.
     */
    Mono<AmlScreeningDTO> update(UUID amlScreeningId, AmlScreeningDTO dto);

    /**
     * Deletes an AML Screening by its ID.
     *
     * @param amlScreeningId The ID of the AML Screening to delete.
     * @return A {@link Mono<Void>} indicating completion of the deletion operation.
     */
    Mono<Void> delete(UUID amlScreeningId);
}
