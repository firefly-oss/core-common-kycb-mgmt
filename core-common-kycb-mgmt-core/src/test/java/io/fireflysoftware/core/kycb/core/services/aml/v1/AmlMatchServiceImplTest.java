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
import io.fireflysoftware.core.kycb.core.mappers.aml.v1.AmlMatchMapper;
import io.fireflysoftware.core.kycb.interfaces.dtos.aml.v1.AmlMatchDTO;
import io.fireflysoftware.core.kycb.models.entities.aml.v1.AmlMatch;
import io.fireflysoftware.core.kycb.models.repositories.aml.v1.AmlMatchRepository;
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
public class AmlMatchServiceImplTest {

    @Mock
    private AmlMatchRepository repository;

    @Mock
    private AmlMatchMapper mapper;

    @Mock
    private FilterUtils filterUtils;

    @InjectMocks
    private AmlMatchServiceImpl amlMatchService;

    private AmlMatchDTO amlMatchDTO;
    private AmlMatch amlMatch;
    private final UUID AML_MATCH_ID = UUID.fromString("550e8400-e29b-41d4-a716-446655440009");

    @BeforeEach
    void setUp() {
        // Initialize test data
        amlMatchDTO = new AmlMatchDTO();
        amlMatchDTO.setAmlMatchId(AML_MATCH_ID);
        
        amlMatch = new AmlMatch();
        amlMatch.setAmlMatchId(AML_MATCH_ID);
        amlMatch.setDateCreated(LocalDateTime.now());
    }

    @Test
    void testCreate() {
        // Arrange
        when(mapper.toEntity(any(AmlMatchDTO.class))).thenReturn(amlMatch);
        when(repository.save(any(AmlMatch.class))).thenReturn(Mono.just(amlMatch));
        when(mapper.toDTO(any(AmlMatch.class))).thenReturn(amlMatchDTO);

        // Act & Assert
        StepVerifier.create(amlMatchService.create(amlMatchDTO))
                .expectNext(amlMatchDTO)
                .verifyComplete();

        verify(mapper).toEntity(amlMatchDTO);
        verify(repository).save(amlMatch);
        verify(mapper).toDTO(amlMatch);
    }

    @Test
    void testGetById() {
        // Arrange
        when(repository.findById(AML_MATCH_ID)).thenReturn(Mono.just(amlMatch));
        when(mapper.toDTO(amlMatch)).thenReturn(amlMatchDTO);

        // Act & Assert
        StepVerifier.create(amlMatchService.getById(AML_MATCH_ID))
                .expectNext(amlMatchDTO)
                .verifyComplete();

        verify(repository).findById(AML_MATCH_ID);
        verify(mapper).toDTO(amlMatch);
    }

    @Test
    void testUpdate() {
        // Arrange
        when(repository.findById(AML_MATCH_ID)).thenReturn(Mono.just(amlMatch));
        when(mapper.toEntity(amlMatchDTO)).thenReturn(amlMatch);
        when(repository.save(amlMatch)).thenReturn(Mono.just(amlMatch));
        when(mapper.toDTO(amlMatch)).thenReturn(amlMatchDTO);

        // Act & Assert
        StepVerifier.create(amlMatchService.update(AML_MATCH_ID, amlMatchDTO))
                .expectNext(amlMatchDTO)
                .verifyComplete();

        verify(repository).findById(AML_MATCH_ID);
        verify(mapper).toEntity(amlMatchDTO);
        verify(repository).save(amlMatch);
        verify(mapper).toDTO(amlMatch);
    }

    @Test
    void testDelete() {
        // Arrange
        when(repository.deleteById(AML_MATCH_ID)).thenReturn(Mono.empty());

        // Act & Assert
        StepVerifier.create(amlMatchService.delete(AML_MATCH_ID))
                .verifyComplete();

        verify(repository).deleteById(AML_MATCH_ID);
    }
}