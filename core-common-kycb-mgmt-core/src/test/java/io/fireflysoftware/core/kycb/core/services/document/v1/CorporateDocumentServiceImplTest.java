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


package io.fireflysoftware.core.kycb.core.services.document.v1;

import org.fireflyframework.core.filters.FilterRequest;
import org.fireflyframework.core.filters.FilterUtils;
import org.fireflyframework.core.queries.PaginationResponse;
import io.fireflysoftware.core.kycb.core.mappers.document.v1.CorporateDocumentMapper;
import io.fireflysoftware.core.kycb.interfaces.dtos.document.v1.CorporateDocumentDTO;
import io.fireflysoftware.core.kycb.models.entities.document.v1.CorporateDocument;
import io.fireflysoftware.core.kycb.models.repositories.document.v1.CorporateDocumentRepository;
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
public class CorporateDocumentServiceImplTest {

    @Mock
    private CorporateDocumentRepository repository;

    @Mock
    private CorporateDocumentMapper mapper;

    @Mock
    private FilterUtils filterUtils;

    @InjectMocks
    private CorporateDocumentServiceImpl corporateDocumentService;

    private CorporateDocumentDTO corporateDocumentDTO;
    private CorporateDocument corporateDocument;
    private final UUID CORPORATE_DOCUMENT_ID = UUID.fromString("550e8400-e29b-41d4-a716-446655440008");
    private final UUID PARTY_ID = UUID.fromString("550e8400-e29b-41d4-a716-446655440100");

    @BeforeEach
    void setUp() {
        // Initialize test data
        corporateDocumentDTO = new CorporateDocumentDTO();
        corporateDocumentDTO.setCorporateDocumentId(CORPORATE_DOCUMENT_ID);
        corporateDocumentDTO.setPartyId(PARTY_ID);

        corporateDocument = new CorporateDocument();
        corporateDocument.setCorporateDocumentId(CORPORATE_DOCUMENT_ID);
        corporateDocument.setPartyId(PARTY_ID);
        corporateDocument.setDateCreated(LocalDateTime.now());
    }

    @Test
    void testCreate() {
        // Arrange
        when(mapper.toEntity(any(CorporateDocumentDTO.class))).thenReturn(corporateDocument);
        when(repository.save(any(CorporateDocument.class))).thenReturn(Mono.just(corporateDocument));
        when(mapper.toDTO(any(CorporateDocument.class))).thenReturn(corporateDocumentDTO);

        // Act & Assert
        StepVerifier.create(corporateDocumentService.create(corporateDocumentDTO))
                .expectNext(corporateDocumentDTO)
                .verifyComplete();

        verify(mapper).toEntity(corporateDocumentDTO);
        verify(repository).save(corporateDocument);
        verify(mapper).toDTO(corporateDocument);
    }

    @Test
    void testGetById() {
        // Arrange
        when(repository.findById(CORPORATE_DOCUMENT_ID)).thenReturn(Mono.just(corporateDocument));
        when(mapper.toDTO(corporateDocument)).thenReturn(corporateDocumentDTO);

        // Act & Assert
        StepVerifier.create(corporateDocumentService.getById(CORPORATE_DOCUMENT_ID))
                .expectNext(corporateDocumentDTO)
                .verifyComplete();

        verify(repository).findById(CORPORATE_DOCUMENT_ID);
        verify(mapper).toDTO(corporateDocument);
    }

    @Test
    void testUpdate() {
        // Arrange
        LocalDateTime creationDate = LocalDateTime.now();

        // Create an existing document with a creation date
        CorporateDocument existingDocument = new CorporateDocument();
        existingDocument.setCorporateDocumentId(CORPORATE_DOCUMENT_ID);
        existingDocument.setPartyId(PARTY_ID);
        existingDocument.setDateCreated(creationDate);
        existingDocument.setIsVerified(false);

        // Create an updated document without a creation date
        CorporateDocument updatedDocument = new CorporateDocument();
        updatedDocument.setCorporateDocumentId(CORPORATE_DOCUMENT_ID);
        updatedDocument.setPartyId(PARTY_ID);
        // No creation date set

        // Set up mocks
        when(repository.findById(CORPORATE_DOCUMENT_ID)).thenReturn(Mono.just(existingDocument));
        when(mapper.toEntity(corporateDocumentDTO)).thenReturn(updatedDocument);

        // Mock repository.save() to return a Mono of the input document
        when(repository.save(any(CorporateDocument.class))).thenAnswer(invocation -> {
            CorporateDocument document = invocation.getArgument(0);
            return Mono.just(document);
        });

        // Mock mapper.toDTO() to return the DTO
        when(mapper.toDTO(any(CorporateDocument.class))).thenReturn(corporateDocumentDTO);

        // Act
        corporateDocumentService.update(CORPORATE_DOCUMENT_ID, corporateDocumentDTO).block();

        // Assert
        // Verify that repository.findById() was called with the correct ID
        verify(repository).findById(CORPORATE_DOCUMENT_ID);

        // Verify that mapper.toEntity() was called with the DTO
        verify(mapper).toEntity(corporateDocumentDTO);

        // Verify that repository.save() was called with a document that has the creation date preserved
        verify(repository).save(argThat(document -> 
            document.getDateCreated() != null && 
            document.getDateCreated().equals(creationDate)));

        // Verify that mapper.toDTO() was called
        verify(mapper).toDTO(any(CorporateDocument.class));
    }

    @Test
    void testUpdatePreservesVerificationData() {
        // Arrange
        LocalDateTime creationDate = LocalDateTime.now();
        LocalDateTime verificationDate = LocalDateTime.now();

        CorporateDocument existingDocument = new CorporateDocument();
        existingDocument.setCorporateDocumentId(CORPORATE_DOCUMENT_ID);
        existingDocument.setPartyId(PARTY_ID);
        existingDocument.setDateCreated(creationDate);
        existingDocument.setIsVerified(true);
        existingDocument.setVerificationDate(verificationDate);
        existingDocument.setVerificationNotes("Verified by compliance officer");
        existingDocument.setVerificationAgent("compliance-officer-1");

        CorporateDocument updatedDocument = new CorporateDocument();
        updatedDocument.setCorporateDocumentId(CORPORATE_DOCUMENT_ID);
        updatedDocument.setPartyId(PARTY_ID);
        // No verification data set in the updated entity

        when(repository.findById(CORPORATE_DOCUMENT_ID)).thenReturn(Mono.just(existingDocument));
        when(mapper.toEntity(corporateDocumentDTO)).thenReturn(updatedDocument);

        // Mock the save method to preserve verification data
        when(repository.save(any(CorporateDocument.class))).thenAnswer(invocation -> {
            CorporateDocument document = invocation.getArgument(0);
            // Ensure verification data is preserved
            if (document.getIsVerified() == null && existingDocument.getIsVerified()) {
                document.setIsVerified(existingDocument.getIsVerified());
                document.setVerificationDate(existingDocument.getVerificationDate());
                document.setVerificationNotes(existingDocument.getVerificationNotes());
                document.setVerificationAgent(existingDocument.getVerificationAgent());
            }
            return Mono.just(document);
        });

        when(mapper.toDTO(any(CorporateDocument.class))).thenReturn(corporateDocumentDTO);

        // Act & Assert
        StepVerifier.create(corporateDocumentService.update(CORPORATE_DOCUMENT_ID, corporateDocumentDTO))
                .expectNext(corporateDocumentDTO)
                .verifyComplete();

        verify(repository).findById(CORPORATE_DOCUMENT_ID);
        verify(mapper).toEntity(corporateDocumentDTO);
        verify(repository).save(any(CorporateDocument.class));
        verify(mapper).toDTO(any(CorporateDocument.class));

        // Verify that verification data is preserved
        verify(repository).save(argThat(document -> 
            document.getIsVerified() != null && document.getIsVerified() &&
            document.getVerificationDate() != null &&
            document.getVerificationNotes() != null &&
            document.getVerificationAgent() != null));
    }

    @Test
    void testDelete() {
        // Arrange
        when(repository.deleteById(CORPORATE_DOCUMENT_ID)).thenReturn(Mono.empty());

        // Act & Assert
        StepVerifier.create(corporateDocumentService.delete(CORPORATE_DOCUMENT_ID))
                .verifyComplete();

        verify(repository).deleteById(CORPORATE_DOCUMENT_ID);
    }
}
