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


package io.fireflysoftware.core.kycb.core.services.kyc.v1;

import org.fireflyframework.core.filters.FilterRequest;
import org.fireflyframework.core.filters.FilterUtils;
import org.fireflyframework.core.queries.PaginationResponse;
import io.fireflysoftware.core.kycb.core.mappers.kyc.v1.KycVerificationMapper;
import io.fireflysoftware.core.kycb.interfaces.dtos.kyc.v1.KycVerificationDTO;
import io.fireflysoftware.core.kycb.models.entities.kyc.v1.KycVerification;
import io.fireflysoftware.core.kycb.models.repositories.kyc.v1.KycVerificationRepository;
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
public class KycVerificationServiceImplTest {

    @Mock
    private KycVerificationRepository repository;

    @Mock
    private KycVerificationMapper mapper;

    @Mock
    private FilterUtils filterUtils;

    @InjectMocks
    private KycVerificationServiceImpl kycVerificationService;

    private KycVerificationDTO kycVerificationDTO;
    private KycVerification kycVerification;
    private final UUID KYC_VERIFICATION_ID = UUID.fromString("550e8400-e29b-41d4-a716-446655440001");

    @BeforeEach
    void setUp() {
        // Initialize test data
        kycVerificationDTO = new KycVerificationDTO();
        kycVerificationDTO.setKycVerificationId(KYC_VERIFICATION_ID);
        
        kycVerification = new KycVerification();
        kycVerification.setKycVerificationId(KYC_VERIFICATION_ID);
        kycVerification.setDateCreated(LocalDateTime.now());
    }

    @Test
    void testCreate() {
        // Arrange
        when(mapper.toEntity(any(KycVerificationDTO.class))).thenReturn(kycVerification);
        when(repository.save(any(KycVerification.class))).thenReturn(Mono.just(kycVerification));
        when(mapper.toDTO(any(KycVerification.class))).thenReturn(kycVerificationDTO);

        // Act & Assert
        StepVerifier.create(kycVerificationService.create(kycVerificationDTO))
                .expectNext(kycVerificationDTO)
                .verifyComplete();

        verify(mapper).toEntity(kycVerificationDTO);
        verify(repository).save(kycVerification);
        verify(mapper).toDTO(kycVerification);
    }

    @Test
    void testGetById() {
        // Arrange
        when(repository.findById(KYC_VERIFICATION_ID)).thenReturn(Mono.just(kycVerification));
        when(mapper.toDTO(kycVerification)).thenReturn(kycVerificationDTO);

        // Act & Assert
        StepVerifier.create(kycVerificationService.getById(KYC_VERIFICATION_ID))
                .expectNext(kycVerificationDTO)
                .verifyComplete();

        verify(repository).findById(KYC_VERIFICATION_ID);
        verify(mapper).toDTO(kycVerification);
    }

    @Test
    void testUpdate() {
        // Arrange
        when(repository.findById(KYC_VERIFICATION_ID)).thenReturn(Mono.just(kycVerification));
        when(mapper.toEntity(kycVerificationDTO)).thenReturn(kycVerification);
        when(repository.save(kycVerification)).thenReturn(Mono.just(kycVerification));
        when(mapper.toDTO(kycVerification)).thenReturn(kycVerificationDTO);

        // Act & Assert
        StepVerifier.create(kycVerificationService.update(KYC_VERIFICATION_ID, kycVerificationDTO))
                .expectNext(kycVerificationDTO)
                .verifyComplete();

        verify(repository).findById(KYC_VERIFICATION_ID);
        verify(mapper).toEntity(kycVerificationDTO);
        verify(repository).save(kycVerification);
        verify(mapper).toDTO(kycVerification);
    }

    @Test
    void testDelete() {
        // Arrange
        when(repository.deleteById(KYC_VERIFICATION_ID)).thenReturn(Mono.empty());

        // Act & Assert
        StepVerifier.create(kycVerificationService.delete(KYC_VERIFICATION_ID))
                .verifyComplete();

        verify(repository).deleteById(KYC_VERIFICATION_ID);
    }
}