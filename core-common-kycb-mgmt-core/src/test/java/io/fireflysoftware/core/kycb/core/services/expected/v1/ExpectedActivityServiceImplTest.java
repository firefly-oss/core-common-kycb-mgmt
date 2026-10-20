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


package io.fireflysoftware.core.kycb.core.services.expected.v1;

import org.fireflyframework.core.filters.FilterRequest;
import org.fireflyframework.core.filters.FilterUtils;
import org.fireflyframework.core.queries.PaginationResponse;
import io.fireflysoftware.core.kycb.core.mappers.expected.v1.ExpectedActivityMapper;
import io.fireflysoftware.core.kycb.interfaces.dtos.expected.v1.ExpectedActivityDTO;
import io.fireflysoftware.core.kycb.models.entities.expected.v1.ExpectedActivity;
import io.fireflysoftware.core.kycb.models.repositories.expected.v1.ExpectedActivityRepository;
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
public class ExpectedActivityServiceImplTest {

    @Mock
    private ExpectedActivityRepository repository;

    @Mock
    private ExpectedActivityMapper mapper;

    @Mock
    private FilterUtils filterUtils;

    @InjectMocks
    private ExpectedActivityServiceImpl expectedActivityService;

    private ExpectedActivityDTO expectedActivityDTO;
    private ExpectedActivity expectedActivity;
    private final UUID EXPECTED_ACTIVITY_ID = UUID.fromString("550e8400-e29b-41d4-a716-446655440012");
    private final UUID PARTY_ID = UUID.fromString("550e8400-e29b-41d4-a716-446655440100");

    @BeforeEach
    void setUp() {
        // Initialize test data
        expectedActivityDTO = new ExpectedActivityDTO();
        expectedActivityDTO.setExpectedActivityId(EXPECTED_ACTIVITY_ID);
        expectedActivityDTO.setPartyId(PARTY_ID);
        expectedActivityDTO.setActivityTypeCode("WIRE_TRANSFER");
        expectedActivityDTO.setExpectedMonthlyVolume(new BigDecimal("5000.00"));
        expectedActivityDTO.setExpectedAnnualVolume(new BigDecimal("60000.00"));
        expectedActivityDTO.setExpectedTransactionCount(10);
        expectedActivityDTO.setCurrencyIsoCode("USD");
        expectedActivityDTO.setTransactionFreqCode("MONTHLY");
        expectedActivityDTO.setAnticipatedCountries("US,CA,MX");
        expectedActivityDTO.setIsHighValue(false);
        expectedActivityDTO.setExpectedCounterparties("Suppliers, Customers");
        expectedActivityDTO.setCashIntensive(false);
        expectedActivityDTO.setTaxHavenTransactions(false);
        expectedActivityDTO.setDeclarationModelCode("STANDARD");
        expectedActivityDTO.setVerificationNotes("Verified during onboarding");
        
        expectedActivity = new ExpectedActivity();
        expectedActivity.setExpectedActivityId(EXPECTED_ACTIVITY_ID);
        expectedActivity.setPartyId(PARTY_ID);
        expectedActivity.setActivityTypeCode("WIRE_TRANSFER");
        expectedActivity.setExpectedMonthlyVolume(new BigDecimal("5000.00"));
        expectedActivity.setExpectedAnnualVolume(new BigDecimal("60000.00"));
        expectedActivity.setExpectedTransactionCount(10);
        expectedActivity.setCurrencyIsoCode("USD");
        expectedActivity.setTransactionFreqCode("MONTHLY");
        expectedActivity.setAnticipatedCountries("US,CA,MX");
        expectedActivity.setIsHighValue(false);
        expectedActivity.setExpectedCounterparties("Suppliers, Customers");
        expectedActivity.setCashIntensive(false);
        expectedActivity.setTaxHavenTransactions(false);
        expectedActivity.setDeclarationModelCode("STANDARD");
        expectedActivity.setVerificationNotes("Verified during onboarding");
        expectedActivity.setDateCreated(LocalDateTime.now());
    }

    @Test
    void testCreate() {
        // Arrange
        when(mapper.toEntity(any(ExpectedActivityDTO.class))).thenReturn(expectedActivity);
        when(repository.save(any(ExpectedActivity.class))).thenReturn(Mono.just(expectedActivity));
        when(mapper.toDTO(any(ExpectedActivity.class))).thenReturn(expectedActivityDTO);

        // Act & Assert
        StepVerifier.create(expectedActivityService.create(expectedActivityDTO))
                .expectNext(expectedActivityDTO)
                .verifyComplete();

        verify(mapper).toEntity(expectedActivityDTO);
        verify(repository).save(expectedActivity);
        verify(mapper).toDTO(expectedActivity);
    }

    @Test
    void testGetById() {
        // Arrange
        when(repository.findById(EXPECTED_ACTIVITY_ID)).thenReturn(Mono.just(expectedActivity));
        when(mapper.toDTO(expectedActivity)).thenReturn(expectedActivityDTO);

        // Act & Assert
        StepVerifier.create(expectedActivityService.getById(EXPECTED_ACTIVITY_ID))
                .expectNext(expectedActivityDTO)
                .verifyComplete();

        verify(repository).findById(EXPECTED_ACTIVITY_ID);
        verify(mapper).toDTO(expectedActivity);
    }

    @Test
    void testUpdate() {
        // Arrange
        LocalDateTime creationDate = LocalDateTime.now();
        
        ExpectedActivity existingActivity = new ExpectedActivity();
        existingActivity.setExpectedActivityId(EXPECTED_ACTIVITY_ID);
        existingActivity.setPartyId(PARTY_ID);
        existingActivity.setActivityTypeCode("WIRE_TRANSFER");
        existingActivity.setDateCreated(creationDate);
        
        ExpectedActivity updatedActivity = new ExpectedActivity();
        updatedActivity.setExpectedActivityId(EXPECTED_ACTIVITY_ID);
        updatedActivity.setPartyId(PARTY_ID);
        updatedActivity.setActivityTypeCode("ACH_TRANSFER");
        updatedActivity.setExpectedMonthlyVolume(new BigDecimal("10000.00"));
        updatedActivity.setExpectedAnnualVolume(new BigDecimal("120000.00"));
        updatedActivity.setExpectedTransactionCount(20);
        
        ExpectedActivityDTO updatedDTO = new ExpectedActivityDTO();
        updatedDTO.setExpectedActivityId(EXPECTED_ACTIVITY_ID);
        updatedDTO.setPartyId(PARTY_ID);
        updatedDTO.setActivityTypeCode("ACH_TRANSFER");
        updatedDTO.setExpectedMonthlyVolume(new BigDecimal("10000.00"));
        updatedDTO.setExpectedAnnualVolume(new BigDecimal("120000.00"));
        updatedDTO.setExpectedTransactionCount(20);
        
        when(repository.findById(EXPECTED_ACTIVITY_ID)).thenReturn(Mono.just(existingActivity));
        when(mapper.toEntity(updatedDTO)).thenReturn(updatedActivity);
        
        // Mock the save method to set the creation date before returning
        when(repository.save(any(ExpectedActivity.class))).thenAnswer(invocation -> {
            ExpectedActivity activity = invocation.getArgument(0);
            if (activity.getDateCreated() == null) {
                activity.setDateCreated(creationDate);
            }
            return Mono.just(activity);
        });
        
        when(mapper.toDTO(any(ExpectedActivity.class))).thenReturn(updatedDTO);

        // Act & Assert
        StepVerifier.create(expectedActivityService.update(EXPECTED_ACTIVITY_ID, updatedDTO))
                .expectNext(updatedDTO)
                .verifyComplete();

        verify(repository).findById(EXPECTED_ACTIVITY_ID);
        verify(mapper).toEntity(updatedDTO);
        verify(repository).save(any(ExpectedActivity.class));
        verify(mapper).toDTO(any(ExpectedActivity.class));
        
        // Verify that the creation date is preserved
        verify(repository).save(argThat(activity -> 
            activity.getDateCreated() != null && activity.getDateCreated().equals(creationDate)));
    }

    @Test
    void testDelete() {
        // Arrange
        when(repository.deleteById(EXPECTED_ACTIVITY_ID)).thenReturn(Mono.empty());

        // Act & Assert
        StepVerifier.create(expectedActivityService.delete(EXPECTED_ACTIVITY_ID))
                .verifyComplete();

        verify(repository).deleteById(EXPECTED_ACTIVITY_ID);
    }
}