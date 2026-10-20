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


package io.fireflysoftware.core.kycb.core.services.expected.v1;

import org.fireflyframework.core.filters.FilterRequest;
import org.fireflyframework.core.queries.PaginationResponse;
import io.fireflysoftware.core.kycb.interfaces.dtos.expected.v1.ExpectedActivityDTO;
import reactor.core.publisher.Mono;
import java.util.UUID;

/**
 * Service interface for expected activity operations.
 */
public interface ExpectedActivityService {
    /**
     * Retrieves all expected activities based on specified filter criteria.
     *
     * @param filterRequest The object containing filter criteria to apply for retrieving expected activities.
     * @return A {@link Mono} containing a paginated response of expected activity DTOs that match the filter criteria.
     */
    Mono<PaginationResponse<ExpectedActivityDTO>> findAll(FilterRequest<ExpectedActivityDTO> filterRequest);

    /**
     * Creates a new expected activity entry based on the provided data transfer object.
     *
     * @param dto The ExpectedActivityDTO containing data to create a new expected activity record
     * @return A Mono containing the created ExpectedActivityDTO
     */
    Mono<ExpectedActivityDTO> create(ExpectedActivityDTO dto);

    /**
     * Retrieves an ExpectedActivityDTO by its unique identifier.
     *
     * @param activityId The ID of the ExpectedActivity to retrieve.
     * @return A Mono containing the ExpectedActivityDTO if found, otherwise an empty mono.
     */
    Mono<ExpectedActivityDTO> getById(UUID activityId);

    /**
     * Updates an existing ExpectedActivity entry with new data provided in the DTO.
     *
     * @param activityId The ID of the ExpectedActivity to be updated.
     * @param dto A DTO containing the fields to update for the ExpectedActivity.
     * @return A Mono containing the updated ExpectedActivityDTO if successful.
     */
    Mono<ExpectedActivityDTO> update(UUID activityId, ExpectedActivityDTO dto);

    /**
     * Deletes an Expected Activity by its ID.
     *
     * @param activityId The ID of the Expected Activity to delete.
     * @return A {@link Mono<Void>} indicating completion of the deletion operation.
     */
    Mono<Void> delete(UUID activityId);
}
