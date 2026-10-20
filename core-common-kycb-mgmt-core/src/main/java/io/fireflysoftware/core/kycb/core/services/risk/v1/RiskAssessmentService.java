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


package io.fireflysoftware.core.kycb.core.services.risk.v1;

import org.fireflyframework.core.filters.FilterRequest;
import org.fireflyframework.core.queries.PaginationResponse;
import io.fireflysoftware.core.kycb.interfaces.dtos.risk.v1.RiskAssessmentDTO;
import reactor.core.publisher.Mono;
import java.util.UUID;

/**
 * Service interface for risk assessment operations.
 */
public interface RiskAssessmentService {
    /**
     * Retrieves all risk assessments based on specified filter criteria.
     *
     * @param filterRequest The object containing filter criteria to apply for retrieving risk assessments.
     * @return A {@link Mono} containing a paginated response of risk assessment DTOs that match the filter criteria.
     */
    Mono<PaginationResponse<RiskAssessmentDTO>> findAll(FilterRequest<RiskAssessmentDTO> filterRequest);

    /**
     * Creates a new risk assessment entry based on the provided data transfer object.
     *
     * @param dto The RiskAssessmentDTO containing data to create a new risk assessment record
     * @return A Mono containing the created RiskAssessmentDTO
     */
    Mono<RiskAssessmentDTO> create(RiskAssessmentDTO dto);

    /**
     * Retrieves a RiskAssessmentDTO by its unique identifier.
     *
     * @param riskAssessmentId The ID of the RiskAssessment to retrieve.
     * @return A Mono containing the RiskAssessmentDTO if found, otherwise an empty mono.
     */
    Mono<RiskAssessmentDTO> getById(UUID riskAssessmentId);

    /**
     * Updates an existing RiskAssessment entry with new data provided in the DTO.
     *
     * @param riskAssessmentId The ID of the RiskAssessment to be updated.
     * @param dto A DTO containing the fields to update for the RiskAssessment.
     * @return A Mono containing the updated RiskAssessmentDTO if successful.
     */
    Mono<RiskAssessmentDTO> update(UUID riskAssessmentId, RiskAssessmentDTO dto);

    /**
     * Deletes a Risk Assessment by its ID.
     *
     * @param riskAssessmentId The ID of the Risk Assessment to delete.
     * @return A {@link Mono<Void>} indicating completion of the deletion operation.
     */
    Mono<Void> delete(UUID riskAssessmentId);
}
