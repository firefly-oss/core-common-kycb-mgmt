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


package io.fireflysoftware.core.kycb.interfaces.enums.sanctions.v1;

/**
 * Enum representing the scope of the entity for which the sanctions questionnaire is answered.
 */
public enum EntitySanctionsQuestionnaireTypeEnum {
    /**
     * Only the legal entity itself.
     */
    LEGAL_ENTITY_ONLY,
    
    /**
     * The legal entity and all its subsidiaries held at 100%.
     */
    LEGAL_ENTITY_AND_WHOLLY_OWNED_SUBSIDIARIES,
    
    /**
     * The legal entity and all its subsidiaries held at 50% or more.
     */
    LEGAL_ENTITY_AND_MAJORITY_OWNED_SUBSIDIARIES,
    
    /**
     * The legal entity and a specific list of subsidiaries.
     */
    LEGAL_ENTITY_AND_SPECIFIC_SUBSIDIARIES
}