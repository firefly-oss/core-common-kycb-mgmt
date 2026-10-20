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


package io.fireflysoftware.core.kycb.models.entities.corporate.v1;

import io.fireflysoftware.core.kycb.models.entities.BaseEntity;
import io.fireflysoftware.core.kycb.interfaces.enums.corporate.v1.RelationshipTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Entity representing a corporate structure relationship.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("corporate_structure")
public class CorporateStructure extends BaseEntity {

    @Id
    @Column("corporate_structure_id")
    private UUID corporateStructureId;

    @Column("party_id")
    private UUID partyId;

    @Column("parent_entity_id")
    private UUID parentEntityId;

    @Column("ownership_percentage")
    private BigDecimal ownershipPercentage;

    @Column("relationship_type")
    private RelationshipTypeEnum relationshipType;

    @Column("control_notes")
    private String controlNotes;

    @Column("is_verified")
    private Boolean isVerified;

    @Column("verification_date")
    private LocalDateTime verificationDate;

    @Column("start_date")
    private LocalDateTime startDate;

    @Column("end_date")
    private LocalDateTime endDate;
}
