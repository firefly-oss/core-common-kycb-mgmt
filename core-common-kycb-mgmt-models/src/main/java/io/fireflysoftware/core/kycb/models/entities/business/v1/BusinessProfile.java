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


package io.fireflysoftware.core.kycb.models.entities.business.v1;

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
 * Entity representing a business profile snapshot.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("business_profile")
public class BusinessProfile extends BaseEntity {

    @Id
    @Column("business_profile_id")
    private UUID businessProfileId;

    @Column("party_id")
    private UUID partyId;

    @Column("legal_form_code")
    private String legalFormCode;

    @Column("business_description")
    private String businessDescription;

    @Column("website_url")
    private String websiteUrl;

    @Column("incorporation_year")
    private Integer incorporationYear;

    @Column("employee_count")
    private Integer employeeCount;

    @Column("employee_range_code")
    private String employeeRangeCode;

    @Column("annual_revenue")
    private BigDecimal annualRevenue;

    @Column("currency_iso_code")
    private String currencyIsoCode;

    @Column("revenue_range_code")
    private String revenueRangeCode;

    @Column("stock_exchange")
    private String stockExchange;

    @Column("stock_symbol")
    private String stockSymbol;

    @Column("is_regulated")
    private Boolean isRegulated;

    @Column("regulatory_authority")
    private String regulatoryAuthority;

    @Column("company_status_code")
    private String companyStatusCode;

    @Column("company_size_code")
    private String companySizeCode;

    @Column("is_public_entity")
    private Boolean isPublicEntity;

    @Column("registration_number")
    private String registrationNumber;
}
