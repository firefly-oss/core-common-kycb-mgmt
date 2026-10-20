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


package io.fireflysoftware.core.kycb.core.services.economic.v1;

import org.fireflyframework.core.filters.FilterRequest;
import org.fireflyframework.core.queries.PaginationResponse;
import io.fireflysoftware.core.kycb.interfaces.dtos.economic.v1.EconomicActivityDTO;
import reactor.core.publisher.Mono;
import java.util.UUID;

/**
 * Service interface for economic activity operations.
 */
public interface EconomicActivityService {
    /**
     * Retrieves all economic activities based on specified filter criteria.
     *
     * @param filterRequest The object containing filter criteria to apply for retrieving economic activities.
     * @return A {@link Mono} containing a paginated response of economic activity DTOs that match the filter criteria.
     */
    Mono<PaginationResponse<EconomicActivityDTO>> findAll(FilterRequest<EconomicActivityDTO> filterRequest);

    /**
     * Creates a new economic activity entry based on the provided data transfer object.
     *
     * @param dto The EconomicActivityDTO containing data to create a new economic activity record
     * @return A Mono containing the created EconomicActivityDTO
     */
    Mono<EconomicActivityDTO> create(EconomicActivityDTO dto);

    /**
     * Retrieves an EconomicActivityDTO by its unique identifier.
     *
     * @param activityId The ID of the EconomicActivity to retrieve.
     * @return A Mono containing the EconomicActivityDTO if found, otherwise an empty mono.
     */
    Mono<EconomicActivityDTO> getById(UUID activityId);

    /**
     * Updates an existing EconomicActivity entry with new data provided in the DTO.
     *
     * @param activityId The ID of the EconomicActivity to be updated.
     * @param dto A DTO containing the fields to update for the EconomicActivity.
     * @return A Mono containing the updated EconomicActivityDTO if successful.
     */
    Mono<EconomicActivityDTO> update(UUID activityId, EconomicActivityDTO dto);

    /**
     * Deletes an Economic Activity by its ID.
     *
     * @param activityId The ID of the Economic Activity to delete.
     * @return A {@link Mono<Void>} indicating completion of the deletion operation.
     */
    Mono<Void> delete(UUID activityId);
}
