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


package io.fireflysoftware.core.kycb.interfaces.dtos.ownership.v1;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Validation tests for UboDTO covering the new email field (BE-5d).
 */
class UboDTOTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testEmailFieldHasEmailAnnotation() throws NoSuchFieldException {
        var field = UboDTO.class.getDeclaredField("email");
        var emailAnnotation = field.getAnnotation(jakarta.validation.constraints.Email.class);
        assertNotNull(emailAnnotation, "email field should have @Email annotation");
    }

    @Test
    void testEmailIsOptional() throws NoSuchFieldException {
        var field = UboDTO.class.getDeclaredField("email");
        assertNull(field.getAnnotation(jakarta.validation.constraints.NotNull.class),
                "email must be optional (no @NotNull)");
    }

    @Test
    void testInvalidEmailIsRejected() {
        UboDTO dto = new UboDTO();
        dto.setEmail("not-an-email");

        Set<ConstraintViolation<UboDTO>> violations =
                validator.validateProperty(dto, "email");
        assertTrue(!violations.isEmpty(),
                "Invalid email should produce at least one violation");
    }

    @Test
    void testValidEmailIsAccepted() {
        UboDTO dto = new UboDTO();
        dto.setEmail("ubo@example.com");

        Set<ConstraintViolation<UboDTO>> violations =
                validator.validateProperty(dto, "email");
        assertTrue(violations.isEmpty(),
                "Valid email should not produce violations");
    }

    @Test
    void testNullEmailIsAccepted() {
        UboDTO dto = new UboDTO();
        dto.setEmail(null);

        Set<ConstraintViolation<UboDTO>> violations =
                validator.validateProperty(dto, "email");
        assertTrue(violations.isEmpty(),
                "Null email must be accepted (optional field)");
    }
}
