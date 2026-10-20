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


package io.fireflysoftware.core.kycb.core.mappers.aml.v1;

import io.fireflysoftware.core.kycb.interfaces.dtos.aml.v1.AmlMatchDTO;
import io.fireflysoftware.core.kycb.models.entities.aml.v1.AmlMatch;
import org.mapstruct.Mapper;

/**
 * Mapper for converting between AmlMatch entity and AmlMatchDTO.
 */
@Mapper(componentModel = "spring")
public interface AmlMatchMapper {
    AmlMatchDTO toDTO(AmlMatch entity);
    AmlMatch toEntity(AmlMatchDTO dto);
}