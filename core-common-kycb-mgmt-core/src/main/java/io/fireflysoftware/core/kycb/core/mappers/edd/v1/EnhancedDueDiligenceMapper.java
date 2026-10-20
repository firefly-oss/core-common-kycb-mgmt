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


package io.fireflysoftware.core.kycb.core.mappers.edd.v1;

import io.fireflysoftware.core.kycb.interfaces.dtos.edd.v1.EnhancedDueDiligenceDTO;
import io.fireflysoftware.core.kycb.models.entities.edd.v1.EnhancedDueDiligence;
import org.mapstruct.Mapper;

/**
 * Mapper for converting between EnhancedDueDiligence entity and EnhancedDueDiligenceDTO.
 */
@Mapper(componentModel = "spring")
public interface EnhancedDueDiligenceMapper {
    EnhancedDueDiligenceDTO toDTO(EnhancedDueDiligence entity);
    EnhancedDueDiligence toEntity(EnhancedDueDiligenceDTO dto);
}