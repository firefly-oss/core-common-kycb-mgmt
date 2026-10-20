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

import io.fireflysoftware.core.kycb.models.entities.compliance.v1.ComplianceCase;
import io.fireflysoftware.core.kycb.interfaces.enums.compliance.v1.CasePriorityEnum;
import io.fireflysoftware.core.kycb.interfaces.enums.compliance.v1.CaseStatusEnum;
import io.fireflysoftware.core.kycb.interfaces.enums.compliance.v1.CaseTypeEnum;
import io.fireflysoftware.core.kycb.models.repositories.BaseRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Repository for compliance case operations.
 */
@Repository
public interface ComplianceCaseRepository extends BaseRepository<ComplianceCase, UUID> {

    /**
     * Find compliance cases by party ID.
     *
     * @param partyId The ID of the party
     * @return A flux of compliance cases
     */
    Flux<ComplianceCase> findByPartyId(UUID partyId);

    /**
     * Find compliance cases by case type.
     *
     * @param caseTypeEnum The type of case
     * @return A flux of compliance cases
     */
    Flux<ComplianceCase> findByCaseType(CaseTypeEnum caseTypeEnum);

    /**
     * Find compliance cases by case status.
     *
     * @param caseStatusEnum The status of the case
     * @return A flux of compliance cases
     */
    Flux<ComplianceCase> findByCaseStatus(CaseStatusEnum caseStatusEnum);

    /**
     * Find compliance cases by case priority.
     *
     * @param casePriorityEnum The priority of the case
     * @return A flux of compliance cases
     */
    Flux<ComplianceCase> findByCasePriority(CasePriorityEnum casePriorityEnum);

    /**
     * Find compliance cases by assigned user.
     *
     * @param assignedTo The user assigned to the case
     * @return A flux of compliance cases
     */
    Flux<ComplianceCase> findByAssignedTo(String assignedTo);

    /**
     * Find compliance cases with due date before a specified date.
     *
     * @param dueDate The due date threshold
     * @return A flux of compliance cases
     */
    Flux<ComplianceCase> findByDueDateBefore(LocalDateTime dueDate);

    /**
     * Find compliance cases that require SEPBLAC reporting.
     *
     * @param reportRequired Whether SEPBLAC reporting is required
     * @return A flux of compliance cases
     */
    Flux<ComplianceCase> findByReportToSepblacRequired(Boolean reportRequired);

    /**
     * Find compliance cases by resolution date range.
     *
     * @param startDate The start date
     * @param endDate The end date
     * @return A flux of compliance cases
     */
    Flux<ComplianceCase> findByResolutionDateBetween(LocalDateTime startDate, LocalDateTime endDate);

    /**
     * Find compliance cases by party ID and case status.
     *
     * @param partyId The ID of the party
     * @param caseStatusEnum The status of the case
     * @return A flux of compliance cases
     */
    Flux<ComplianceCase> findByPartyIdAndCaseStatus(UUID partyId, CaseStatusEnum caseStatusEnum);

    /**
     * Find the latest compliance case for a party.
     *
     * @param partyId The ID of the party
     * @return A mono with the latest compliance case
     */
    Mono<ComplianceCase> findFirstByPartyIdOrderByDateCreatedDesc(UUID partyId);
}