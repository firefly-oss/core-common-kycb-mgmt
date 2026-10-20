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
import org.fireflyframework.core.filters.FilterUtils;
import org.fireflyframework.core.queries.PaginationResponse;
import io.fireflysoftware.core.kycb.core.mappers.regulatory.v1.RegulatoryReportingMapper;
import io.fireflysoftware.core.kycb.interfaces.dtos.regulatory.v1.RegulatoryReportingDTO;
import io.fireflysoftware.core.kycb.interfaces.enums.report.v1.ReportStatusEnum;
import io.fireflysoftware.core.kycb.interfaces.enums.report.v1.ReportTypeEnum;
import io.fireflysoftware.core.kycb.models.entities.regulatory.v1.RegulatoryReporting;
import io.fireflysoftware.core.kycb.models.repositories.regulatory.v1.RegulatoryReportingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class RegulatoryReportingServiceImplTest {

    @Mock
    private RegulatoryReportingRepository repository;

    @Mock
    private RegulatoryReportingMapper mapper;

    @Mock
    private FilterUtils filterUtils;

    @InjectMocks
    private RegulatoryReportingServiceImpl regulatoryReportingService;

    private RegulatoryReportingDTO regulatoryReportingDTO;
    private RegulatoryReporting regulatoryReporting;
    private final UUID REPORT_ID = UUID.fromString("550e8400-e29b-41d4-a716-446655440016");
    private final UUID COMPLIANCE_CASE_ID = UUID.fromString("550e8400-e29b-41d4-a716-446655440002");

    @BeforeEach
    void setUp() {
        // Initialize test data
        regulatoryReportingDTO = new RegulatoryReportingDTO();
        regulatoryReportingDTO.setReportId(REPORT_ID);
        regulatoryReportingDTO.setComplianceCaseId(COMPLIANCE_CASE_ID);
        regulatoryReportingDTO.setReportType("COMUNICACION_SEPBLAC");
        regulatoryReportingDTO.setReportReference("REF-2023-12345");
        regulatoryReportingDTO.setRegulatoryAuthority("SEPBLAC");
        regulatoryReportingDTO.setReportStatus("DRAFT");
        regulatoryReportingDTO.setReportContentSummary("Suspicious transaction report for customer XYZ");
        
        regulatoryReporting = new RegulatoryReporting();
        regulatoryReporting.setReportId(REPORT_ID);
        regulatoryReporting.setComplianceCaseId(COMPLIANCE_CASE_ID);
        regulatoryReporting.setReportType(ReportTypeEnum.COMUNICACION_SEPBLAC);
        regulatoryReporting.setReportReference("REF-2023-12345");
        regulatoryReporting.setRegulatoryAuthority("SEPBLAC");
        regulatoryReporting.setReportStatus(ReportStatusEnum.DRAFT);
        regulatoryReporting.setReportContentSummary("Suspicious transaction report for customer XYZ");
        regulatoryReporting.setDateCreated(LocalDateTime.now());
    }

    @Test
    void testCreate() {
        // Arrange
        when(mapper.toEntity(any(RegulatoryReportingDTO.class))).thenReturn(regulatoryReporting);
        when(repository.save(any(RegulatoryReporting.class))).thenReturn(Mono.just(regulatoryReporting));
        when(mapper.toDTO(any(RegulatoryReporting.class))).thenReturn(regulatoryReportingDTO);

        // Act & Assert
        StepVerifier.create(regulatoryReportingService.create(regulatoryReportingDTO))
                .expectNext(regulatoryReportingDTO)
                .verifyComplete();

        verify(mapper).toEntity(regulatoryReportingDTO);
        verify(repository).save(regulatoryReporting);
        verify(mapper).toDTO(regulatoryReporting);
    }

    @Test
    void testCreateWithDefaultStatus() {
        // Arrange
        RegulatoryReporting entityWithoutStatus = new RegulatoryReporting();
        entityWithoutStatus.setReportId(REPORT_ID);
        entityWithoutStatus.setComplianceCaseId(COMPLIANCE_CASE_ID);
        entityWithoutStatus.setReportType(ReportTypeEnum.COMUNICACION_SEPBLAC);
        entityWithoutStatus.setReportReference("REF-2023-12345");
        // No status set
        
        RegulatoryReportingDTO dtoWithoutStatus = new RegulatoryReportingDTO();
        dtoWithoutStatus.setReportId(REPORT_ID);
        dtoWithoutStatus.setComplianceCaseId(COMPLIANCE_CASE_ID);
        dtoWithoutStatus.setReportType("COMUNICACION_SEPBLAC");
        dtoWithoutStatus.setReportReference("REF-2023-12345");
        // No status set

        when(mapper.toEntity(dtoWithoutStatus)).thenReturn(entityWithoutStatus);
        
        // Mock the save method to verify the status is set to DRAFT
        when(repository.save(any(RegulatoryReporting.class))).thenAnswer(invocation -> {
            RegulatoryReporting savedEntity = invocation.getArgument(0);
            // Verify that the status is set to DRAFT
            if (savedEntity.getReportStatus() != ReportStatusEnum.DRAFT) {
                throw new AssertionError("Status should be set to DRAFT");
            }
            return Mono.just(savedEntity);
        });
        
        when(mapper.toDTO(any(RegulatoryReporting.class))).thenReturn(regulatoryReportingDTO);

        // Act & Assert
        StepVerifier.create(regulatoryReportingService.create(dtoWithoutStatus))
                .expectNext(regulatoryReportingDTO)
                .verifyComplete();

        verify(mapper).toEntity(dtoWithoutStatus);
        verify(repository).save(any(RegulatoryReporting.class));
        verify(mapper).toDTO(any(RegulatoryReporting.class));
        
        // Verify that the saved entity has the status set to DRAFT
        verify(repository).save(argThat(entity -> entity.getReportStatus() == ReportStatusEnum.DRAFT));
    }

    @Test
    void testGetById() {
        // Arrange
        when(repository.findById(REPORT_ID)).thenReturn(Mono.just(regulatoryReporting));
        when(mapper.toDTO(regulatoryReporting)).thenReturn(regulatoryReportingDTO);

        // Act & Assert
        StepVerifier.create(regulatoryReportingService.getById(REPORT_ID))
                .expectNext(regulatoryReportingDTO)
                .verifyComplete();

        verify(repository).findById(REPORT_ID);
        verify(mapper).toDTO(regulatoryReporting);
    }

    @Test
    void testUpdate() {
        // Arrange
        LocalDateTime creationDate = LocalDateTime.now();
        
        RegulatoryReporting existingReport = new RegulatoryReporting();
        existingReport.setReportId(REPORT_ID);
        existingReport.setComplianceCaseId(COMPLIANCE_CASE_ID);
        existingReport.setReportType(ReportTypeEnum.COMUNICACION_SEPBLAC);
        existingReport.setReportStatus(ReportStatusEnum.DRAFT);
        existingReport.setDateCreated(creationDate);
        
        RegulatoryReporting updatedReport = new RegulatoryReporting();
        updatedReport.setReportId(REPORT_ID);
        updatedReport.setComplianceCaseId(COMPLIANCE_CASE_ID);
        updatedReport.setReportType(ReportTypeEnum.COMUNICACION_SEPBLAC);
        updatedReport.setReportStatus(ReportStatusEnum.SUBMITTED);
        updatedReport.setSubmissionDate(LocalDateTime.now());
        updatedReport.setSubmittingAgent("John Doe");
        
        RegulatoryReportingDTO updatedDTO = new RegulatoryReportingDTO();
        updatedDTO.setReportId(REPORT_ID);
        updatedDTO.setComplianceCaseId(COMPLIANCE_CASE_ID);
        updatedDTO.setReportType("COMUNICACION_SEPBLAC");
        updatedDTO.setReportStatus("SUBMITTED");
        updatedDTO.setSubmissionDate(LocalDateTime.now());
        updatedDTO.setSubmittingAgent("John Doe");
        
        when(repository.findById(REPORT_ID)).thenReturn(Mono.just(existingReport));
        when(mapper.toEntity(updatedDTO)).thenReturn(updatedReport);
        
        // Mock the save method to set the creation date before returning
        when(repository.save(any(RegulatoryReporting.class))).thenAnswer(invocation -> {
            RegulatoryReporting report = invocation.getArgument(0);
            if (report.getDateCreated() == null) {
                report.setDateCreated(creationDate);
            }
            return Mono.just(report);
        });
        
        when(mapper.toDTO(any(RegulatoryReporting.class))).thenReturn(updatedDTO);

        // Act & Assert
        StepVerifier.create(regulatoryReportingService.update(REPORT_ID, updatedDTO))
                .expectNext(updatedDTO)
                .verifyComplete();

        verify(repository).findById(REPORT_ID);
        verify(mapper).toEntity(updatedDTO);
        verify(repository).save(any(RegulatoryReporting.class));
        verify(mapper).toDTO(any(RegulatoryReporting.class));
        
        // Verify that the creation date is preserved
        verify(repository).save(argThat(report -> 
            report.getDateCreated() != null && report.getDateCreated().equals(creationDate)));
    }

    @Test
    void testDelete() {
        // Arrange
        when(repository.deleteById(REPORT_ID)).thenReturn(Mono.empty());

        // Act & Assert
        StepVerifier.create(regulatoryReportingService.delete(REPORT_ID))
                .verifyComplete();

        verify(repository).deleteById(REPORT_ID);
    }
}