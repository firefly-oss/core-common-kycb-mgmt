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


package io.fireflysoftware.core.kycb.core.mappers.corporate.v1;

import io.fireflysoftware.core.kycb.interfaces.dtos.corporate.v1.CorporateStructureDTO;
import io.fireflysoftware.core.kycb.models.entities.corporate.v1.CorporateStructure;
import org.mapstruct.Mapper;

/**
 * Mapper for converting between CorporateStructure entity and CorporateStructureDTO.
 */
@Mapper(componentModel = "spring")
public interface CorporateStructureMapper {
    CorporateStructureDTO toDTO(CorporateStructure entity);
    CorporateStructure toEntity(CorporateStructureDTO dto);
}