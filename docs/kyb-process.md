# KYB Process Documentation

This document provides a detailed walkthrough of the Know Your Business (KYB) process for legal entities using the Firefly KYC/B & AML Management microservice.

## Table of Contents

- [Overview](#overview)
- [Process Flow](#process-flow)
- [Step 1: Create a KYB Verification](#step-1-create-a-kyb-verification)
- [Step 2: Register Business Profile and Locations](#step-2-register-business-profile-and-locations)
- [Step 3: Complete Sanctions and Embargo Questionnaire](#step-3-complete-sanctions-and-embargo-questionnaire)
- [Step 4: Upload and Verify Corporate Documents](#step-4-upload-and-verify-corporate-documents)
- [Step 5: Define Corporate Structure](#step-5-define-corporate-structure)
- [Step 6: Register Ultimate Beneficial Owners (UBOs)](#step-6-register-ultimate-beneficial-owners-ubos)
- [Step 7: Register Directors and Key Personnel](#step-7-register-directors-and-key-personnel)
- [Step 8: Perform AML Screening](#step-8-perform-aml-screening)
- [Step 9: Conduct Risk Assessment](#step-9-conduct-risk-assessment)
- [Step 10: Apply Enhanced Due Diligence (if necessary)](#step-10-apply-enhanced-due-diligence-if-necessary)
- [Step 11: Complete the KYB Verification](#step-11-complete-the-kyb-verification)
- [Error Handling](#error-handling)
- [Best Practices](#best-practices)

## Overview

The Know Your Business (KYB) process is a mandatory procedure for financial institutions and regulated businesses to verify the identity and legitimacy of business entities they work with. This process helps prevent financial fraud, money laundering, terrorist financing, and ensures compliance with regulatory requirements.

The Firefly KYC/B & AML Management microservice provides a comprehensive solution for implementing KYB processes in compliance with regulatory requirements.

## Process Flow

The KYB process for legal entities follows these steps:

1. Create a KYB Verification
2. Register Business Profile and Locations
3. Complete Sanctions and Embargo Questionnaire
4. Upload and Verify Corporate Documents
5. Define Corporate Structure
6. Register Ultimate Beneficial Owners (UBOs)
7. Register Directors and Key Personnel
8. Perform AML Screening
9. Conduct Risk Assessment
10. Apply Enhanced Due Diligence (if necessary)
11. Complete the KYB Verification

```mermaid
flowchart TD
    A[Start KYB Process] --> B[Create KYB Verification]
    B --> C[Register Business Profile and Locations]
    C --> D[Complete Sanctions and Embargo Questionnaire]
    D --> E[Upload and Verify Corporate Documents]
    E --> F[Define Corporate Structure]
    F --> G[Register Ultimate Beneficial Owners]
    G --> H[Register Directors and Key Personnel]
    H --> I[Perform AML Screening]
    I --> J[Conduct Risk Assessment]
    J --> K{High Risk?}
    K -->|Yes| L[Apply Enhanced Due Diligence]
    K -->|No| M[Complete KYB Verification]
    L --> M
    M --> N[End KYB Process]
```

Each step is detailed below with code examples.

## Step 1: Create a KYB Verification

The first step is to create a KYB verification record for the legal entity. This establishes the verification process and tracks its progress.

### API Endpoint

```
POST /api/v1/identity/parties/{partyId}/kyb
```

### Request Example

```java
// Create a new KYB verification
KybVerificationDTO verification = new KybVerificationDTO();
verification.setPartyId(123L);
verification.setVerificationType(VerificationTypeEnum.BUSINESS_IDENTITY);
verification.setStatus(VerificationStatusEnum.PENDING);
verification.setRiskLevel(RiskLevelEnum.MEDIUM);
verification.setVerificationMethod(VerificationMethodEnum.DOCUMENT);
verification.setBusinessType(BusinessTypeEnum.CORPORATION);
verification.setCountryOfIncorporation("US");

webClient.post()
    .uri("/api/v1/identity/parties/123/kyb")
    .bodyValue(verification)
    .retrieve()
    .bodyToMono(KybVerificationDTO.class)
    .subscribe(response -> {
        System.out.println("Verification created with ID: " + response.getKybVerificationId());
    });
```

### Response Example

```json
{
  "kybVerificationId": 456,
  "partyId": 123,
  "verificationType": "BUSINESS_IDENTITY",
  "status": "PENDING",
  "riskLevel": "MEDIUM",
  "verificationMethod": "DOCUMENT",
  "businessType": "CORPORATION",
  "countryOfIncorporation": "US",
  "createdDate": "2023-06-15T10:30:45Z",
  "lastModifiedDate": "2023-06-15T10:30:45Z"
}
```

## Step 2: Register Business Profile and Locations

After creating the KYB verification, the next step is to register the business profile and its locations.

### API Endpoint for Business Profile

```
POST /api/v1/identity/parties/{partyId}/business-profile
```

### Request Example for Business Profile

```java
// Register business profile
BusinessProfileDTO profile = new BusinessProfileDTO();
profile.setPartyId(123L);
profile.setLegalName("Acme Corporation");
profile.setTradingName("Acme Corp");
profile.setRegistrationNumber("US12345678");
profile.setTaxIdentificationNumber("987654321");
profile.setDateOfIncorporation(LocalDate.of(2010, 5, 15));
profile.setLegalForm("Corporation");
profile.setWebsite("https://www.acmecorp.example.com");
profile.setIndustryCode("SIC-7371");
profile.setIndustryName("Computer Programming Services");

webClient.post()
    .uri("/api/v1/identity/parties/123/business-profile")
    .bodyValue(profile)
    .retrieve()
    .bodyToMono(BusinessProfileDTO.class)
    .subscribe(response -> {
        System.out.println("Business profile registered with ID: " + response.getBusinessProfileId());
    });
```

### API Endpoint for Business Locations

```
POST /api/v1/corporate/parties/{partyId}/locations
```

### Request Example for Business Locations

```java
// Register business location
BusinessLocationDTO location = new BusinessLocationDTO();
location.setPartyId(123L);
location.setLocationType(LocationTypeEnum.HEADQUARTERS);
location.setAddressLine1("123 Main Street");
location.setAddressLine2("Suite 500");
location.setCity("San Francisco");
location.setState("CA");
location.setPostalCode("94105");
location.setCountry("US");
location.setPhoneNumber("+14155551234");
location.setEmailAddress("contact@acmecorp.example.com");
location.setIsPrimary(true);

webClient.post()
    .uri("/api/v1/corporate/parties/123/locations")
    .bodyValue(location)
    .retrieve()
    .bodyToMono(BusinessLocationDTO.class)
    .subscribe(response -> {
        System.out.println("Business location registered with ID: " + response.getBusinessLocationId());
    });
```

## Step 3: Complete Sanctions and Embargo Questionnaire

The sanctions and embargo questionnaire helps assess the business's exposure to sanctioned countries, individuals, or activities.

### API Endpoint

```
POST /api/v1/identity/parties/{partyId}/sanctions-questionnaire
```

### Request Example

```java
// Complete sanctions questionnaire
SanctionsQuestionnaireDTO questionnaire = new SanctionsQuestionnaireDTO();
questionnaire.setPartyId(123L);
questionnaire.setEntitySanctionsQuestionnaire(EntitySanctionsQuestionnaireTypeEnum.LEGAL_ENTITY_ONLY);
questionnaire.setActivityOutsideEu(false);
questionnaire.setEconomicSanctions(false);
questionnaire.setResidentCountriesSanctions(false);
questionnaire.setInvolvedSanctions(false);
questionnaire.setQuestionnaireDate(LocalDateTime.now());

webClient.post()
    .uri("/api/v1/identity/parties/123/sanctions-questionnaire")
    .bodyValue(questionnaire)
    .retrieve()
    .bodyToMono(SanctionsQuestionnaireDTO.class)
    .subscribe(response -> {
        System.out.println("Sanctions questionnaire completed with ID: " + response.getSanctionsQuestionnaireId());
    });
```

### Response Example

```json
{
  "sanctionsQuestionnaireId": 789,
  "partyId": 123,
  "entitySanctionsQuestionnaire": "LEGAL_ENTITY_ONLY",
  "activityOutsideEu": false,
  "economicSanctions": false,
  "residentCountriesSanctions": false,
  "involvedSanctions": false,
  "questionnaireDate": "2023-06-15T11:15:30Z",
  "createdDate": "2023-06-15T11:15:30Z",
  "lastModifiedDate": "2023-06-15T11:15:30Z"
}
```

## Step 4: Upload and Verify Corporate Documents

Corporate documents need to be uploaded and verified to confirm the business's legal status and operations.

### API Endpoint

```
POST /api/v1/documents/corporate
```

### Request Example

```java
// Upload a corporate document
CorporateDocumentDTO document = new CorporateDocumentDTO();
document.setPartyId(UUID.fromString("550e8400-e29b-41d4-a716-446655440100"));
document.setDocumentType(CorporateDocumentTypeEnum.CERTIFICATE_OF_INCORPORATION);
document.setDocumentNumber("CORP-2023-12345");
document.setIssueDate(LocalDate.now().minusYears(5));
document.setIssuingAuthority("Delaware Secretary of State");
document.setDocumentUrl("https://document-storage.example.com/docs/certificate-123.pdf");

webClient.post()
    .uri("/api/v1/documents/corporate")
    .bodyValue(document)
    .retrieve()
    .bodyToMono(CorporateDocumentDTO.class)
    .subscribe(response -> {
        System.out.println("Document uploaded with ID: " + response.getCorporateDocumentId());
    });
```

### Verify Document

After uploading the document, it needs to be verified:

```java
// Verify document authenticity
CorporateDocumentDTO verifyDocument = new CorporateDocumentDTO();
verifyDocument.setIsVerified(true);
verifyDocument.setVerificationDate(LocalDateTime.now());
verifyDocument.setVerificationNotes("Document verified against official registry");
verifyDocument.setVerificationAgent("compliance-officer-2");

webClient.patch()
    .uri("/api/v1/documents/corporate/456")
    .bodyValue(verifyDocument)
    .retrieve()
    .bodyToMono(CorporateDocumentDTO.class)
    .subscribe(response -> {
        System.out.println("Document verified: " + response.getIsVerified());
    });
```

## Step 5: Define Corporate Structure

The corporate structure defines the relationships between the business entity and its parent, subsidiaries, or affiliates. An edge records a party and a different direct parent. An ownership cycle is rejected. An ownership percentage, when set, is from 0 to 100.

### API Endpoint

```
POST /api/v1/corporate/parties/{partyId}/structure
```

### Request Example

```java
// Define corporate structure
CorporateStructureDTO structure = new CorporateStructureDTO();
structure.setPartyId(123L);
structure.setStructureType(StructureTypeEnum.SUBSIDIARY);
structure.setParentEntityId(789L);
structure.setParentEntityName("Global Holdings Inc.");
structure.setOwnershipPercentage(new BigDecimal("100.00"));
structure.setRelationshipStartDate(LocalDate.of(2015, 3, 10));
structure.setIsVerified(false);

webClient.post()
    .uri("/api/v1/corporate/parties/123/structure")
    .bodyValue(structure)
    .retrieve()
    .bodyToMono(CorporateStructureDTO.class)
    .subscribe(response -> {
        System.out.println("Corporate structure defined with ID: " + response.getCorporateStructureId());
    });
```

## Step 6: Register Ultimate Beneficial Owners (UBOs)

Ultimate Beneficial Owners (UBOs) are the natural persons who ultimately own or control a legal entity.

### API Endpoint

```
POST /api/v1/corporate/parties/{partyId}/ubos
```

### Request Example

```java
// Register a UBO for a corporate entity
UboDTO ubo = new UboDTO();
ubo.setPartyId(123L);
ubo.setIndividualPartyId(456L);
ubo.setOwnershipPercentage(new BigDecimal("25.5"));
ubo.setOwnershipType(OwnershipTypeEnum.DIRECT);
ubo.setIsControlling(true);
ubo.setStartDate(LocalDateTime.now());

webClient.post()
    .uri("/api/v1/corporate/parties/123/ubos")
    .bodyValue(ubo)
    .retrieve()
    .bodyToMono(UboDTO.class)
    .subscribe(response -> {
        System.out.println("UBO registered with ID: " + response.getUboId());
    });
```

### Verify UBO Information

UBO information needs to be verified:

```java
// Verify UBO information
UboDTO verifyUbo = new UboDTO();
verifyUbo.setIsVerified(true);
verifyUbo.setVerificationDate(LocalDateTime.now());
verifyUbo.setVerificationMethod(VerificationMethodEnum.DOCUMENT);
verifyUbo.setBeneficialOwnershipDocument("https://document-storage.example.com/docs/ownership-proof-123.pdf");

webClient.patch()
    .uri("/api/v1/corporate/parties/123/ubos/789")
    .bodyValue(verifyUbo)
    .retrieve()
    .bodyToMono(UboDTO.class)
    .subscribe(response -> {
        System.out.println("UBO verified: " + response.getIsVerified());
    });
```

## Step 7: Register Directors and Key Personnel

Directors and key personnel of the business entity need to be registered and verified.

### API Endpoint

```
POST /api/v1/corporate/parties/{partyId}/key-personnel
```

### Request Example

```java
// Register a director
KeyPersonnelDTO director = new KeyPersonnelDTO();
director.setPartyId(123L);
director.setIndividualPartyId(567L);
director.setRole(PersonnelRoleEnum.DIRECTOR);
director.setTitle("Chief Executive Officer");
director.setStartDate(LocalDate.of(2018, 6, 1));
director.setIsExecutive(true);
director.setHasSigningAuthority(true);

webClient.post()
    .uri("/api/v1/corporate/parties/123/key-personnel")
    .bodyValue(director)
    .retrieve()
    .bodyToMono(KeyPersonnelDTO.class)
    .subscribe(response -> {
        System.out.println("Director registered with ID: " + response.getKeyPersonnelId());
    });
```

### Verify Key Personnel Information

Key personnel information needs to be verified:

```java
// Verify key personnel information
KeyPersonnelDTO verifyPersonnel = new KeyPersonnelDTO();
verifyPersonnel.setIsVerified(true);
verifyPersonnel.setVerificationDate(LocalDateTime.now());
verifyPersonnel.setVerificationMethod(VerificationMethodEnum.DOCUMENT);
verifyPersonnel.setVerificationDocument("https://document-storage.example.com/docs/director-proof-123.pdf");

webClient.patch()
    .uri("/api/v1/corporate/parties/123/key-personnel/456")
    .bodyValue(verifyPersonnel)
    .retrieve()
    .bodyToMono(KeyPersonnelDTO.class)
    .subscribe(response -> {
        System.out.println("Key personnel verified: " + response.getIsVerified());
    });
```

## Step 8: Perform AML Screening

The business entity, its UBOs, and key personnel should be screened against Anti-Money Laundering (AML) watchlists.

### API Endpoint

```
POST /api/v1/compliance/parties/{partyId}/aml-screenings
```

### Request Example

```java
// Perform AML screening for the business entity
AmlScreeningDTO screening = new AmlScreeningDTO();
screening.setPartyId(UUID.fromString("550e8400-e29b-41d4-a716-446655440100"));
screening.setScreeningType(ScreeningTypeEnum.ENTITY_SANCTIONS);
screening.setScreeningSource("OFAC, EU Sanctions List, UN Sanctions List");
screening.setScreeningDate(LocalDateTime.now());

webClient.post()
    .uri("/api/v1/compliance/parties/123/aml-screenings")
    .bodyValue(screening)
    .retrieve()
    .bodyToMono(AmlScreeningDTO.class)
    .subscribe(response -> {
        System.out.println("Screening completed with ID: " + response.getAmlScreeningId());
        System.out.println("Status: " + response.getScreeningStatus());
    });
```

### Handle Potential Matches

If matches are found, they need to be reviewed and resolved:

```java
// Get all matches for a screening
webClient.get()
    .uri("/api/v1/compliance/parties/123/aml-screenings/321/matches")
    .retrieve()
    .bodyToMono(new ParameterizedTypeReference<PaginationResponse<AmlMatchDTO>>() {})
    .subscribe(response -> {
        response.getContent().forEach(match -> {
            System.out.println("Match ID: " + match.getAmlMatchId());
            System.out.println("Match type: " + match.getMatchType());
            System.out.println("Match score: " + match.getMatchScore());

            // Resolve a match
            AmlMatchDTO updateMatch = new AmlMatchDTO();
            updateMatch.setAmlMatchId(match.getAmlMatchId());
            updateMatch.setResolutionStatus(ResolutionStatusEnum.FALSE_POSITIVE);
            updateMatch.setResolutionNotes("Verified not a match due to different registration details");
            updateMatch.setResolvedBy("compliance-officer-1");

            webClient.patch()
                .uri("/api/v1/compliance/parties/123/aml-screenings/321/matches/" + match.getAmlMatchId())
                .bodyValue(updateMatch)
                .retrieve()
                .bodyToMono(AmlMatchDTO.class)
                .subscribe(updatedMatch -> {
                    System.out.println("Match resolved: " + updatedMatch.getResolutionStatus());
                });
        });
    });
```

## Step 9: Conduct Risk Assessment

After AML screening, a risk assessment should be performed to determine the risk level of the business entity.

### API Endpoint

```
POST /api/v1/compliance/parties/{partyId}/risk-assessments
```

### Request Example

```java
// Perform risk assessment
RiskAssessmentDTO assessment = new RiskAssessmentDTO();
assessment.setPartyId(123L);
assessment.setRiskFactors(Arrays.asList("COUNTRY_RISK", "INDUSTRY_RISK", "BUSINESS_MODEL_RISK", "TRANSACTION_VOLUME"));
assessment.setAssessmentType(AssessmentTypeEnum.INITIAL);
assessment.setAssessmentDate(LocalDateTime.now());

webClient.post()
    .uri("/api/v1/compliance/parties/123/risk-assessments")
    .bodyValue(assessment)
    .retrieve()
    .bodyToMono(RiskAssessmentDTO.class)
    .subscribe(response -> {
        System.out.println("Risk assessment ID: " + response.getRiskAssessmentId());
        System.out.println("Risk level: " + response.getRiskLevel());
        System.out.println("Risk score: " + response.getRiskScore());
    });
```

## Step 10: Apply Enhanced Due Diligence (if necessary)

If the risk assessment identifies the business entity as high-risk, Enhanced Due Diligence (EDD) should be applied.

### API Endpoint

```
POST /api/v1/identity/parties/{partyId}/kyb/{kybVerificationId}/edd
```

### Request Example

```java
// If high risk, trigger Enhanced Due Diligence
if (riskLevel == RiskLevelEnum.HIGH) {
    EnhancedDueDiligenceDTO edd = new EnhancedDueDiligenceDTO();
    edd.setKybVerificationId(456L);
    edd.setEddType(EddTypeEnum.HIGH_RISK_BUSINESS);
    edd.setEddStatus(EddStatusEnum.PENDING);
    edd.setAssignedTo("senior-compliance-officer");

    webClient.post()
        .uri("/api/v1/identity/parties/123/kyb/456/edd")
        .bodyValue(edd)
        .retrieve()
        .bodyToMono(EnhancedDueDiligenceDTO.class)
        .subscribe(eddResponse -> {
            System.out.println("EDD process initiated: " + eddResponse.getEddId());
        });
}
```

### Complete EDD Process

Once the EDD process is completed:

```java
// Complete EDD process
EnhancedDueDiligenceDTO completeEdd = new EnhancedDueDiligenceDTO();
completeEdd.setEddStatus(EddStatusEnum.COMPLETED);
completeEdd.setCompletionDate(LocalDateTime.now());
completeEdd.setCompletedBy("senior-compliance-officer");
completeEdd.setNotes("Additional verification completed. Source of funds verified through financial statements and bank references.");

webClient.patch()
    .uri("/api/v1/identity/parties/123/kyb/456/edd/987")
    .bodyValue(completeEdd)
    .retrieve()
    .bodyToMono(EnhancedDueDiligenceDTO.class)
    .subscribe(response -> {
        System.out.println("EDD completed: " + response.getEddStatus());
    });
```

## Step 11: Complete the KYB Verification

After all the previous steps are completed, the KYB verification can be finalized.

### API Endpoint

```
PATCH /api/v1/identity/parties/{partyId}/kyb/{kybVerificationId}
```

### Request Example

```java
// Update a KYB verification status
KybVerificationDTO updateDto = new KybVerificationDTO();
updateDto.setStatus(VerificationStatusEnum.COMPLETED);
updateDto.setVerificationDate(LocalDateTime.now());
updateDto.setVerifiedBy("compliance-officer-1");
updateDto.setNotes("All corporate documents verified successfully. AML screening completed with no matches. Risk assessment completed. UBOs and key personnel verified.");

webClient.patch()
    .uri("/api/v1/identity/parties/123/kyb/456")
    .bodyValue(updateDto)
    .retrieve()
    .bodyToMono(KybVerificationDTO.class)
    .subscribe(response -> {
        System.out.println("Verification updated: " + response.getStatus());
    });
```

### Response Example

```json
{
  "kybVerificationId": 456,
  "partyId": 123,
  "verificationType": "BUSINESS_IDENTITY",
  "status": "COMPLETED",
  "riskLevel": "MEDIUM",
  "verificationMethod": "DOCUMENT",
  "businessType": "CORPORATION",
  "countryOfIncorporation": "US",
  "verificationDate": "2023-06-15T14:30:00Z",
  "verifiedBy": "compliance-officer-1",
  "notes": "All corporate documents verified successfully. AML screening completed with no matches. Risk assessment completed. UBOs and key personnel verified.",
  "createdDate": "2023-06-15T10:30:45Z",
  "lastModifiedDate": "2023-06-15T14:30:00Z"
}
```

## Error Handling

During the KYB process, various errors may occur. Here are some common errors and how to handle them:

### Document Verification Failures

If a corporate document fails verification:

```java
// Handle document verification failure
CorporateDocumentDTO failedDoc = new CorporateDocumentDTO();
failedDoc.setIsVerified(false);
failedDoc.setVerificationDate(LocalDateTime.now());
failedDoc.setVerificationAgent("compliance-officer-2");
failedDoc.setVerificationNotes("Document appears altered. Request a new document.");

webClient.patch()
    .uri("/api/v1/documents/corporate/456")
    .bodyValue(failedDoc)
    .retrieve()
    .bodyToMono(CorporateDocumentDTO.class)
    .subscribe(response -> {
        System.out.println("Document verification failed: " + response.getVerificationNotes());

        // Notify the business
        // Code to send notification...
    });
```

### AML Screening Matches

If AML screening finds true matches:

```java
// Handle true AML match
AmlMatchDTO trueMatch = new AmlMatchDTO();
trueMatch.setAmlMatchId(match.getAmlMatchId());
trueMatch.setResolutionStatus(ResolutionStatusEnum.TRUE_MATCH);
trueMatch.setResolutionNotes("Confirmed match with OFAC sanctions list");
trueMatch.setResolvedBy("compliance-officer-1");

webClient.patch()
    .uri("/api/v1/compliance/parties/123/aml-screenings/321/matches/" + match.getAmlMatchId())
    .bodyValue(trueMatch)
    .retrieve()
    .bodyToMono(AmlMatchDTO.class)
    .subscribe(updatedMatch -> {
        System.out.println("True match confirmed: " + updatedMatch.getResolutionStatus());

        // Create a compliance case
        ComplianceCaseDTO complianceCase = new ComplianceCaseDTO();
        complianceCase.setPartyId(123L);
        complianceCase.setCaseType(CaseTypeEnum.SANCTIONS_MATCH);
        complianceCase.setCasePriority(CasePriorityEnum.HIGH);
        complianceCase.setCaseStatus(CaseStatusEnum.OPEN);
        complianceCase.setAssignedTo("compliance-team-lead");
        complianceCase.setDescription("True match found in OFAC sanctions list for business entity");

        webClient.post()
            .uri("/api/v1/compliance/cases")
            .bodyValue(complianceCase)
            .retrieve()
            .bodyToMono(ComplianceCaseDTO.class)
            .subscribe(caseResponse -> {
                System.out.println("Compliance case created: " + caseResponse.getComplianceCaseId());
            });
    });
```

### UBO Verification Issues

If UBO information cannot be verified:

```java
// Handle UBO verification issues
UboDTO unverifiedUbo = new UboDTO();
unverifiedUbo.setIsVerified(false);
unverifiedUbo.setVerificationDate(LocalDateTime.now());
unverifiedUbo.setVerificationNotes("Unable to verify ownership percentage. Additional documentation required.");

webClient.patch()
    .uri("/api/v1/corporate/parties/550e8400-e29b-41d4-a716-446655440100/ubos/550e8400-e29b-41d4-a716-446655440000")
    .bodyValue(unverifiedUbo)
    .retrieve()
    .bodyToMono(UboDTO.class)
    .subscribe(response -> {
        System.out.println("UBO verification failed: " + response.getVerificationNotes());

        // Request additional documentation
        DocumentRequestDTO request = new DocumentRequestDTO();
        request.setPartyId(UUID.fromString("550e8400-e29b-41d4-a716-446655440100"));
        request.setDocumentType(DocumentTypeEnum.OWNERSHIP_PROOF);
        request.setRequestNotes("Please provide official documentation confirming ownership percentage.");
        request.setRequestedBy("compliance-officer-1");
        request.setDueDate(LocalDate.now().plusDays(14));

        webClient.post()
            .uri("/api/v1/documents/requests")
            .bodyValue(request)
            .retrieve()
            .bodyToMono(DocumentRequestDTO.class)
            .subscribe(requestResponse -> {
                System.out.println("Document request created: " + requestResponse.getDocumentRequestId());
            });
    });
```

## Best Practices

1. **Document Everything**: Maintain detailed records of all verification steps, decisions, and the reasoning behind them.

2. **Four-Eyes Principle**: For high-risk businesses, implement a review process where at least two compliance officers verify the information.

3. **Regular Updates**: Re-verify business information periodically, especially for high-risk businesses.

4. **Risk-Based Approach**: Apply more stringent verification for high-risk businesses and less stringent for low-risk ones.

5. **Automation**: Automate routine checks while maintaining human oversight for complex cases.

6. **Continuous Monitoring**: Implement ongoing monitoring for changes in business risk profiles, ownership structures, and key personnel.

7. **Secure Storage**: Ensure all business data and documents are stored securely and in compliance with data protection regulations.

8. **Audit Trail**: Maintain a comprehensive audit trail of all actions taken during the KYB process.

9. **Training**: Ensure all staff involved in the KYB process are properly trained and updated on the latest regulations.

10. **Feedback Loop**: Continuously improve the KYB process based on feedback and lessons learned.

11. **Cross-Reference Verification**: Verify information across multiple sources to ensure consistency and accuracy.

12. **Industry-Specific Checks**: Implement additional checks for businesses in high-risk industries.

13. **Global Compliance**: Ensure compliance with regulations in all jurisdictions where the business operates.

14. **Technology Integration**: Integrate with external data sources and registries for more efficient verification.

15. **Escalation Procedures**: Establish clear procedures for escalating complex or high-risk cases to senior compliance staff.
