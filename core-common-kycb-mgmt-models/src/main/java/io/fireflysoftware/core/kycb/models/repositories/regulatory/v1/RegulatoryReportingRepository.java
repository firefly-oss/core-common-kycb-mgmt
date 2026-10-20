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


package io.fireflysoftware.core.kycb.models.repositories.regulatory.v1;

import io.fireflysoftware.core.kycb.models.entities.regulatory.v1.RegulatoryReporting;
import io.fireflysoftware.core.kycb.interfaces.enums.report.v1.ReportStatusEnum;
import io.fireflysoftware.core.kycb.interfaces.enums.report.v1.ReportTypeEnum;
import io.fireflysoftware.core.kycb.models.repositories.BaseRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Repository for regulatory reporting operations.
 */
@Repository
public interface RegulatoryReportingRepository extends BaseRepository<RegulatoryReporting, UUID> {

    /**
     * Find regulatory reports by compliance case ID.
     *
     * @param complianceCaseId The ID of the compliance case
     * @return A flux of regulatory reports
     */
    Flux<RegulatoryReporting> findByComplianceCaseId(UUID complianceCaseId);

    /**
     * Find regulatory reports by report type.
     *
     * @param reportTypeEnum The type of report
     * @return A flux of regulatory reports
     */
    Flux<RegulatoryReporting> findByReportType(ReportTypeEnum reportTypeEnum);

    /**
     * Find regulatory reports by report status.
     *
     * @param reportStatusEnum The status of the report
     * @return A flux of regulatory reports
     */
    Flux<RegulatoryReporting> findByReportStatus(ReportStatusEnum reportStatusEnum);

    /**
     * Find regulatory reports by regulatory authority.
     *
     * @param regulatoryAuthority The regulatory authority
     * @return A flux of regulatory reports
     */
    Flux<RegulatoryReporting> findByRegulatoryAuthority(String regulatoryAuthority);

    /**
     * Find regulatory reports by submitting agent.
     *
     * @param submittingAgent The agent who submitted the report
     * @return A flux of regulatory reports
     */
    Flux<RegulatoryReporting> findBySubmittingAgent(String submittingAgent);

    /**
     * Find regulatory reports by submission date range.
     *
     * @param startDate The start date
     * @param endDate The end date
     * @return A flux of regulatory reports
     */
    Flux<RegulatoryReporting> findBySubmissionDateBetween(LocalDateTime startDate, LocalDateTime endDate);

    /**
     * Find regulatory reports by acknowledgment date range.
     *
     * @param startDate The start date
     * @param endDate The end date
     * @return A flux of regulatory reports
     */
    Flux<RegulatoryReporting> findByAcknowledgmentDateBetween(LocalDateTime startDate, LocalDateTime endDate);

    /**
     * Find regulatory reports by report reference.
     *
     * @param reportReference The report reference
     * @return A flux of regulatory reports
     */
    Flux<RegulatoryReporting> findByReportReference(String reportReference);

    /**
     * Find regulatory reports by compliance case ID and report status.
     *
     * @param complianceCaseId The ID of the compliance case
     * @param reportStatusEnum The status of the report
     * @return A flux of regulatory reports
     */
    Flux<RegulatoryReporting> findByComplianceCaseIdAndReportStatus(UUID complianceCaseId, ReportStatusEnum reportStatusEnum);

    /**
     * Find the latest regulatory report for a compliance case.
     *
     * @param complianceCaseId The ID of the compliance case
     * @return A mono with the latest regulatory report
     */
    Mono<RegulatoryReporting> findFirstByComplianceCaseIdOrderBySubmissionDateDesc(UUID complianceCaseId);
}