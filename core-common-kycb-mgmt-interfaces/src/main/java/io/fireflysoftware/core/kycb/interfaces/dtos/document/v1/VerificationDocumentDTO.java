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


package io.fireflysoftware.core.kycb.interfaces.dtos.document.v1;

import org.fireflyframework.annotations.ValidDateTime;
import io.fireflysoftware.core.kycb.interfaces.dtos.BaseDTO;
import org.fireflyframework.utils.annotations.FilterableId;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * DTO for verification document data.
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class VerificationDocumentDTO extends BaseDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID verificationDocumentId;

    @FilterableId
    @NotNull(message = "KYC verification ID is required")
    private UUID kycVerificationId;

    @FilterableId
    @NotNull(message = "Identity document ID is required")
    private UUID identityDocumentId;

    @NotBlank(message = "Document type is required")
    @Size(max = 50, message = "Document type must not exceed 50 characters")
    private String documentType;

    @NotBlank(message = "Verification purpose is required")
    @Size(max = 50, message = "Verification purpose must not exceed 50 characters")
    private String verificationPurpose;

    @NotBlank(message = "Document reference is required")
    @Size(max = 100, message = "Document reference must not exceed 100 characters")
    private String documentReference;

    @Size(max = 100, message = "Document system ID must not exceed 100 characters")
    private String documentSystemId;

    @NotNull(message = "Verified status is required")
    private Boolean isVerified;

    @Size(max = 1000, message = "Verification notes must not exceed 1000 characters")
    private String verificationNotes;

    @ValidDateTime
    private LocalDateTime expiryDate;
}
