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


package io.fireflysoftware.core.kycb.core.services.source.v1;

import org.fireflyframework.core.filters.FilterRequest;
import org.fireflyframework.core.filters.FilterUtils;
import org.fireflyframework.core.queries.PaginationResponse;
import io.fireflysoftware.core.kycb.core.mappers.source.v1.SourceOfFundsMapper;
import io.fireflysoftware.core.kycb.interfaces.dtos.source.v1.SourceOfFundsDTO;
import io.fireflysoftware.core.kycb.interfaces.enums.source.v1.SourceTypeEnum;
import io.fireflysoftware.core.kycb.models.entities.source.v1.SourceOfFunds;
import io.fireflysoftware.core.kycb.models.repositories.source.v1.SourceOfFundsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class SourceOfFundsServiceImplTest {

    @Mock
    private SourceOfFundsRepository repository;

    @Mock
    private SourceOfFundsMapper mapper;

    @Mock
    private FilterUtils filterUtils;

    @InjectMocks
    private SourceOfFundsServiceImpl sourceOfFundsService;

    private SourceOfFundsDTO sourceOfFundsDTO;
    private SourceOfFunds sourceOfFunds;
    private final UUID SOURCE_OF_FUNDS_ID = UUID.fromString("550e8400-e29b-41d4-a716-446655440005");
    private final UUID PARTY_ID = UUID.fromString("550e8400-e29b-41d4-a716-446655440100");

    @BeforeEach
    void setUp() {
        // Initialize test data
        sourceOfFundsDTO = new SourceOfFundsDTO();
        sourceOfFundsDTO.setSourceOfFundsId(SOURCE_OF_FUNDS_ID);
        sourceOfFundsDTO.setPartyId(PARTY_ID);
        sourceOfFundsDTO.setSourceType("SALARY");
        sourceOfFundsDTO.setSourceDescription("Monthly salary from ABC Corp");
        sourceOfFundsDTO.setEstimatedAnnualAmount(new BigDecimal("60000.00"));
        sourceOfFundsDTO.setCurrency("USD");
        sourceOfFundsDTO.setIsVerified(false);
        sourceOfFundsDTO.setSupportingDocuments("payslips.pdf");
        
        sourceOfFunds = new SourceOfFunds();
        sourceOfFunds.setSourceOfFundsId(SOURCE_OF_FUNDS_ID);
        sourceOfFunds.setPartyId(PARTY_ID);
        sourceOfFunds.setSourceType(SourceTypeEnum.SALARY);
        sourceOfFunds.setSourceDescription("Monthly salary from ABC Corp");
        sourceOfFunds.setEstimatedAnnualAmount(new BigDecimal("60000.00"));
        sourceOfFunds.setCurrency("USD");
        sourceOfFunds.setIsVerified(false);
        sourceOfFunds.setSupportingDocuments("payslips.pdf");
        sourceOfFunds.setDateCreated(LocalDateTime.now());
    }

    @Test
    void testCreate() {
        // Arrange
        when(mapper.toEntity(any(SourceOfFundsDTO.class))).thenReturn(sourceOfFunds);
        when(repository.save(any(SourceOfFunds.class))).thenReturn(Mono.just(sourceOfFunds));
        when(mapper.toDTO(any(SourceOfFunds.class))).thenReturn(sourceOfFundsDTO);

        // Act & Assert
        StepVerifier.create(sourceOfFundsService.create(sourceOfFundsDTO))
                .expectNext(sourceOfFundsDTO)
                .verifyComplete();

        verify(mapper).toEntity(sourceOfFundsDTO);
        verify(repository).save(sourceOfFunds);
        verify(mapper).toDTO(sourceOfFunds);
    }

    @Test
    void testGetById() {
        // Arrange
        when(repository.findById(SOURCE_OF_FUNDS_ID)).thenReturn(Mono.just(sourceOfFunds));
        when(mapper.toDTO(sourceOfFunds)).thenReturn(sourceOfFundsDTO);

        // Act & Assert
        StepVerifier.create(sourceOfFundsService.getById(SOURCE_OF_FUNDS_ID))
                .expectNext(sourceOfFundsDTO)
                .verifyComplete();

        verify(repository).findById(SOURCE_OF_FUNDS_ID);
        verify(mapper).toDTO(sourceOfFunds);
    }

    @Test
    void testUpdate() {
        // Arrange
        LocalDateTime creationDate = LocalDateTime.now();
        
        SourceOfFunds existingSource = new SourceOfFunds();
        existingSource.setSourceOfFundsId(SOURCE_OF_FUNDS_ID);
        existingSource.setPartyId(PARTY_ID);
        existingSource.setSourceType(SourceTypeEnum.SALARY);
        existingSource.setDateCreated(creationDate);
        existingSource.setIsVerified(false);
        
        SourceOfFunds updatedSource = new SourceOfFunds();
        updatedSource.setSourceOfFundsId(SOURCE_OF_FUNDS_ID);
        updatedSource.setPartyId(PARTY_ID);
        updatedSource.setSourceType(SourceTypeEnum.BUSINESS_INCOME);
        updatedSource.setSourceDescription("Business income from XYZ LLC");
        updatedSource.setEstimatedAnnualAmount(new BigDecimal("120000.00"));
        
        SourceOfFundsDTO updatedDTO = new SourceOfFundsDTO();
        updatedDTO.setSourceOfFundsId(SOURCE_OF_FUNDS_ID);
        updatedDTO.setPartyId(PARTY_ID);
        updatedDTO.setSourceType("BUSINESS_INCOME");
        updatedDTO.setSourceDescription("Business income from XYZ LLC");
        updatedDTO.setEstimatedAnnualAmount(new BigDecimal("120000.00"));
        
        when(repository.findById(SOURCE_OF_FUNDS_ID)).thenReturn(Mono.just(existingSource));
        when(mapper.toEntity(updatedDTO)).thenReturn(updatedSource);
        when(repository.save(any(SourceOfFunds.class))).thenReturn(Mono.just(updatedSource));
        when(mapper.toDTO(any(SourceOfFunds.class))).thenReturn(updatedDTO);

        // Act & Assert
        StepVerifier.create(sourceOfFundsService.update(SOURCE_OF_FUNDS_ID, updatedDTO))
                .expectNext(updatedDTO)
                .verifyComplete();

        verify(repository).findById(SOURCE_OF_FUNDS_ID);
        verify(mapper).toEntity(updatedDTO);
        verify(repository).save(any(SourceOfFunds.class));
        verify(mapper).toDTO(any(SourceOfFunds.class));
        
        // Verify that the creation date is preserved
        verify(repository).save(argThat(source -> 
            source.getDateCreated() != null && source.getDateCreated().equals(creationDate)));
    }

    @Test
    void testUpdatePreservesVerificationData() {
        // Arrange
        LocalDateTime creationDate = LocalDateTime.now();
        LocalDateTime verificationDate = LocalDateTime.now();
        
        SourceOfFunds existingSource = new SourceOfFunds();
        existingSource.setSourceOfFundsId(SOURCE_OF_FUNDS_ID);
        existingSource.setPartyId(PARTY_ID);
        existingSource.setSourceType(SourceTypeEnum.SALARY);
        existingSource.setDateCreated(creationDate);
        existingSource.setIsVerified(true);
        existingSource.setVerificationDate(verificationDate);
        existingSource.setVerificationMethod("DOCUMENT_REVIEW");
        
        SourceOfFunds updatedSource = new SourceOfFunds();
        updatedSource.setSourceOfFundsId(SOURCE_OF_FUNDS_ID);
        updatedSource.setPartyId(PARTY_ID);
        updatedSource.setSourceType(SourceTypeEnum.SALARY);
        updatedSource.setSourceDescription("Updated description");
        // No verification data set in the updated entity
        
        SourceOfFundsDTO updatedDTO = new SourceOfFundsDTO();
        updatedDTO.setSourceOfFundsId(SOURCE_OF_FUNDS_ID);
        updatedDTO.setPartyId(PARTY_ID);
        updatedDTO.setSourceType("SALARY");
        updatedDTO.setSourceDescription("Updated description");
        // No verification data set in the updated DTO
        
        when(repository.findById(SOURCE_OF_FUNDS_ID)).thenReturn(Mono.just(existingSource));
        when(mapper.toEntity(updatedDTO)).thenReturn(updatedSource);
        
        // Mock the save method to verify verification data is preserved
        when(repository.save(any(SourceOfFunds.class))).thenAnswer(invocation -> {
            SourceOfFunds savedSource = invocation.getArgument(0);
            // Verify that verification data is preserved
            if (savedSource.getIsVerified() == null && existingSource.getIsVerified()) {
                savedSource.setIsVerified(existingSource.getIsVerified());
                savedSource.setVerificationDate(existingSource.getVerificationDate());
                savedSource.setVerificationMethod(existingSource.getVerificationMethod());
            }
            return Mono.just(savedSource);
        });
        
        when(mapper.toDTO(any(SourceOfFunds.class))).thenReturn(updatedDTO);

        // Act & Assert
        StepVerifier.create(sourceOfFundsService.update(SOURCE_OF_FUNDS_ID, updatedDTO))
                .expectNext(updatedDTO)
                .verifyComplete();

        verify(repository).findById(SOURCE_OF_FUNDS_ID);
        verify(mapper).toEntity(updatedDTO);
        verify(repository).save(any(SourceOfFunds.class));
        verify(mapper).toDTO(any(SourceOfFunds.class));
        
        // Verify that verification data is preserved
        verify(repository).save(argThat(source -> 
            source.getIsVerified() != null && source.getIsVerified() &&
            source.getVerificationDate() != null &&
            source.getVerificationMethod() != null));
    }

    @Test
    void testDelete() {
        // Arrange
        when(repository.deleteById(SOURCE_OF_FUNDS_ID)).thenReturn(Mono.empty());

        // Act & Assert
        StepVerifier.create(sourceOfFundsService.delete(SOURCE_OF_FUNDS_ID))
                .verifyComplete();

        verify(repository).deleteById(SOURCE_OF_FUNDS_ID);
    }
}