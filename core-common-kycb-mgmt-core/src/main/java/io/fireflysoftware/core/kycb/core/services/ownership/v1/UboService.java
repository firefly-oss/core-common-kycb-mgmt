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


package io.fireflysoftware.core.kycb.core.services.ownership.v1;

import org.fireflyframework.core.filters.FilterRequest;
import org.fireflyframework.core.queries.PaginationResponse;
import io.fireflysoftware.core.kycb.interfaces.dtos.ownership.v1.UboDTO;
import reactor.core.publisher.Mono;
import java.util.UUID;

/**
 * Service interface for UBO (Ultimate Beneficial Owner) operations.
 */
public interface UboService {
    /**
     * Retrieves all UBOs based on specified filter criteria.
     *
     * @param filterRequest The object containing filter criteria to apply for retrieving UBOs.
     * @return A {@link Mono} containing a paginated response of UBO DTOs that match the filter criteria.
     */
    Mono<PaginationResponse<UboDTO>> findAll(FilterRequest<UboDTO> filterRequest);

    /**
     * Creates a new UBO entry based on the provided data transfer object.
     *
     * @param dto The UboDTO containing data to create a new UBO record
     * @return A Mono containing the created UboDTO
     */
    Mono<UboDTO> create(UboDTO dto);

    /**
     * Retrieves a UboDTO by its unique identifier.
     *
     * @param uboId The ID of the UBO to retrieve.
     * @return A Mono containing the UboDTO if found, otherwise an empty mono.
     */
    Mono<UboDTO> getById(UUID uboId);

    /**
     * Updates an existing UBO entry with new data provided in the DTO.
     *
     * @param uboId The ID of the UBO to be updated.
     * @param dto A DTO containing the fields to update for the UBO.
     * @return A Mono containing the updated UboDTO if successful.
     */
    Mono<UboDTO> update(UUID uboId, UboDTO dto);

    /**
     * Deletes a UBO by its ID.
     *
     * @param uboId The ID of the UBO to delete.
     * @return A {@link Mono<Void>} indicating completion of the deletion operation.
     */
    Mono<Void> delete(UUID uboId);
}
