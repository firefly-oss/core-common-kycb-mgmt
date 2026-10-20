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


package io.fireflysoftware.core.kycb.core.mappers.business.v1;

import io.fireflysoftware.core.kycb.interfaces.dtos.business.v1.BusinessProfileDTO;
import io.fireflysoftware.core.kycb.models.entities.business.v1.BusinessProfile;
import org.mapstruct.Mapper;

/**
 * Mapper for converting between BusinessProfile entity and BusinessProfileDTO.
 */
@Mapper(componentModel = "spring")
public interface BusinessProfileMapper {
    BusinessProfileDTO toDTO(BusinessProfile entity);
    BusinessProfile toEntity(BusinessProfileDTO dto);
}