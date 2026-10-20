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


package io.fireflysoftware.core.kycb.core.services.regulatory.v1;

import org.fireflyframework.core.filters.FilterRequest;
import org.fireflyframework.core.queries.PaginationResponse;
import io.fireflysoftware.core.kycb.interfaces.dtos.regulatory.v1.RegulatoryReportingDTO;
import reactor.core.publisher.Mono;
import java.util.UUID;

/**
 * Service interface for regulatory reporting operations.
 */
public interface RegulatoryReportingService {
    /**
     * Retrieves all regulatory reports based on specified filter criteria.
     *
     * @param filterRequest The object containing filter criteria to apply for retrieving regulatory reports.
     * @return A {@link Mono} containing a paginated response of regulatory report DTOs that match the filter criteria.
     */
    Mono<PaginationResponse<RegulatoryReportingDTO>> findAll(FilterRequest<RegulatoryReportingDTO> filterRequest);

    /**
     * Creates a new regulatory report entry based on the provided data transfer object.
     *
     * @param dto The RegulatoryReportingDTO containing data to create a new regulatory report record
     * @return A Mono containing the created RegulatoryReportingDTO
     */
    Mono<RegulatoryReportingDTO> create(RegulatoryReportingDTO dto);

    /**
     * Retrieves a RegulatoryReportingDTO by its unique identifier.
     *
     * @param reportId The ID of the RegulatoryReporting to retrieve.
     * @return A Mono containing the RegulatoryReportingDTO if found, otherwise an empty mono.
     */
    Mono<RegulatoryReportingDTO> getById(UUID reportId);

    /**
     * Updates an existing RegulatoryReporting entry with new data provided in the DTO.
     *
     * @param reportId The ID of the RegulatoryReporting to be updated.
     * @param dto A DTO containing the fields to update for the RegulatoryReporting.
     * @return A Mono containing the updated RegulatoryReportingDTO if successful.
     */
    Mono<RegulatoryReportingDTO> update(UUID reportId, RegulatoryReportingDTO dto);

    /**
     * Deletes a Regulatory Report by its ID.
     *
     * @param reportId The ID of the Regulatory Report to delete.
     * @return A {@link Mono<Void>} indicating completion of the deletion operation.
     */
    Mono<Void> delete(UUID reportId);
}
