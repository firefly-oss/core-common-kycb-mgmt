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


package io.fireflysoftware.core.kycb.core.mappers.power.v1;

import io.fireflysoftware.core.kycb.interfaces.dtos.power.v1.PowerOfAttorneyDTO;
import io.fireflysoftware.core.kycb.interfaces.enums.power.v1.PowerTypeEnum;
import io.fireflysoftware.core.kycb.models.entities.power.v1.PowerOfAttorney;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * Unit test for PowerOfAttorneyMapper covering the new email, signingAuthorized
 * and isPep fields introduced in V9.
 */
class PowerOfAttorneyMapperTest {

    private final PowerOfAttorneyMapper mapper = Mappers.getMapper(PowerOfAttorneyMapper.class);

    @Test
    void toDTO_mapsNewSignerFields() {
        PowerOfAttorney entity = new PowerOfAttorney();
        entity.setPowerOfAttorneyId(UUID.randomUUID());
        entity.setPartyId(UUID.randomUUID());
        entity.setAttorneyId(UUID.randomUUID());
        entity.setPowerType(PowerTypeEnum.LIMITED);
        entity.setEmail("signer@example.com");
        entity.setSigningAuthorized(Boolean.TRUE);
        entity.setIsPep(Boolean.FALSE);

        PowerOfAttorneyDTO dto = mapper.toDTO(entity);

        assertNotNull(dto);
        assertEquals("signer@example.com", dto.getEmail());
        assertEquals(Boolean.TRUE, dto.getSigningAuthorized());
        assertEquals(Boolean.FALSE, dto.getIsPep());
    }

    @Test
    void toEntity_mapsNewSignerFields() {
        PowerOfAttorneyDTO dto = new PowerOfAttorneyDTO();
        dto.setPartyId(UUID.randomUUID());
        dto.setAttorneyId(UUID.randomUUID());
        dto.setPowerType("LIMITED");
        dto.setEmail("attorney@example.com");
        dto.setSigningAuthorized(Boolean.TRUE);
        dto.setIsPep(Boolean.TRUE);

        PowerOfAttorney entity = mapper.toEntity(dto);

        assertNotNull(entity);
        assertEquals("attorney@example.com", entity.getEmail());
        assertEquals(Boolean.TRUE, entity.getSigningAuthorized());
        assertEquals(Boolean.TRUE, entity.getIsPep());
    }

    @Test
    void toDTO_handlesNullableNewFields() {
        PowerOfAttorney entity = new PowerOfAttorney();
        entity.setPowerOfAttorneyId(UUID.randomUUID());
        entity.setPartyId(UUID.randomUUID());
        entity.setAttorneyId(UUID.randomUUID());
        entity.setPowerType(PowerTypeEnum.LIMITED);
        // email / signingAuthorized / isPep left null on purpose

        PowerOfAttorneyDTO dto = mapper.toDTO(entity);

        assertNotNull(dto);
        assertNull(dto.getEmail());
        assertNull(dto.getSigningAuthorized());
        assertNull(dto.getIsPep());
    }
}
