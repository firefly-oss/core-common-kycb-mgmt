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


package io.fireflysoftware.core.kycb.core.services.source.v1;

import org.fireflyframework.core.filters.FilterRequest;
import org.fireflyframework.core.queries.PaginationResponse;
import io.fireflysoftware.core.kycb.interfaces.dtos.source.v1.SourceOfFundsDTO;
import reactor.core.publisher.Mono;
import java.util.UUID;

/**
 * Service interface for source of funds operations.
 */
public interface SourceOfFundsService {
    /**
     * Retrieves all sources of funds based on specified filter criteria.
     *
     * @param filterRequest The object containing filter criteria to apply for retrieving sources of funds.
     * @return A {@link Mono} containing a paginated response of source of funds DTOs that match the filter criteria.
     */
    Mono<PaginationResponse<SourceOfFundsDTO>> findAll(FilterRequest<SourceOfFundsDTO> filterRequest);

    /**
     * Creates a new source of funds entry based on the provided data transfer object.
     *
     * @param dto The SourceOfFundsDTO containing data to create a new source of funds record
     * @return A Mono containing the created SourceOfFundsDTO
     */
    Mono<SourceOfFundsDTO> create(SourceOfFundsDTO dto);

    /**
     * Retrieves a SourceOfFundsDTO by its unique identifier.
     *
     * @param sourceId The ID of the SourceOfFunds to retrieve.
     * @return A Mono containing the SourceOfFundsDTO if found, otherwise an empty mono.
     */
    Mono<SourceOfFundsDTO> getById(UUID sourceId);

    /**
     * Updates an existing SourceOfFunds entry with new data provided in the DTO.
     *
     * @param sourceId The ID of the SourceOfFunds to be updated.
     * @param dto A DTO containing the fields to update for the SourceOfFunds.
     * @return A Mono containing the updated SourceOfFundsDTO if successful.
     */
    Mono<SourceOfFundsDTO> update(UUID sourceId, SourceOfFundsDTO dto);

    /**
     * Deletes a Source of Funds by its ID.
     *
     * @param sourceId The ID of the Source of Funds to delete.
     * @return A {@link Mono<Void>} indicating completion of the deletion operation.
     */
    Mono<Void> delete(UUID sourceId);
}
