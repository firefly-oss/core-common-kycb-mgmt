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


package io.fireflysoftware.core.kycb.core.services.economic.v1;

import org.fireflyframework.core.filters.FilterRequest;
import org.fireflyframework.core.filters.FilterUtils;
import org.fireflyframework.core.queries.PaginationResponse;
import io.fireflysoftware.core.kycb.core.mappers.economic.v1.EconomicActivityMapper;
import io.fireflysoftware.core.kycb.interfaces.dtos.economic.v1.EconomicActivityDTO;
import io.fireflysoftware.core.kycb.models.entities.economic.v1.EconomicActivity;
import io.fireflysoftware.core.kycb.models.repositories.economic.v1.EconomicActivityRepository;
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
public class EconomicActivityServiceImplTest {

    @Mock
    private EconomicActivityRepository repository;

    @Mock
    private EconomicActivityMapper mapper;

    @Mock
    private FilterUtils filterUtils;

    @InjectMocks
    private EconomicActivityServiceImpl economicActivityService;

    private EconomicActivityDTO economicActivityDTO;
    private EconomicActivity economicActivity;
    private final UUID ECONOMIC_ACTIVITY_ID = UUID.fromString("550e8400-e29b-41d4-a716-446655440017");
    private final UUID PARTY_ID = UUID.fromString("550e8400-e29b-41d4-a716-446655440100");

    @BeforeEach
    void setUp() {
        // Initialize test data
        economicActivityDTO = new EconomicActivityDTO();
        economicActivityDTO.setEconomicActivityId(ECONOMIC_ACTIVITY_ID);
        economicActivityDTO.setPartyId(PARTY_ID);
        economicActivityDTO.setIsPrimary(false);
        
        economicActivity = new EconomicActivity();
        economicActivity.setEconomicActivityId(ECONOMIC_ACTIVITY_ID);
        economicActivity.setPartyId(PARTY_ID);
        economicActivity.setIsPrimary(false);
        economicActivity.setDateCreated(LocalDateTime.now());
    }

    @Test
    void testCreate() {
        // Arrange
        when(mapper.toEntity(any(EconomicActivityDTO.class))).thenReturn(economicActivity);
        when(repository.save(any(EconomicActivity.class))).thenReturn(Mono.just(economicActivity));
        when(mapper.toDTO(any(EconomicActivity.class))).thenReturn(economicActivityDTO);

        // Act & Assert
        StepVerifier.create(economicActivityService.create(economicActivityDTO))
                .expectNext(economicActivityDTO)
                .verifyComplete();

        verify(mapper).toEntity(economicActivityDTO);
        verify(repository).save(economicActivity);
        verify(mapper).toDTO(economicActivity);
    }

    @Test
    void testCreatePrimaryActivity() {
        // Arrange
        economicActivityDTO.setIsPrimary(true);
        economicActivity.setIsPrimary(true);

        EconomicActivity existingPrimaryActivity = new EconomicActivity();
        existingPrimaryActivity.setEconomicActivityId(UUID.fromString("550e8400-e29b-41d4-a716-446655440022"));
        existingPrimaryActivity.setPartyId(PARTY_ID);
        existingPrimaryActivity.setIsPrimary(true);

        when(mapper.toEntity(any(EconomicActivityDTO.class))).thenReturn(economicActivity);
        when(repository.findByPartyIdAndIsPrimaryTrue(PARTY_ID)).thenReturn(Mono.just(existingPrimaryActivity));
        when(repository.save(existingPrimaryActivity)).thenReturn(Mono.just(existingPrimaryActivity));
        when(repository.save(economicActivity)).thenReturn(Mono.just(economicActivity));
        when(mapper.toDTO(economicActivity)).thenReturn(economicActivityDTO);

        // Act & Assert
        StepVerifier.create(economicActivityService.create(economicActivityDTO))
                .expectNext(economicActivityDTO)
                .verifyComplete();

        verify(repository).findByPartyIdAndIsPrimaryTrue(PARTY_ID);
        verify(repository).save(existingPrimaryActivity);
        // The save method is called twice due to the implementation using switchIfEmpty
        verify(repository, times(2)).save(economicActivity);
        verify(mapper).toDTO(economicActivity);

        // Verify that the existing primary activity is no longer primary
        verify(repository).save(argThat(activity ->
            activity.getEconomicActivityId().equals(UUID.fromString("550e8400-e29b-41d4-a716-446655440022")) && !activity.getIsPrimary()));
    }

    @Test
    void testCreatePrimaryActivityWithNoExistingPrimary() {
        // Arrange
        economicActivityDTO.setIsPrimary(true);
        economicActivity.setIsPrimary(true);

        when(mapper.toEntity(any(EconomicActivityDTO.class))).thenReturn(economicActivity);
        when(repository.findByPartyIdAndIsPrimaryTrue(PARTY_ID)).thenReturn(Mono.empty());
        when(repository.save(economicActivity)).thenReturn(Mono.just(economicActivity));
        when(mapper.toDTO(economicActivity)).thenReturn(economicActivityDTO);

        // Act & Assert
        StepVerifier.create(economicActivityService.create(economicActivityDTO))
                .expectNext(economicActivityDTO)
                .verifyComplete();

        verify(repository).findByPartyIdAndIsPrimaryTrue(PARTY_ID);
        // The save method is called twice due to the implementation using switchIfEmpty
        verify(repository, times(2)).save(economicActivity);
        verify(mapper).toDTO(economicActivity);
    }

    @Test
    void testGetById() {
        // Arrange
        when(repository.findById(ECONOMIC_ACTIVITY_ID)).thenReturn(Mono.just(economicActivity));
        when(mapper.toDTO(economicActivity)).thenReturn(economicActivityDTO);

        // Act & Assert
        StepVerifier.create(economicActivityService.getById(ECONOMIC_ACTIVITY_ID))
                .expectNext(economicActivityDTO)
                .verifyComplete();

        verify(repository).findById(ECONOMIC_ACTIVITY_ID);
        verify(mapper).toDTO(economicActivity);
    }

    @Test
    void testUpdate() {
        // Arrange
        LocalDateTime creationDate = LocalDateTime.now();
        
        EconomicActivity existingActivity = new EconomicActivity();
        existingActivity.setEconomicActivityId(ECONOMIC_ACTIVITY_ID);
        existingActivity.setPartyId(PARTY_ID);
        existingActivity.setIsPrimary(false);
        existingActivity.setDateCreated(creationDate);
        
        EconomicActivity updatedActivity = new EconomicActivity();
        updatedActivity.setEconomicActivityId(ECONOMIC_ACTIVITY_ID);
        updatedActivity.setPartyId(PARTY_ID);
        updatedActivity.setIsPrimary(false);
        
        when(repository.findById(ECONOMIC_ACTIVITY_ID)).thenReturn(Mono.just(existingActivity));
        when(mapper.toEntity(economicActivityDTO)).thenReturn(updatedActivity);
        when(repository.save(updatedActivity)).thenReturn(Mono.just(updatedActivity));
        when(mapper.toDTO(updatedActivity)).thenReturn(economicActivityDTO);

        // Act & Assert
        StepVerifier.create(economicActivityService.update(ECONOMIC_ACTIVITY_ID, economicActivityDTO))
                .expectNext(economicActivityDTO)
                .verifyComplete();

        verify(repository).findById(ECONOMIC_ACTIVITY_ID);
        verify(mapper).toEntity(economicActivityDTO);
        verify(repository).save(updatedActivity);
        verify(mapper).toDTO(updatedActivity);
    }

    @Test
    void testUpdateToPrimary() {
        // Arrange
        LocalDateTime creationDate = LocalDateTime.now();
        
        EconomicActivity existingActivity = new EconomicActivity();
        existingActivity.setEconomicActivityId(ECONOMIC_ACTIVITY_ID);
        existingActivity.setPartyId(PARTY_ID);
        existingActivity.setIsPrimary(false);
        existingActivity.setDateCreated(creationDate);
        
        EconomicActivity updatedActivity = new EconomicActivity();
        updatedActivity.setEconomicActivityId(ECONOMIC_ACTIVITY_ID);
        updatedActivity.setPartyId(PARTY_ID);
        updatedActivity.setIsPrimary(true);
        
        EconomicActivity existingPrimaryActivity = new EconomicActivity();
        existingPrimaryActivity.setEconomicActivityId(UUID.fromString("550e8400-e29b-41d4-a716-446655440022"));
        existingPrimaryActivity.setPartyId(PARTY_ID);
        existingPrimaryActivity.setIsPrimary(true);
        
        economicActivityDTO.setIsPrimary(true);
        
        when(repository.findById(ECONOMIC_ACTIVITY_ID)).thenReturn(Mono.just(existingActivity));
        when(mapper.toEntity(economicActivityDTO)).thenReturn(updatedActivity);
        when(repository.findByPartyIdAndIsPrimaryTrue(PARTY_ID)).thenReturn(Mono.just(existingPrimaryActivity));
        when(repository.save(existingPrimaryActivity)).thenReturn(Mono.just(existingPrimaryActivity));
        when(repository.save(updatedActivity)).thenReturn(Mono.just(updatedActivity));
        when(mapper.toDTO(updatedActivity)).thenReturn(economicActivityDTO);

        // Act & Assert
        StepVerifier.create(economicActivityService.update(ECONOMIC_ACTIVITY_ID, economicActivityDTO))
                .expectNext(economicActivityDTO)
                .verifyComplete();

        verify(repository).findById(ECONOMIC_ACTIVITY_ID);
        verify(repository).findByPartyIdAndIsPrimaryTrue(PARTY_ID);
        verify(repository).save(existingPrimaryActivity);
        // The save method is called twice due to the implementation using switchIfEmpty
        verify(repository, times(2)).save(updatedActivity);
        verify(mapper).toDTO(updatedActivity);

        // Verify that the existing primary activity is no longer primary
        verify(repository).save(argThat(activity ->
            activity.getEconomicActivityId().equals(UUID.fromString("550e8400-e29b-41d4-a716-446655440022")) && !activity.getIsPrimary()));
    }

    @Test
    void testUpdateToPrimaryWithNoExistingPrimary() {
        // Arrange
        LocalDateTime creationDate = LocalDateTime.now();
        
        EconomicActivity existingActivity = new EconomicActivity();
        existingActivity.setEconomicActivityId(ECONOMIC_ACTIVITY_ID);
        existingActivity.setPartyId(PARTY_ID);
        existingActivity.setIsPrimary(false);
        existingActivity.setDateCreated(creationDate);
        
        EconomicActivity updatedActivity = new EconomicActivity();
        updatedActivity.setEconomicActivityId(ECONOMIC_ACTIVITY_ID);
        updatedActivity.setPartyId(PARTY_ID);
        updatedActivity.setIsPrimary(true);
        
        economicActivityDTO.setIsPrimary(true);
        
        when(repository.findById(ECONOMIC_ACTIVITY_ID)).thenReturn(Mono.just(existingActivity));
        when(mapper.toEntity(economicActivityDTO)).thenReturn(updatedActivity);
        when(repository.findByPartyIdAndIsPrimaryTrue(PARTY_ID)).thenReturn(Mono.empty());
        when(repository.save(updatedActivity)).thenReturn(Mono.just(updatedActivity));
        when(mapper.toDTO(updatedActivity)).thenReturn(economicActivityDTO);

        // Act & Assert
        StepVerifier.create(economicActivityService.update(ECONOMIC_ACTIVITY_ID, economicActivityDTO))
                .expectNext(economicActivityDTO)
                .verifyComplete();

        verify(repository).findById(ECONOMIC_ACTIVITY_ID);
        verify(repository).findByPartyIdAndIsPrimaryTrue(PARTY_ID);
        // The save method is called twice due to the implementation using switchIfEmpty
        verify(repository, times(2)).save(updatedActivity);
        verify(mapper).toDTO(updatedActivity);
    }

    @Test
    void testDelete() {
        // Arrange
        when(repository.deleteById(ECONOMIC_ACTIVITY_ID)).thenReturn(Mono.empty());

        // Act & Assert
        StepVerifier.create(economicActivityService.delete(ECONOMIC_ACTIVITY_ID))
                .verifyComplete();

        verify(repository).deleteById(ECONOMIC_ACTIVITY_ID);
    }
}