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


package io.fireflysoftware.core.kycb.core.services.compliance.v1;

import org.fireflyframework.core.filters.FilterRequest;
import org.fireflyframework.core.filters.FilterUtils;
import org.fireflyframework.core.queries.PaginationResponse;
import io.fireflysoftware.core.kycb.core.mappers.compliance.v1.ComplianceCaseMapper;
import io.fireflysoftware.core.kycb.interfaces.dtos.compliance.v1.ComplianceCaseDTO;
import io.fireflysoftware.core.kycb.models.entities.compliance.v1.ComplianceCase;
import io.fireflysoftware.core.kycb.models.repositories.compliance.v1.ComplianceCaseRepository;
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
public class ComplianceCaseServiceImplTest {

    @Mock
    private ComplianceCaseRepository repository;

    @Mock
    private ComplianceCaseMapper mapper;

    @Mock
    private FilterUtils filterUtils;

    @InjectMocks
    private ComplianceCaseServiceImpl complianceCaseService;

    private ComplianceCaseDTO complianceCaseDTO;
    private ComplianceCase complianceCase;
    private final UUID COMPLIANCE_CASE_ID = UUID.fromString("550e8400-e29b-41d4-a716-446655440002");
    private final UUID PARTY_ID = UUID.fromString("550e8400-e29b-41d4-a716-446655440100");

    @BeforeEach
    void setUp() {
        // Initialize test data
        complianceCaseDTO = new ComplianceCaseDTO();
        complianceCaseDTO.setComplianceCaseId(COMPLIANCE_CASE_ID);
        complianceCaseDTO.setPartyId(PARTY_ID);
        
        complianceCase = new ComplianceCase();
        complianceCase.setComplianceCaseId(COMPLIANCE_CASE_ID);
        complianceCase.setPartyId(PARTY_ID);
        complianceCase.setDateCreated(LocalDateTime.now());
    }

    @Test
    void testCreate() {
        // Arrange
        when(mapper.toEntity(any(ComplianceCaseDTO.class))).thenReturn(complianceCase);
        when(repository.save(any(ComplianceCase.class))).thenReturn(Mono.just(complianceCase));
        when(mapper.toDTO(any(ComplianceCase.class))).thenReturn(complianceCaseDTO);

        // Act & Assert
        StepVerifier.create(complianceCaseService.create(complianceCaseDTO))
                .expectNext(complianceCaseDTO)
                .verifyComplete();

        verify(mapper).toEntity(complianceCaseDTO);
        verify(repository).save(complianceCase);
        verify(mapper).toDTO(complianceCase);
    }

    @Test
    void testGetById() {
        // Arrange
        when(repository.findById(COMPLIANCE_CASE_ID)).thenReturn(Mono.just(complianceCase));
        when(mapper.toDTO(complianceCase)).thenReturn(complianceCaseDTO);

        // Act & Assert
        StepVerifier.create(complianceCaseService.getById(COMPLIANCE_CASE_ID))
                .expectNext(complianceCaseDTO)
                .verifyComplete();

        verify(repository).findById(COMPLIANCE_CASE_ID);
        verify(mapper).toDTO(complianceCase);
    }

    @Test
    void testUpdate() {
        // Arrange
        LocalDateTime creationDate = LocalDateTime.now();
        
        ComplianceCase existingCase = new ComplianceCase();
        existingCase.setComplianceCaseId(COMPLIANCE_CASE_ID);
        existingCase.setPartyId(PARTY_ID);
        existingCase.setDateCreated(creationDate);
        
        ComplianceCase updatedCase = new ComplianceCase();
        updatedCase.setComplianceCaseId(COMPLIANCE_CASE_ID);
        updatedCase.setPartyId(PARTY_ID);
        
        when(repository.findById(COMPLIANCE_CASE_ID)).thenReturn(Mono.just(existingCase));
        when(mapper.toEntity(complianceCaseDTO)).thenReturn(updatedCase);
        
        // Mock the save method to set the creation date before returning
        when(repository.save(any(ComplianceCase.class))).thenAnswer(invocation -> {
            ComplianceCase caseEntity = invocation.getArgument(0);
            if (caseEntity.getDateCreated() == null) {
                caseEntity.setDateCreated(creationDate);
            }
            return Mono.just(caseEntity);
        });
        
        when(mapper.toDTO(any(ComplianceCase.class))).thenReturn(complianceCaseDTO);

        // Act & Assert
        StepVerifier.create(complianceCaseService.update(COMPLIANCE_CASE_ID, complianceCaseDTO))
                .expectNext(complianceCaseDTO)
                .verifyComplete();

        verify(repository).findById(COMPLIANCE_CASE_ID);
        verify(mapper).toEntity(complianceCaseDTO);
        verify(repository).save(any(ComplianceCase.class));
        verify(mapper).toDTO(any(ComplianceCase.class));
    }

    @Test
    void testDelete() {
        // Arrange
        when(repository.deleteById(COMPLIANCE_CASE_ID)).thenReturn(Mono.empty());

        // Act & Assert
        StepVerifier.create(complianceCaseService.delete(COMPLIANCE_CASE_ID))
                .verifyComplete();

        verify(repository).deleteById(COMPLIANCE_CASE_ID);
    }
}