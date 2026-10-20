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


package io.fireflysoftware.core.kycb.models.repositories.corporate.v1;

import io.fireflysoftware.core.kycb.models.entities.corporate.v1.CorporateStructure;
import io.fireflysoftware.core.kycb.interfaces.enums.corporate.v1.RelationshipTypeEnum;
import io.fireflysoftware.core.kycb.models.repositories.BaseRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Repository for corporate structure operations.
 */
@Repository
public interface CorporateStructureRepository extends BaseRepository<CorporateStructure, UUID> {

    /**
     * Find corporate structures by party ID (subsidiary).
     *
     * @param partyId The ID of the party (subsidiary)
     * @return A flux of corporate structures
     */
    Flux<CorporateStructure> findByPartyId(UUID partyId);

    /**
     * Find corporate structures by parent entity ID.
     *
     * @param parentEntityId The ID of the parent entity
     * @return A flux of corporate structures
     */
    Flux<CorporateStructure> findByParentEntityId(UUID parentEntityId);

    /**
     * Find corporate structures by relationship type.
     *
     * @param relationshipTypeEnum The type of relationship
     * @return A flux of corporate structures
     */
    Flux<CorporateStructure> findByRelationshipType(RelationshipTypeEnum relationshipTypeEnum);

    /**
     * Find corporate structures by verification status.
     *
     * @param isVerified The verification status
     * @return A flux of corporate structures
     */
    Flux<CorporateStructure> findByIsVerified(Boolean isVerified);

    /**
     * Find corporate structures with ownership percentage above a threshold.
     *
     * @param threshold The ownership percentage threshold
     * @return A flux of corporate structures
     */
    Flux<CorporateStructure> findByOwnershipPercentageGreaterThanEqual(BigDecimal threshold);

    /**
     * Find corporate structures by verification date range.
     *
     * @param startDate The start date
     * @param endDate The end date
     * @return A flux of corporate structures
     */
    Flux<CorporateStructure> findByVerificationDateBetween(LocalDateTime startDate, LocalDateTime endDate);

    /**
     * Find active corporate structures (not ended).
     *
     * @param currentDate The current date
     * @return A flux of corporate structures
     */
    Flux<CorporateStructure> findByEndDateAfterOrEndDateIsNull(LocalDateTime currentDate);

    /**
     * Find corporate structures by party ID and relationship type.
     *
     * @param partyId The ID of the party
     * @param relationshipTypeEnum The type of relationship
     * @return A flux of corporate structures
     */
    Flux<CorporateStructure> findByPartyIdAndRelationshipType(UUID partyId, RelationshipTypeEnum relationshipTypeEnum);

    /**
     * Find corporate structures by parent entity ID and relationship type.
     *
     * @param parentEntityId The ID of the parent entity
     * @param relationshipTypeEnum The type of relationship
     * @return A flux of corporate structures
     */
    Flux<CorporateStructure> findByParentEntityIdAndRelationshipType(UUID parentEntityId, RelationshipTypeEnum relationshipTypeEnum);
}
