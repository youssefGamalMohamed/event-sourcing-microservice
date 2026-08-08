# Task 2 Execution Report: Implement `GlobalResponseBodyAdvice` and `GlobalExceptionHandler` in `category-service`

## Summary
- **Status**: `DONE`
- **Date**: 2026-08-09
- **Target Service**: `category-service`

---

## Actions Taken

### 1. Created Integration Test
- Created [`CategoryControllerResponseTest.java`](file:///home/youssef-gamal/Desktop/git-workspace/event-sourcing-microservice/category-service/src/test/java/com/youssef/gamal/ecommerce/microservice/category/commands/controllers/CategoryControllerResponseTest.java) under `category-service/src/test/java/com/youssef/gamal/ecommerce/microservice/category/commands/controllers/`.
- Configured MockMvc test to invoke `POST /categories` and assert that the HTTP response body is automatically wrapped in `ApiResponse<T>` with `status`, `message`, `data.id`, and `path` fields.

### 2. Ran Test to Confirm Initial Failure (Red Phase)
- Executed `./mvnw test -f category-service/pom.xml -Dtest=CategoryControllerResponseTest`.
- Confirmed test failure as expected:
  ```text
  java.lang.AssertionError: No value at JSON path "$.status"
      Caused by: com.jayway.jsonpath.PathNotFoundException: No results for path: $['status']
  ```

### 3. Implemented `GlobalResponseBodyAdvice`
- Created [`GlobalResponseBodyAdvice.java`](file:///home/youssef-gamal/Desktop/git-workspace/event-sourcing-microservice/category-service/src/main/java/com/youssef/gamal/ecommerce/microservice/category/common/response/GlobalResponseBodyAdvice.java) annotated with `@RestControllerAdvice`.
- Implements `ResponseBodyAdvice<Object>`:
  - Excludes system endpoints (`springdoc`, `swagger`, `actuator`).
  - Checks if response body is already an instance of `ApiResponse`; if so, returns unchanged.
  - Automatically wraps arbitrary objects in `ApiResponse.success(body, statusCode, path)`.
  - Serializes `String` responses using `ObjectMapper` to maintain Spring `StringHttpMessageConverter` compatibility.

### 4. Implemented `GlobalExceptionHandler`
- Created [`GlobalExceptionHandler.java`](file:///home/youssef-gamal/Desktop/git-workspace/event-sourcing-microservice/category-service/src/main/java/com/youssef/gamal/ecommerce/microservice/category/common/response/GlobalExceptionHandler.java) annotated with `@RestControllerAdvice`.
- Implements `@ExceptionHandler(Exception.class)` to catch generic unhandled exceptions, log details, and return a `ResponseEntity<ApiResponse<Void>>` with HTTP 500.

### 5. Verified Tests (Green Phase)
- Executed `./mvnw test -f category-service/pom.xml -Dtest=CategoryControllerResponseTest`:
  ```text
  [INFO] Tests run: 1, Failures: 0, Errors: 0, Skipped: 0
  [INFO] BUILD SUCCESS
  ```
- Executed full test suite for `category-service` (`./mvnw test -f category-service/pom.xml`):
  ```text
  [INFO] Tests run: 3, Failures: 0, Errors: 0, Skipped: 0
  [INFO] BUILD SUCCESS
  ```

### 6. Git Commit
- Committed changes:
  ```bash
  git commit -m "feat(category-service): add GlobalResponseBodyAdvice and GlobalExceptionHandler"
  ```
- Commit hash: `49f71c6`

---

## Files Created / Modified
- `category-service/src/main/java/com/youssef/gamal/ecommerce/microservice/category/common/response/GlobalResponseBodyAdvice.java`
- `category-service/src/main/java/com/youssef/gamal/ecommerce/microservice/category/common/response/GlobalExceptionHandler.java`
- `category-service/src/test/java/com/youssef/gamal/ecommerce/microservice/category/commands/controllers/CategoryControllerResponseTest.java`

---

## Fix Round 1

### Summary of Fixes Applied
1. **Actuator Exclusion Package Matching**:
   Updated `GlobalResponseBodyAdvice.supports()` to check for `"actuate"` (matching `org.springframework.boot.actuate`) in addition to `"actuator"`.
2. **Binary and Resource Response Exclusions**:
   Updated `GlobalResponseBodyAdvice.supports()` to check for `byte[]` and `Resource` return types, as well as `ByteArrayHttpMessageConverter` and `ResourceHttpMessageConverter`.
3. **Path-Based Exclusions**:
   Updated `GlobalResponseBodyAdvice.beforeBodyWrite()` to inspect request URI path and skip wrapping for requests starting with `/v3/api-docs`, `/swagger-ui`, or `/actuator`.
4. **Exception Handling Test Coverage**:
   Added `testUnhandledExceptionReturnsApiResponseWithErrorStatus` in `CategoryControllerResponseTest` to verify that unhandled exceptions processed by `GlobalExceptionHandler` return `ApiResponse<Void>` with HTTP 500 status, correct error message, and path.

### Verification Results
- Executed `./mvnw test -f category-service/pom.xml -Dtest=CategoryControllerResponseTest,ApiResponseTest`
- Output: `Tests run: 5, Failures: 0, Errors: 0, Skipped: 0` -> `BUILD SUCCESS`
- Executed full test suite for `category-service`:
- Output: `Tests run: 4, Failures: 0, Errors: 0, Skipped: 0` -> `BUILD SUCCESS`

