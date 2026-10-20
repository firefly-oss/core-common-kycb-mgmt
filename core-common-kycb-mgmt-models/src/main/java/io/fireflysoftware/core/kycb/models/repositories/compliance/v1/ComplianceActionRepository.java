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


package io.fireflysoftware.core.kycb.models.repositories.compliance.v1;

import io.fireflysoftware.core.kycb.models.entities.compliance.v1.ComplianceAction;
import io.fireflysoftware.core.kycb.interfaces.enums.action.v1.ActionStatusEnum;
import io.fireflysoftware.core.kycb.interfaces.enums.action.v1.ActionTypeEnum;
import io.fireflysoftware.core.kycb.models.repositories.BaseRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Repository for compliance action operations.
 */
@Repository
public interface ComplianceActionRepository extends BaseRepository<ComplianceAction, UUID> {

    /**
     * Find compliance actions by compliance case ID.
     *
     * @param complianceCaseId The ID of the compliance case
     * @return A flux of compliance actions
     */
    Flux<ComplianceAction> findByComplianceCaseId(UUID complianceCaseId);

    /**
     * Find compliance actions by action type.
     *
     * @param actionType The type of action
     * @return A flux of compliance actions
     */
    Flux<ComplianceAction> findByActionType(ActionTypeEnum actionType);

    /**
     * Find compliance actions by action status.
     *
     * @param actionStatusEnum The status of the action
     * @return A flux of compliance actions
     */
    Flux<ComplianceAction> findByActionStatus(ActionStatusEnum actionStatusEnum);

    /**
     * Find compliance actions by assigned agent.
     *
     * @param actionAgent The agent assigned to the action
     * @return A flux of compliance actions
     */
    Flux<ComplianceAction> findByActionAgent(String actionAgent);

    /**
     * Find compliance actions with due date before a specified date.
     *
     * @param dueDate The due date threshold
     * @return A flux of compliance actions
     */
    Flux<ComplianceAction> findByDueDateBefore(LocalDateTime dueDate);

    /**
     * Find compliance actions by completion date range.
     *
     * @param startDate The start date
     * @param endDate The end date
     * @return A flux of compliance actions
     */
    Flux<ComplianceAction> findByCompletionDateBetween(LocalDateTime startDate, LocalDateTime endDate);

    /**
     * Find compliance actions by compliance case ID and action status.
     *
     * @param complianceCaseId The ID of the compliance case
     * @param actionStatusEnum The status of the action
     * @return A flux of compliance actions
     */
    Flux<ComplianceAction> findByComplianceCaseIdAndActionStatus(UUID complianceCaseId, ActionStatusEnum actionStatusEnum);

    /**
     * Find compliance actions by compliance case ID and action type.
     *
     * @param complianceCaseId The ID of the compliance case
     * @param actionType The type of action
     * @return A flux of compliance actions
     */
    Flux<ComplianceAction> findByComplianceCaseIdAndActionType(UUID complianceCaseId, ActionTypeEnum actionType);

    /**
     * Find the latest compliance action for a case.
     *
     * @param complianceCaseId The ID of the compliance case
     * @return A mono with the latest compliance action
     */
    Mono<ComplianceAction> findFirstByComplianceCaseIdOrderByDateCreatedDesc(UUID complianceCaseId);
}