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


package io.fireflysoftware.core.kycb.core.services.aml.v1;

import org.fireflyframework.core.filters.FilterRequest;
import org.fireflyframework.core.filters.FilterUtils;
import org.fireflyframework.core.queries.PaginationResponse;
import io.fireflysoftware.core.kycb.core.mappers.aml.v1.AmlScreeningMapper;
import io.fireflysoftware.core.kycb.interfaces.dtos.aml.v1.AmlScreeningDTO;
import io.fireflysoftware.core.kycb.models.entities.aml.v1.AmlScreening;
import io.fireflysoftware.core.kycb.models.repositories.aml.v1.AmlScreeningRepository;
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
public class AmlScreeningServiceImplTest {

    @Mock
    private AmlScreeningRepository repository;

    @Mock
    private AmlScreeningMapper mapper;

    @Mock
    private FilterUtils filterUtils;

    @InjectMocks
    private AmlScreeningServiceImpl amlScreeningService;

    private AmlScreeningDTO amlScreeningDTO;
    private AmlScreening amlScreening;
    private final UUID AML_SCREENING_ID = UUID.fromString("550e8400-e29b-41d4-a716-446655440007");

    @BeforeEach
    void setUp() {
        // Initialize test data
        amlScreeningDTO = new AmlScreeningDTO();
        amlScreeningDTO.setAmlScreeningId(AML_SCREENING_ID);
        
        amlScreening = new AmlScreening();
        amlScreening.setAmlScreeningId(AML_SCREENING_ID);
        amlScreening.setDateCreated(LocalDateTime.now());
    }

    @Test
    void testCreate() {
        // Arrange
        when(mapper.toEntity(any(AmlScreeningDTO.class))).thenReturn(amlScreening);
        when(repository.save(any(AmlScreening.class))).thenReturn(Mono.just(amlScreening));
        when(mapper.toDTO(any(AmlScreening.class))).thenReturn(amlScreeningDTO);

        // Act & Assert
        StepVerifier.create(amlScreeningService.create(amlScreeningDTO))
                .expectNext(amlScreeningDTO)
                .verifyComplete();

        verify(mapper).toEntity(amlScreeningDTO);
        verify(repository).save(amlScreening);
        verify(mapper).toDTO(amlScreening);
    }

    @Test
    void testGetById() {
        // Arrange
        when(repository.findById(AML_SCREENING_ID)).thenReturn(Mono.just(amlScreening));
        when(mapper.toDTO(amlScreening)).thenReturn(amlScreeningDTO);

        // Act & Assert
        StepVerifier.create(amlScreeningService.getById(AML_SCREENING_ID))
                .expectNext(amlScreeningDTO)
                .verifyComplete();

        verify(repository).findById(AML_SCREENING_ID);
        verify(mapper).toDTO(amlScreening);
    }

    @Test
    void testUpdate() {
        // Arrange
        when(repository.findById(AML_SCREENING_ID)).thenReturn(Mono.just(amlScreening));
        when(mapper.toEntity(amlScreeningDTO)).thenReturn(amlScreening);
        when(repository.save(amlScreening)).thenReturn(Mono.just(amlScreening));
        when(mapper.toDTO(amlScreening)).thenReturn(amlScreeningDTO);

        // Act & Assert
        StepVerifier.create(amlScreeningService.update(AML_SCREENING_ID, amlScreeningDTO))
                .expectNext(amlScreeningDTO)
                .verifyComplete();

        verify(repository).findById(AML_SCREENING_ID);
        verify(mapper).toEntity(amlScreeningDTO);
        verify(repository).save(amlScreening);
        verify(mapper).toDTO(amlScreening);
    }

    @Test
    void testDelete() {
        // Arrange
        when(repository.deleteById(AML_SCREENING_ID)).thenReturn(Mono.empty());

        // Act & Assert
        StepVerifier.create(amlScreeningService.delete(AML_SCREENING_ID))
                .verifyComplete();

        verify(repository).deleteById(AML_SCREENING_ID);
    }
}