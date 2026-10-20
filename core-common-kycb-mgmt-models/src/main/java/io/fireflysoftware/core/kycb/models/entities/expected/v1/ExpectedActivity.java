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


package io.fireflysoftware.core.kycb.models.entities.expected.v1;

import io.fireflysoftware.core.kycb.models.entities.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Entity representing expected account activity for a party.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("expected_activity")
public class ExpectedActivity extends BaseEntity {

    @Id
    @Column("expected_activity_id")
    private UUID expectedActivityId;

    @Column("party_id")
    private UUID partyId;

    @Column("activity_type_code")
    private String activityTypeCode;

    @Column("expected_monthly_volume")
    private BigDecimal expectedMonthlyVolume;

    @Column("expected_annual_volume")
    private BigDecimal expectedAnnualVolume;

    @Column("expected_transaction_count")
    private Integer expectedTransactionCount;

    @Column("currency_iso_code")
    private String currencyIsoCode;

    @Column("transaction_freq_code")
    private String transactionFreqCode;

    @Column("anticipated_countries")
    private String anticipatedCountries;

    @Column("is_high_value")
    private Boolean isHighValue;

    @Column("high_value_details")
    private String highValueDetails;

    @Column("expected_counterparties")
    private String expectedCounterparties;

    @Column("cash_intensive")
    private Boolean cashIntensive;

    @Column("tax_haven_transactions")
    private Boolean taxHavenTransactions;

    @Column("declaration_model_code")
    private String declarationModelCode;

    @Column("declaration_details")
    private String declarationDetails;

    @Column("verification_notes")
    private String verificationNotes;
}