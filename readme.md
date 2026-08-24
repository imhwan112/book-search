# Book Search API

PostgreSQL과 Spring Boot JPA를 기반으로 구현한 도서 검색 백엔드 프로젝트입니다.

약 30,000건의 도서 데이터를 PostgreSQL에 적재하고 도서명 부분 검색,
페이지네이션, 검색 결과 없음 처리 및 PostgreSQL `pg_trgm` 기반 검색 최적화를 적용합니다.

또한 향후 사용자, 주문, 결제 등의 도메인이 추가될 수 있도록
Hexagonal Architecture(Ports & Adapters)를 기반으로 설계합니다.

---



# 1. Tech Stack

| Category | Technology |
|---|---|
| Language | Java |
| Framework | Spring Boot 4.1.1 |
| ORM | Spring Data JPA / Hibernate |
| Database | PostgreSQL |
| Search Optimization | PostgreSQL pg_trgm |
| API Documentation | Swagger / OpenAPI |
| Container | Docker / Docker Compose |
| Architecture | Hexagonal Architecture |
| Build | Gradle |

---

# 2. Project Requirements

현재 구현 범위는 다음과 같습니다.

- 도서명 검색
- 검색 결과 목록 조회
- 페이지네이션
- 검색 결과가 없는 경우 정상적인 빈 결과 반환
- 검색어 Validation (두 글자 이상 - 한 글자 입력 시 400 ERROR)
- PostgreSQL `pg_trgm` 기반 부분 문자열 검색
- 검색 성능을 위한 인덱스 적용
- Swagger API 문서화
- CSV 기반 초기 데이터 적재
- 실행계획(`EXPLAIN ANALYZE`)을 통한 검색 성능 분석

---

# 3. Architecture

본 프로젝트는 Hexagonal Architecture
(Ports & Adapters)를 기반으로 설계합니다.

핵심 목적은 Application / Domain 로직을
HTTP, JPA, PostgreSQL 등의 외부 기술과 분리하는 것입니다.

이를 통해 향후 저장소나 외부 시스템이 변경되더라도
핵심 비즈니스 로직에 대한 변경을 최소화할 수 있습니다.

## 3.1 Directory Structure

```text
com.example.book_search
│
├── domain
│   └── book
│       ├── Book.java
│       └── BookId.java
│
├── application
│   │
│   ├── dto
│   │   ├── BookSearchQuery.java
│   │   ├── BookSearchResult.java
│   │   └── PageResult.java
│   │
│   ├── port
│   │   ├── in
│   │   │   └── BookSearchUseCase.java
│   │   │
│   │   └── out
│   │       └── BookSearchPort.java
│   │
│   └── service
│       └── BookSearchService.java
│
└── adapter
    │
    ├── in
    │   └── web
    │       ├── BookController.java
    │       ├── BookSearchRequest.java
    │       ├── BookSearchResponse.java
    │       ├── ErrorResponse.java
    │       └── GlobalExceptionHandler.java
    │
    └── out
        └── persistence
            └── postgres
                ├── BookJpaEntity.java
                ├── BookJpaRepository.java
                ├── BookMapper.java
                └── PostgresBookSearchAdapter.java