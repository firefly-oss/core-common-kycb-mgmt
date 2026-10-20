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


package io.fireflysoftware.core.kycb.core.services.location.v1;

import org.fireflyframework.core.filters.FilterRequest;
import org.fireflyframework.core.queries.PaginationResponse;
import io.fireflysoftware.core.kycb.interfaces.dtos.location.v1.BusinessLocationDTO;
import reactor.core.publisher.Mono;
import java.util.UUID;

/**
 * Service interface for business location operations.
 */
public interface BusinessLocationService {
    /**
     * Retrieves all business locations based on specified filter criteria.
     *
     * @param filterRequest The object containing filter criteria to apply for retrieving business locations.
     * @return A {@link Mono} containing a paginated response of business location DTOs that match the filter criteria.
     */
    Mono<PaginationResponse<BusinessLocationDTO>> findAll(FilterRequest<BusinessLocationDTO> filterRequest);

    /**
     * Creates a new business location entry based on the provided data transfer object.
     *
     * @param dto The BusinessLocationDTO containing data to create a new business location record
     * @return A Mono containing the created BusinessLocationDTO
     */
    Mono<BusinessLocationDTO> create(BusinessLocationDTO dto);

    /**
     * Retrieves a BusinessLocationDTO by its unique identifier.
     *
     * @param locationId The ID of the BusinessLocation to retrieve.
     * @return A Mono containing the BusinessLocationDTO if found, otherwise an empty mono.
     */
    Mono<BusinessLocationDTO> getById(UUID locationId);

    /**
     * Updates an existing BusinessLocation entry with new data provided in the DTO.
     *
     * @param locationId The ID of the BusinessLocation to be updated.
     * @param dto A DTO containing the fields to update for the BusinessLocation.
     * @return A Mono containing the updated BusinessLocationDTO if successful.
     */
    Mono<BusinessLocationDTO> update(UUID locationId, BusinessLocationDTO dto);

    /**
     * Deletes a Business Location by its ID.
     *
     * @param locationId The ID of the Business Location to delete.
     * @return A {@link Mono<Void>} indicating completion of the deletion operation.
     */
    Mono<Void> delete(UUID locationId);
}
