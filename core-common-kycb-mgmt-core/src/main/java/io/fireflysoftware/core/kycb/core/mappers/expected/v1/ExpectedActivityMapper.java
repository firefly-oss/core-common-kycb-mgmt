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


package io.fireflysoftware.core.kycb.core.mappers.expected.v1;

import io.fireflysoftware.core.kycb.interfaces.dtos.expected.v1.ExpectedActivityDTO;
import io.fireflysoftware.core.kycb.models.entities.expected.v1.ExpectedActivity;
import org.mapstruct.Mapper;

/**
 * Mapper for converting between ExpectedActivity entity and ExpectedActivityDTO.
 */
@Mapper(componentModel = "spring")
public interface ExpectedActivityMapper {
    ExpectedActivityDTO toDTO(ExpectedActivity entity);
    ExpectedActivity toEntity(ExpectedActivityDTO dto);
}