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
 * DTO for corporate document data.
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CorporateDocumentDTO extends BaseDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID corporateDocumentId;

    @FilterableId
    @NotNull(message = "Party ID is required")
    private UUID partyId;

    @NotBlank(message = "Document type is required")
    @Size(max = 50, message = "Document type must not exceed 50 characters")
    private String documentType;

    @NotBlank(message = "Document reference is required")
    @Size(max = 100, message = "Document reference must not exceed 100 characters")
    private String documentReference;

    @FilterableId
    @Size(max = 100, message = "Document system ID must not exceed 100 characters")
    private String documentSystemId;

    @Size(max = 200, message = "Notary name must not exceed 200 characters")
    private String notaryName;

    @Size(max = 200, message = "Notary location must not exceed 200 characters")
    private String notaryLocation;

    @Size(max = 200, message = "Commercial registry must not exceed 200 characters")
    private String commercialRegistry;

    @Size(max = 100, message = "Registry entry must not exceed 100 characters")
    private String registryEntry;

    @NotNull(message = "Issue date is required")
    @ValidDateTime
    private LocalDateTime issueDate;

    @ValidDateTime
    private LocalDateTime registryDate;

    @ValidDateTime
    private LocalDateTime expiryDate;

    @NotNull(message = "Verified status is required")
    private Boolean isVerified;

    @Size(max = 1000, message = "Verification notes must not exceed 1000 characters")
    private String verificationNotes;

    @ValidDateTime
    private LocalDateTime verificationDate;

    @Size(max = 100, message = "Verification agent must not exceed 100 characters")
    private String verificationAgent;
}
