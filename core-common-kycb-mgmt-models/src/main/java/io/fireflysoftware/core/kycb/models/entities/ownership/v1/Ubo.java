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


package io.fireflysoftware.core.kycb.models.entities.ownership.v1;

import io.fireflysoftware.core.kycb.models.entities.BaseEntity;
import io.fireflysoftware.core.kycb.interfaces.enums.ownership.v1.OwnershipTypeEnum;
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
 * Entity representing an Ultimate Beneficial Owner.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("ubo")
public class Ubo extends BaseEntity {

    @Id
    @Column("ubo_id")
    private UUID uboId;

    @Column("party_id")
    private UUID partyId;

    @Column("natural_person_id")
    private UUID naturalPersonId;

    @Column("ownership_percentage")
    private BigDecimal ownershipPercentage;

    @Column("ownership_type")
    private OwnershipTypeEnum ownershipType;

    @Column("control_structure")
    private String controlStructure;

    @Column("is_verified")
    private Boolean isVerified;

    @Column("verification_method")
    private String verificationMethod;

    @Column("titularidad_real_document")
    private String titularidadRealDocument;

    @Column("verification_date")
    private LocalDateTime verificationDate;

    @Column("start_date")
    private LocalDateTime startDate;

    @Column("end_date")
    private LocalDateTime endDate;

    @Column("email")
    private String email;
}
