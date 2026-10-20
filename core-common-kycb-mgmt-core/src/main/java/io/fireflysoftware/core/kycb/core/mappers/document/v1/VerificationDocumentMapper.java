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


package io.fireflysoftware.core.kycb.core.mappers.document.v1;

import io.fireflysoftware.core.kycb.interfaces.dtos.document.v1.VerificationDocumentDTO;
import io.fireflysoftware.core.kycb.models.entities.document.v1.VerificationDocument;
import org.mapstruct.Mapper;

/**
 * Mapper for converting between VerificationDocument entity and VerificationDocumentDTO.
 */
@Mapper(componentModel = "spring")
public interface VerificationDocumentMapper {
    VerificationDocumentDTO toDTO(VerificationDocument entity);
    VerificationDocument toEntity(VerificationDocumentDTO dto);
}