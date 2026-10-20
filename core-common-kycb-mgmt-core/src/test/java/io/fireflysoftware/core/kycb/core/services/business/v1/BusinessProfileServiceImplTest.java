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


package io.fireflysoftware.core.kycb.core.services.business.v1;

import org.fireflyframework.core.filters.FilterRequest;
import org.fireflyframework.core.filters.FilterUtils;
import org.fireflyframework.core.queries.PaginationResponse;
import io.fireflysoftware.core.kycb.core.mappers.business.v1.BusinessProfileMapper;
import io.fireflysoftware.core.kycb.interfaces.dtos.business.v1.BusinessProfileDTO;
import io.fireflysoftware.core.kycb.models.entities.business.v1.BusinessProfile;
import io.fireflysoftware.core.kycb.models.repositories.business.v1.BusinessProfileRepository;
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
public class BusinessProfileServiceImplTest {

    @Mock
    private BusinessProfileRepository repository;

    @Mock
    private BusinessProfileMapper mapper;

    @Mock
    private FilterUtils filterUtils;

    @InjectMocks
    private BusinessProfileServiceImpl businessProfileService;

    private BusinessProfileDTO businessProfileDTO;
    private BusinessProfile businessProfile;
    private final UUID BUSINESS_PROFILE_ID = UUID.fromString("550e8400-e29b-41d4-a716-446655440020");
    private final UUID PARTY_ID = UUID.fromString("550e8400-e29b-41d4-a716-446655440100");

    @BeforeEach
    void setUp() {
        // Initialize test data
        businessProfileDTO = new BusinessProfileDTO();
        businessProfileDTO.setBusinessProfileId(BUSINESS_PROFILE_ID);
        businessProfileDTO.setPartyId(PARTY_ID);
        
        businessProfile = new BusinessProfile();
        businessProfile.setBusinessProfileId(BUSINESS_PROFILE_ID);
        businessProfile.setPartyId(PARTY_ID);
        businessProfile.setDateCreated(LocalDateTime.now());
    }

    @Test
    void testCreate() {
        // Arrange
        when(mapper.toEntity(any(BusinessProfileDTO.class))).thenReturn(businessProfile);
        when(repository.save(any(BusinessProfile.class))).thenReturn(Mono.just(businessProfile));
        when(mapper.toDTO(any(BusinessProfile.class))).thenReturn(businessProfileDTO);

        // Act & Assert
        StepVerifier.create(businessProfileService.create(businessProfileDTO))
                .expectNext(businessProfileDTO)
                .verifyComplete();

        verify(mapper).toEntity(businessProfileDTO);
        verify(repository).save(businessProfile);
        verify(mapper).toDTO(businessProfile);
    }

    @Test
    void testGetById() {
        // Arrange
        when(repository.findById(BUSINESS_PROFILE_ID)).thenReturn(Mono.just(businessProfile));
        when(mapper.toDTO(businessProfile)).thenReturn(businessProfileDTO);

        // Act & Assert
        StepVerifier.create(businessProfileService.getById(BUSINESS_PROFILE_ID))
                .expectNext(businessProfileDTO)
                .verifyComplete();

        verify(repository).findById(BUSINESS_PROFILE_ID);
        verify(mapper).toDTO(businessProfile);
    }

    @Test
    void testUpdate() {
        // Arrange
        LocalDateTime creationDate = LocalDateTime.now();
        
        BusinessProfile existingProfile = new BusinessProfile();
        existingProfile.setBusinessProfileId(BUSINESS_PROFILE_ID);
        existingProfile.setPartyId(PARTY_ID);
        existingProfile.setDateCreated(creationDate);
        
        BusinessProfile updatedProfile = new BusinessProfile();
        updatedProfile.setBusinessProfileId(BUSINESS_PROFILE_ID);
        updatedProfile.setPartyId(PARTY_ID);
        
        when(repository.findById(BUSINESS_PROFILE_ID)).thenReturn(Mono.just(existingProfile));
        when(mapper.toEntity(businessProfileDTO)).thenReturn(updatedProfile);
        
        // Mock the save method to set the creation date before returning
        when(repository.save(any(BusinessProfile.class))).thenAnswer(invocation -> {
            BusinessProfile profile = invocation.getArgument(0);
            if (profile.getDateCreated() == null) {
                profile.setDateCreated(creationDate);
            }
            return Mono.just(profile);
        });
        
        when(mapper.toDTO(any(BusinessProfile.class))).thenReturn(businessProfileDTO);

        // Act & Assert
        StepVerifier.create(businessProfileService.update(BUSINESS_PROFILE_ID, businessProfileDTO))
                .expectNext(businessProfileDTO)
                .verifyComplete();

        verify(repository).findById(BUSINESS_PROFILE_ID);
        verify(mapper).toEntity(businessProfileDTO);
        verify(repository).save(any(BusinessProfile.class));
        verify(mapper).toDTO(any(BusinessProfile.class));
    }

    @Test
    void testDelete() {
        // Arrange
        when(repository.deleteById(BUSINESS_PROFILE_ID)).thenReturn(Mono.empty());

        // Act & Assert
        StepVerifier.create(businessProfileService.delete(BUSINESS_PROFILE_ID))
                .verifyComplete();

        verify(repository).deleteById(BUSINESS_PROFILE_ID);
    }
}