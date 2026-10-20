# KYCB Services Test Suite

This directory contains a comprehensive test suite for all services in the `io.fireflysoftware.core.kycb.core.services` package. The test suite is designed to ensure that all service implementations function correctly and maintain their expected behavior over time.

## Test Structure

The test suite follows a structure that mirrors the service package structure:

```
io.fireflysoftware.core.kycb.core.services
├── aml
│   └── v1
│       ├── AmlMatchServiceImplTest.java
│       └── AmlScreeningServiceImplTest.java
├── business
│   └── v1
│       └── BusinessProfileServiceImplTest.java
├── compliance
│   └── v1
│       ├── ComplianceActionServiceImplTest.java
│       └── ComplianceCaseServiceImplTest.java
├── corporate
│   └── v1
│       └── CorporateStructureServiceImplTest.java
├── document
│   └── v1
│       ├── CorporateDocumentServiceImplTest.java
│       └── VerificationDocumentServiceImplTest.java
├── economic
│   └── v1
│       └── EconomicActivityServiceImplTest.java
├── edd
│   └── v1
│       └── EnhancedDueDiligenceServiceImplTest.java
├── expected
│   └── v1
│       └── ExpectedActivityServiceImplTest.java
├── industry
│   └── v1
│       └── IndustryRiskServiceImplTest.java
├── kyb
│   └── v1
│       └── KybVerificationServiceImplTest.java
├── kyc
│   └── v1
│       └── KycVerificationServiceImplTest.java
├── location
│   └── v1
│       └── BusinessLocationServiceImplTest.java
├── ownership
│   └── v1
│       └── UboServiceImplTest.java
├── power
│   └── v1
│       └── PowerOfAttorneyServiceImplTest.java
├── regulatory
│   └── v1
│       └── RegulatoryReportingServiceImplTest.java
├── risk
│   └── v1
│       └── RiskAssessmentServiceImplTest.java
└── source
    └── v1
        └── SourceOfFundsServiceImplTest.java
```

Each service implementation has a corresponding test class that verifies its functionality.

## Testing Approach

The tests use the following frameworks and libraries:

- JUnit 5: For test execution and assertions
- Mockito: For mocking dependencies
- Reactor Test: For testing reactive streams

The tests follow a consistent pattern:

1. **Setup**: Initialize test data and mock dependencies
2. **Arrange**: Configure mock behavior
3. **Act**: Call the service method being tested
4. **Assert**: Verify the expected behavior using StepVerifier and Mockito verifications

## Test Coverage

The test suite covers the following aspects of each service:

- CRUD operations (Create, Read, Update, Delete)
- Error handling
- Business logic specific to each service
- Data validation and transformation

Each test class includes tests for:

1. **Create**: Testing the creation of new entities
2. **Read**: Testing the retrieval of entities by ID
3. **Update**: Testing the update of existing entities
4. **Delete**: Testing the deletion of entities
5. **Find All**: Testing the retrieval of entities with filtering and pagination
6. **Error Scenarios**: Testing how the service handles error conditions

## Running the Tests

To run the tests, use the following Maven command:

```bash
mvn test
```

To run tests for a specific service:

```bash
mvn test -Dtest=*ServiceImplTest
```

To run a specific test class:

```bash
mvn test -Dtest=UboServiceImplTest
```

To run a specific test method:

```bash
mvn test -Dtest=UboServiceImplTest#testCreate
```

## Test Examples

### Basic CRUD Test

```java
@Test
void testCreate() {
    // Arrange
    when(mapper.toEntity(any(EntityDTO.class))).thenReturn(entity);
    when(repository.save(any(Entity.class))).thenReturn(Mono.just(entity));
    when(mapper.toDTO(any(Entity.class))).thenReturn(entityDTO);

    // Act & Assert
    StepVerifier.create(service.create(entityDTO))
            .expectNext(entityDTO)
            .verifyComplete();

    verify(mapper).toEntity(entityDTO);
    verify(repository).save(entity);
    verify(mapper).toDTO(entity);
}
```

### Error Handling Test

```java
@Test
void testGetByIdNotFound() {
    // Arrange
    when(repository.findById(ENTITY_ID)).thenReturn(Mono.empty());

    // Act & Assert
    StepVerifier.create(service.getById(ENTITY_ID))
            .verifyComplete();

    verify(repository).findById(ENTITY_ID);
    verify(mapper, never()).toDTO(any());
}
```

### Business Logic Test

```java
@Test
void testUpdatePreservesVerificationData() {
    // Arrange
    LocalDateTime verificationDate = LocalDateTime.now();
    
    Entity existingEntity = new Entity();
    existingEntity.setEntityId(ENTITY_ID);
    existingEntity.setIsVerified(true);
    existingEntity.setVerificationDate(verificationDate);
    existingEntity.setVerificationMethod("DOCUMENT");
    existingEntity.setDateCreated(LocalDateTime.now());
    
    Entity updatedEntity = new Entity();
    updatedEntity.setEntityId(ENTITY_ID);
    // No verification data set in the updated entity
    
    when(repository.findById(ENTITY_ID)).thenReturn(Mono.just(existingEntity));
    when(mapper.toEntity(entityDTO)).thenReturn(updatedEntity);
    
    // Mock the save method to verify verification data is preserved
    when(repository.save(any(Entity.class))).thenAnswer(invocation -> {
        Entity savedEntity = invocation.getArgument(0);
        // Verify that verification data is preserved
        if (savedEntity.getIsVerified() == null && existingEntity.getIsVerified()) {
            savedEntity.setIsVerified(existingEntity.getIsVerified());
            savedEntity.setVerificationDate(existingEntity.getVerificationDate());
            savedEntity.setVerificationMethod(existingEntity.getVerificationMethod());
        }
        return Mono.just(savedEntity);
    });
    
    when(mapper.toDTO(any(Entity.class))).thenReturn(entityDTO);

    // Act & Assert
    StepVerifier.create(service.update(ENTITY_ID, entityDTO))
            .expectNext(entityDTO)
            .verifyComplete();

    // Verify that verification data is preserved
    verify(repository).save(argThat(entity -> 
        entity.getIsVerified() != null && entity.getIsVerified() &&
        entity.getVerificationDate() != null &&
        entity.getVerificationMethod() != null));
}
```

### Testing with Pagination and Filtering

```java
@Test
void testFindAll() {
    // Arrange
    FilterRequest<EntityDTO> filterRequest = new FilterRequest<>();
    PaginationResponse<EntityDTO> expectedResponse = new PaginationResponse<>();
    expectedResponse.setContent(List.of(entityDTO));
    expectedResponse.setTotalElements(1);
    
    // Create a mock Filter object
    Filter<EntityDTO> mockFilter = mock(Filter.class);
    when(mockFilter.filter(filterRequest)).thenReturn(Mono.just(expectedResponse));
    
    // Mock the static FilterUtils.createFilter method
    try (MockedStatic<FilterUtils> mockedFilterUtils = mockStatic(FilterUtils.class)) {
        mockedFilterUtils.when(() -> FilterUtils.createFilter(
                eq(Entity.class),
                any()
        )).thenReturn(mockFilter);
        
        // Act & Assert
        StepVerifier.create(service.findAll(filterRequest))
                .expectNext(expectedResponse)
                .verifyComplete();
    }
}
```

## Maintenance

When adding new services or modifying existing ones, ensure that:

1. A corresponding test class is created or updated
2. All methods in the service are covered by tests
3. Edge cases and error scenarios are tested
4. The tests follow the established pattern for consistency

## Test Coverage Metrics

The test suite aims to achieve high test coverage for all service implementations. Coverage metrics include:

- **Line Coverage**: The percentage of code lines executed during tests
- **Branch Coverage**: The percentage of code branches (if/else, switch) executed during tests
- **Method Coverage**: The percentage of methods called during tests

To generate a test coverage report, use the following Maven command:

```bash
mvn test jacoco:report
```

The coverage report will be available in the `target/site/jacoco` directory.

## Continuous Integration

The test suite is integrated into the CI/CD pipeline and runs automatically on every pull request and merge to the main branch. This ensures that all changes to the codebase are properly tested before being deployed to production.