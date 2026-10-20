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


package io.fireflysoftware.core.kycb.core.services.sanctions.v1;

import io.fireflysoftware.core.kycb.interfaces.dtos.sanctions.v1.SanctionsQuestionnaireDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.UUID;

/**
 * Service for managing sanctions questionnaires.
 */
public interface SanctionsQuestionnaireService {

    /**
     * Create a new sanctions questionnaire.
     *
     * @param sanctionsQuestionnaireDTO The sanctions questionnaire data
     * @return A mono of the created sanctions questionnaire
     */
    Mono<SanctionsQuestionnaireDTO> create(SanctionsQuestionnaireDTO sanctionsQuestionnaireDTO);

    /**
     * Find a sanctions questionnaire by ID.
     *
     * @param sanctionsQuestionnaireId The ID of the sanctions questionnaire
     * @return A mono of the found sanctions questionnaire
     */
    Mono<SanctionsQuestionnaireDTO> findById(UUID sanctionsQuestionnaireId);

    /**
     * Find sanctions questionnaires by party ID.
     *
     * @param partyId The ID of the party
     * @return A flux of sanctions questionnaires
     */
    Flux<SanctionsQuestionnaireDTO> findByPartyId(UUID partyId);

    /**
     * Find the latest sanctions questionnaire for a party.
     *
     * @param partyId The ID of the party
     * @return A mono of the latest sanctions questionnaire
     */
    Mono<SanctionsQuestionnaireDTO> findLatestByPartyId(UUID partyId);

    /**
     * Update a sanctions questionnaire.
     *
     * @param sanctionsQuestionnaireId The ID of the sanctions questionnaire to update
     * @param sanctionsQuestionnaireDTO The updated sanctions questionnaire data
     * @return A mono of the updated sanctions questionnaire
     */
    Mono<SanctionsQuestionnaireDTO> update(UUID sanctionsQuestionnaireId, SanctionsQuestionnaireDTO sanctionsQuestionnaireDTO);

    /**
     * Delete a sanctions questionnaire.
     *
     * @param sanctionsQuestionnaireId The ID of the sanctions questionnaire to delete
     * @return A mono of void
     */
    Mono<Void> delete(UUID sanctionsQuestionnaireId);
}