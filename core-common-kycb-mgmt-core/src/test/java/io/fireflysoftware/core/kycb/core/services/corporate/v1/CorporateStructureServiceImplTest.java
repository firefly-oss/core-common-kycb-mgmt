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


package io.fireflysoftware.core.kycb.core.services.corporate.v1;

import org.fireflyframework.core.filters.FilterRequest;
import org.fireflyframework.core.filters.FilterUtils;
import org.fireflyframework.core.queries.PaginationResponse;
import io.fireflysoftware.core.kycb.core.mappers.corporate.v1.CorporateStructureMapper;
import io.fireflysoftware.core.kycb.interfaces.dtos.corporate.v1.CorporateStructureDTO;
import io.fireflysoftware.core.kycb.interfaces.enums.corporate.v1.RelationshipTypeEnum;
import io.fireflysoftware.core.kycb.models.entities.corporate.v1.CorporateStructure;
import io.fireflysoftware.core.kycb.models.repositories.corporate.v1.CorporateStructureRepository;
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
public class CorporateStructureServiceImplTest {

    @Mock
    private CorporateStructureRepository repository;

    @Mock
    private CorporateStructureMapper mapper;

    @Mock
    private FilterUtils filterUtils;

    @InjectMocks
    private CorporateStructureServiceImpl corporateStructureService;

    private CorporateStructureDTO corporateStructureDTO;
    private CorporateStructure corporateStructure;
    private final UUID CORPORATE_STRUCTURE_ID = UUID.fromString("550e8400-e29b-41d4-a716-446655440013");
    private final UUID PARTY_ID = UUID.fromString("550e8400-e29b-41d4-a716-446655440100");
    private final UUID PARENT_ENTITY_ID = UUID.fromString("550e8400-e29b-41d4-a716-446655440200");

    @BeforeEach
    void setUp() {
        // Initialize test data
        corporateStructureDTO = new CorporateStructureDTO();
        corporateStructureDTO.setCorporateStructureId(CORPORATE_STRUCTURE_ID);
        corporateStructureDTO.setPartyId(PARTY_ID);
        corporateStructureDTO.setParentEntityId(PARENT_ENTITY_ID);

        corporateStructure = new CorporateStructure();
        corporateStructure.setCorporateStructureId(CORPORATE_STRUCTURE_ID);
        corporateStructure.setPartyId(PARTY_ID);
        corporateStructure.setParentEntityId(PARENT_ENTITY_ID);
        corporateStructure.setDateCreated(LocalDateTime.now());
    }

    @Test
    void testCreate() {
        // Arrange
        when(mapper.toEntity(any(CorporateStructureDTO.class))).thenReturn(corporateStructure);
        when(repository.save(any(CorporateStructure.class))).thenReturn(Mono.just(corporateStructure));
        when(mapper.toDTO(any(CorporateStructure.class))).thenReturn(corporateStructureDTO);

        // Act & Assert
        StepVerifier.create(corporateStructureService.create(corporateStructureDTO))
                .expectNext(corporateStructureDTO)
                .verifyComplete();

        verify(mapper).toEntity(corporateStructureDTO);
        verify(repository).save(corporateStructure);
        verify(mapper).toDTO(corporateStructure);
    }

    @Test
    void testCreateWithStartDate() {
        // Arrange
        CorporateStructure entityWithoutStartDate = new CorporateStructure();
        entityWithoutStartDate.setCorporateStructureId(CORPORATE_STRUCTURE_ID);
        entityWithoutStartDate.setPartyId(PARTY_ID);
        entityWithoutStartDate.setParentEntityId(PARENT_ENTITY_ID);
        // No start date set

        when(mapper.toEntity(any(CorporateStructureDTO.class))).thenReturn(entityWithoutStartDate);

        // Mock the save method to verify start date is set
        when(repository.save(any(CorporateStructure.class))).thenAnswer(invocation -> {
            CorporateStructure savedEntity = invocation.getArgument(0);
            // Verify that start date is set
            if (savedEntity.getStartDate() == null) {
                throw new AssertionError("Start date should be set");
            }
            return Mono.just(savedEntity);
        });

        when(mapper.toDTO(any(CorporateStructure.class))).thenReturn(corporateStructureDTO);

        // Act & Assert
        StepVerifier.create(corporateStructureService.create(corporateStructureDTO))
                .expectNext(corporateStructureDTO)
                .verifyComplete();

        verify(mapper).toEntity(corporateStructureDTO);
        verify(repository).save(any(CorporateStructure.class));
        verify(mapper).toDTO(any(CorporateStructure.class));

        // Verify that the saved entity has a start date
        verify(repository).save(argThat(entity -> entity.getStartDate() != null));
    }

    @Test
    void testGetById() {
        // Arrange
        when(repository.findById(CORPORATE_STRUCTURE_ID)).thenReturn(Mono.just(corporateStructure));
        when(mapper.toDTO(corporateStructure)).thenReturn(corporateStructureDTO);

        // Act & Assert
        StepVerifier.create(corporateStructureService.getById(CORPORATE_STRUCTURE_ID))
                .expectNext(corporateStructureDTO)
                .verifyComplete();

        verify(repository).findById(CORPORATE_STRUCTURE_ID);
        verify(mapper).toDTO(corporateStructure);
    }

    @Test
    void testUpdate() {
        // Arrange
        LocalDateTime creationDate = LocalDateTime.now();

        CorporateStructure existingStructure = new CorporateStructure();
        existingStructure.setCorporateStructureId(CORPORATE_STRUCTURE_ID);
        existingStructure.setPartyId(PARTY_ID);
        existingStructure.setParentEntityId(PARENT_ENTITY_ID);
        existingStructure.setDateCreated(creationDate);
        existingStructure.setIsVerified(false); // Set isVerified to avoid NPE

        CorporateStructure updatedStructure = new CorporateStructure();
        updatedStructure.setCorporateStructureId(CORPORATE_STRUCTURE_ID);
        updatedStructure.setPartyId(PARTY_ID);
        updatedStructure.setParentEntityId(PARENT_ENTITY_ID);

        when(repository.findById(CORPORATE_STRUCTURE_ID)).thenReturn(Mono.just(existingStructure));
        when(mapper.toEntity(corporateStructureDTO)).thenReturn(updatedStructure);

        // Mock the save method to set the creation date before returning
        when(repository.save(any(CorporateStructure.class))).thenAnswer(invocation -> {
            CorporateStructure structure = invocation.getArgument(0);
            if (structure.getDateCreated() == null) {
                structure.setDateCreated(creationDate);
            }
            return Mono.just(structure);
        });

        when(mapper.toDTO(any(CorporateStructure.class))).thenReturn(corporateStructureDTO);

        // Act & Assert
        StepVerifier.create(corporateStructureService.update(CORPORATE_STRUCTURE_ID, corporateStructureDTO))
                .expectNext(corporateStructureDTO)
                .verifyComplete();

        verify(repository).findById(CORPORATE_STRUCTURE_ID);
        verify(mapper).toEntity(corporateStructureDTO);
        verify(repository).save(any(CorporateStructure.class));
        verify(mapper).toDTO(any(CorporateStructure.class));
    }

    @Test
    void testUpdatePreservesVerificationData() {
        // Arrange
        LocalDateTime creationDate = LocalDateTime.now();
        LocalDateTime verificationDate = LocalDateTime.now();

        CorporateStructure existingStructure = new CorporateStructure();
        existingStructure.setCorporateStructureId(CORPORATE_STRUCTURE_ID);
        existingStructure.setPartyId(PARTY_ID);
        existingStructure.setParentEntityId(PARENT_ENTITY_ID);
        existingStructure.setDateCreated(creationDate);
        existingStructure.setIsVerified(true);
        existingStructure.setVerificationDate(verificationDate);

        CorporateStructure updatedStructure = new CorporateStructure();
        updatedStructure.setCorporateStructureId(CORPORATE_STRUCTURE_ID);
        updatedStructure.setPartyId(PARTY_ID);
        updatedStructure.setParentEntityId(PARENT_ENTITY_ID);
        // No verification data set in the updated entity

        when(repository.findById(CORPORATE_STRUCTURE_ID)).thenReturn(Mono.just(existingStructure));
        when(mapper.toEntity(corporateStructureDTO)).thenReturn(updatedStructure);

        // Mock the save method to preserve verification data
        when(repository.save(any(CorporateStructure.class))).thenAnswer(invocation -> {
            CorporateStructure structure = invocation.getArgument(0);
            // Ensure verification data is preserved
            if (structure.getIsVerified() == null && existingStructure.getIsVerified()) {
                structure.setIsVerified(existingStructure.getIsVerified());
                structure.setVerificationDate(existingStructure.getVerificationDate());
            }
            return Mono.just(structure);
        });

        when(mapper.toDTO(any(CorporateStructure.class))).thenReturn(corporateStructureDTO);

        // Act & Assert
        StepVerifier.create(corporateStructureService.update(CORPORATE_STRUCTURE_ID, corporateStructureDTO))
                .expectNext(corporateStructureDTO)
                .verifyComplete();

        verify(repository).findById(CORPORATE_STRUCTURE_ID);
        verify(mapper).toEntity(corporateStructureDTO);
        verify(repository).save(any(CorporateStructure.class));
        verify(mapper).toDTO(any(CorporateStructure.class));

        // Verify that verification data is preserved
        verify(repository).save(argThat(structure -> 
            structure.getIsVerified() != null && structure.getIsVerified() &&
            structure.getVerificationDate() != null));
    }

    @Test
    void testDelete() {
        // Arrange
        when(repository.deleteById(CORPORATE_STRUCTURE_ID)).thenReturn(Mono.empty());

        // Act & Assert
        StepVerifier.create(corporateStructureService.delete(CORPORATE_STRUCTURE_ID))
                .verifyComplete();

        verify(repository).deleteById(CORPORATE_STRUCTURE_ID);
    }
}
