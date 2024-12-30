# LatinHouse.BE
Backend for LatinHouse

## Project Configuration

이 프로젝트는 [Spring Initializr](https://start.spring.io)에서 생성되었습니다. 아래는 프로젝트 생성 시 사용된 설정입니다.

### Project Metadata
- **Group**: `com.latinhouse`
- **Artifact**: `backend`
- **Name**: `backend`
- **Description**: `Backend for Latin House Project`
- **Package name**: `com.latinhouse.backend`
- **Packaging**: `Jar`
- **Java Version**: `23`

### Dependencies
- **Spring Boot Version**: `3.4.1`
- **Dependencies**:
    - Spring DevTools
    - Lombok
    - H2 Database
    - Spring Web
    - Spring Security
    - Validation
    - Spring Data JPA
    - Spring Data MongoDB

### Additional Information
- **Build Tool**: `Gradle`
- **Language**: `Java`

---

# Database Information

## MongoDB with Docker Compose

### Docker Compose Configuration
- **Host**: localhost
- **Port**: 27017
- **Username**: admin
- **Password**: password
- **Authentication Database**: admin
- **URI**: mongodb://admin:password@localhost:27017/?authSource=admin

### How to Start the Database
1. docker compose up -d
2. docker ps

---

## Issue List

1. **[2024-12-30]** [Swagger에서 `Handler dispatch failed` 오류 발생](#issue-1)
  - **Description**: `@RestControllerAdvice`가 Swagger와 호환되지 않는 문제.
  - **Status**: `Pending`
  - **Assigned To**: Backend Developer
  - **Priority**: `Medium`

---

### 상세 Issue 설명

#### Issue 1: [Swagger에서 `Handler dispatch failed` 오류 발생]
- **Date**: 2024-12-30
- **Description**: Swagger에서 예외 처리 관련 오류 발생. `ControllerAdviceBean.<init>(java.lang.Object)` 오류로 인해 Swagger UI가 동작하지 않음.
- **Steps to Reproduce**:
  1. `/swagger-ui.html`로 접속.
  2. `Handler dispatch failed` 에러 발생.
     1. 2024-12-30T15:20:10.041+09:00  WARN 62267 --- [nio-8080-exec-4] .m.m.a.ExceptionHandlerExceptionResolver : Resolved [jakarta.servlet.ServletException: Handler dispatch failed: java.lang.NoSuchMethodError: 'void org.springframework.web.method.ControllerAdviceBean.<init>(java.lang.Object)']
- **Expected Behavior**: Swagger UI에서 모든 API 문서를 정상적으로 렌더링.
- **Actual Behavior**: Swagger UI 렌더링 실패
- **Suggested Solution**:
  - Spring Boot와 의존성 버전 업그레이드.
  - Swagger 설정 검토.
- **Action Taken**:
  - @RestControllerAdvice 주석처리
  - 이후 에러 전역처리를 위한 별도 작업 필요
