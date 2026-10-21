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


package io.fireflysoftware.core.kycb.core.mappers.ownership.v1;

import io.fireflysoftware.core.kycb.interfaces.dtos.ownership.v1.UboDTO;
import io.fireflysoftware.core.kycb.interfaces.enums.ownership.v1.OwnershipTypeEnum;
import io.fireflysoftware.core.kycb.models.entities.ownership.v1.Ubo;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * Unit test for UboMapper covering the new email field introduced in V10.
 */
class UboMapperTest {

    private final UboMapper mapper = Mappers.getMapper(UboMapper.class);

    @Test
    void toDTO_mapsEmail() {
        Ubo entity = new Ubo();
        entity.setUboId(UUID.randomUUID());
        entity.setPartyId(UUID.randomUUID());
        entity.setNaturalPersonId(UUID.randomUUID());
        entity.setOwnershipPercentage(new BigDecimal("25.50"));
        entity.setOwnershipType(OwnershipTypeEnum.DIRECT);
        entity.setEmail("ubo@example.com");

        UboDTO dto = mapper.toDTO(entity);

        assertNotNull(dto);
        assertEquals("ubo@example.com", dto.getEmail());
    }

    @Test
    void toEntity_mapsEmail() {
        UboDTO dto = new UboDTO();
        dto.setPartyId(UUID.randomUUID());
        dto.setNaturalPersonId(UUID.randomUUID());
        dto.setOwnershipPercentage(new BigDecimal("10.00"));
        dto.setOwnershipType("INDIRECT");
        dto.setEmail("beneficial.owner@example.com");

        Ubo entity = mapper.toEntity(dto);

        assertNotNull(entity);
        assertEquals("beneficial.owner@example.com", entity.getEmail());
    }

    @Test
    void toDTO_handlesNullEmail() {
        Ubo entity = new Ubo();
        entity.setUboId(UUID.randomUUID());
        entity.setPartyId(UUID.randomUUID());
        entity.setNaturalPersonId(UUID.randomUUID());
        entity.setOwnershipType(OwnershipTypeEnum.DIRECT);
        // email left null on purpose

        UboDTO dto = mapper.toDTO(entity);

        assertNotNull(dto);
        assertNull(dto.getEmail());
    }
}
