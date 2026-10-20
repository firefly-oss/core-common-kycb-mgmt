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
import io.fireflysoftware.core.kycb.core.mappers.document.v1.VerificationDocumentMapper;
import io.fireflysoftware.core.kycb.interfaces.dtos.document.v1.VerificationDocumentDTO;
import io.fireflysoftware.core.kycb.interfaces.enums.document.v1.DocumentTypeEnum;
import io.fireflysoftware.core.kycb.interfaces.enums.verification.v1.VerificationPurposeEnum;
import io.fireflysoftware.core.kycb.models.entities.document.v1.VerificationDocument;
import io.fireflysoftware.core.kycb.models.repositories.document.v1.VerificationDocumentRepository;
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
public class VerificationDocumentServiceImplTest {

    @Mock
    private VerificationDocumentRepository repository;

    @Mock
    private VerificationDocumentMapper mapper;

    @Mock
    private FilterUtils filterUtils;

    @InjectMocks
    private VerificationDocumentServiceImpl verificationDocumentService;

    private VerificationDocumentDTO verificationDocumentDTO;
    private VerificationDocument verificationDocument;
    private final UUID VERIFICATION_DOCUMENT_ID = UUID.fromString("550e8400-e29b-41d4-a716-446655440006");
    private final UUID KYC_VERIFICATION_ID = UUID.fromString("550e8400-e29b-41d4-a716-446655440001");
    private final UUID IDENTITY_DOCUMENT_ID = UUID.fromString("550e8400-e29b-41d4-a716-446655440200");

    @BeforeEach
    void setUp() {
        // Initialize test data
        verificationDocumentDTO = new VerificationDocumentDTO();
        verificationDocumentDTO.setVerificationDocumentId(VERIFICATION_DOCUMENT_ID);
        verificationDocumentDTO.setKycVerificationId(KYC_VERIFICATION_ID);
        verificationDocumentDTO.setIdentityDocumentId(IDENTITY_DOCUMENT_ID);
        verificationDocumentDTO.setDocumentType("PASSPORT");
        verificationDocumentDTO.setVerificationPurpose("IDENTITY");
        verificationDocumentDTO.setDocumentReference("REF-123456");
        verificationDocumentDTO.setDocumentSystemId("SYS-789012");
        verificationDocumentDTO.setIsVerified(false);
        verificationDocumentDTO.setVerificationNotes("Pending verification");
        verificationDocumentDTO.setExpiryDate(LocalDateTime.now().plusYears(5));

        verificationDocument = new VerificationDocument();
        verificationDocument.setVerificationDocumentId(VERIFICATION_DOCUMENT_ID);
        verificationDocument.setKycVerificationId(KYC_VERIFICATION_ID);
        verificationDocument.setIdentityDocumentId(IDENTITY_DOCUMENT_ID);
        verificationDocument.setDocumentType(DocumentTypeEnum.PASSPORT);
        verificationDocument.setVerificationPurpose(VerificationPurposeEnum.IDENTITY);
        verificationDocument.setDocumentReference("REF-123456");
        verificationDocument.setDocumentSystemId("SYS-789012");
        verificationDocument.setIsVerified(false);
        verificationDocument.setVerificationNotes("Pending verification");
        verificationDocument.setExpiryDate(LocalDateTime.now().plusYears(5));
        verificationDocument.setDateCreated(LocalDateTime.now());
    }

    @Test
    void testCreate() {
        // Arrange
        when(mapper.toEntity(any(VerificationDocumentDTO.class))).thenReturn(verificationDocument);
        when(repository.save(any(VerificationDocument.class))).thenReturn(Mono.just(verificationDocument));
        when(mapper.toDTO(any(VerificationDocument.class))).thenReturn(verificationDocumentDTO);

        // Act & Assert
        StepVerifier.create(verificationDocumentService.create(verificationDocumentDTO))
                .expectNext(verificationDocumentDTO)
                .verifyComplete();

        verify(mapper).toEntity(verificationDocumentDTO);
        verify(repository).save(verificationDocument);
        verify(mapper).toDTO(verificationDocument);
    }

    @Test
    void testGetById() {
        // Arrange
        when(repository.findById(VERIFICATION_DOCUMENT_ID)).thenReturn(Mono.just(verificationDocument));
        when(mapper.toDTO(verificationDocument)).thenReturn(verificationDocumentDTO);

        // Act & Assert
        StepVerifier.create(verificationDocumentService.getById(VERIFICATION_DOCUMENT_ID))
                .expectNext(verificationDocumentDTO)
                .verifyComplete();

        verify(repository).findById(VERIFICATION_DOCUMENT_ID);
        verify(mapper).toDTO(verificationDocument);
    }

    @Test
    void testUpdate() {
        // Arrange
        LocalDateTime creationDate = LocalDateTime.now();

        VerificationDocument existingDocument = new VerificationDocument();
        existingDocument.setVerificationDocumentId(VERIFICATION_DOCUMENT_ID);
        existingDocument.setKycVerificationId(KYC_VERIFICATION_ID);
        existingDocument.setIdentityDocumentId(IDENTITY_DOCUMENT_ID);
        existingDocument.setDocumentType(DocumentTypeEnum.PASSPORT);
        existingDocument.setVerificationPurpose(VerificationPurposeEnum.IDENTITY);
        existingDocument.setDateCreated(creationDate);

        VerificationDocument updatedDocument = new VerificationDocument();
        updatedDocument.setVerificationDocumentId(VERIFICATION_DOCUMENT_ID);
        updatedDocument.setKycVerificationId(KYC_VERIFICATION_ID);
        updatedDocument.setIdentityDocumentId(IDENTITY_DOCUMENT_ID);
        updatedDocument.setDocumentType(DocumentTypeEnum.PASSPORT);
        updatedDocument.setVerificationPurpose(VerificationPurposeEnum.IDENTITY);
        updatedDocument.setIsVerified(true);
        updatedDocument.setVerificationNotes("Verified successfully");

        VerificationDocumentDTO updatedDTO = new VerificationDocumentDTO();
        updatedDTO.setVerificationDocumentId(VERIFICATION_DOCUMENT_ID);
        updatedDTO.setKycVerificationId(KYC_VERIFICATION_ID);
        updatedDTO.setIdentityDocumentId(IDENTITY_DOCUMENT_ID);
        updatedDTO.setDocumentType("PASSPORT");
        updatedDTO.setVerificationPurpose("IDENTITY");
        updatedDTO.setIsVerified(true);
        updatedDTO.setVerificationNotes("Verified successfully");

        when(repository.findById(VERIFICATION_DOCUMENT_ID)).thenReturn(Mono.just(existingDocument));
        when(mapper.toEntity(updatedDTO)).thenReturn(updatedDocument);

        // Mock the save method to set the creation date before returning
        when(repository.save(any(VerificationDocument.class))).thenAnswer(invocation -> {
            VerificationDocument document = invocation.getArgument(0);
            if (document.getDateCreated() == null) {
                document.setDateCreated(creationDate);
            }
            return Mono.just(document);
        });

        when(mapper.toDTO(any(VerificationDocument.class))).thenReturn(updatedDTO);

        // Act & Assert
        StepVerifier.create(verificationDocumentService.update(VERIFICATION_DOCUMENT_ID, updatedDTO))
                .expectNext(updatedDTO)
                .verifyComplete();

        verify(repository).findById(VERIFICATION_DOCUMENT_ID);
        verify(mapper).toEntity(updatedDTO);
        verify(repository).save(any(VerificationDocument.class));
        verify(mapper).toDTO(any(VerificationDocument.class));

        // Verify that the creation date is preserved
        verify(repository).save(argThat(document -> 
            document.getDateCreated() != null && document.getDateCreated().equals(creationDate)));
    }

    @Test
    void testDelete() {
        // Arrange
        when(repository.deleteById(VERIFICATION_DOCUMENT_ID)).thenReturn(Mono.empty());

        // Act & Assert
        StepVerifier.create(verificationDocumentService.delete(VERIFICATION_DOCUMENT_ID))
                .verifyComplete();

        verify(repository).deleteById(VERIFICATION_DOCUMENT_ID);
    }
}
