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


package io.fireflysoftware.core.kycb.models.repositories.sanctions.v1;

import io.fireflysoftware.core.kycb.models.entities.sanctions.v1.SanctionsQuestionnaire;
import io.fireflysoftware.core.kycb.models.repositories.BaseRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.UUID;

/**
 * Repository for sanctions questionnaire operations.
 */
@Repository
public interface SanctionsQuestionnaireRepository extends BaseRepository<SanctionsQuestionnaire, UUID> {

    /**
     * Find sanctions questionnaires by party ID.
     *
     * @param partyId The ID of the party
     * @return A flux of sanctions questionnaires
     */
    Flux<SanctionsQuestionnaire> findByPartyId(UUID partyId);

    /**
     * Find the latest sanctions questionnaire for a party.
     *
     * @param partyId The ID of the party
     * @return A mono of the latest sanctions questionnaire
     */
    Mono<SanctionsQuestionnaire> findFirstByPartyIdOrderByQuestionnaireDateDesc(UUID partyId);
}