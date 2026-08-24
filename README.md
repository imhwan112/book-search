# Book Search API

PostgreSQL과 Spring Boot JPA를 기반으로 구현한 도서 검색 백엔드 프로젝트입니다.

약 30,000건의 도서 데이터를 PostgreSQL에 적재하고 도서명 부분 검색,
페이지네이션, 검색 결과 없음 처리 및 PostgreSQL `pg_trgm` 기반 검색 최적화를 적용합니다.

또한 향후 사용자, 주문, 결제 등의 도메인이 추가될 수 있도록
Hexagonal Architecture(Ports & Adapters)를 기반으로 설계합니다.

---

# 실행 방법

## 1. 사전 설치

프로젝트를 실행하기 전에 다음 프로그램이 설치되어 있어야 합니다.

- Git
- Java
- Docker
- Docker Compose

Java 버전은 프로젝트의 Gradle 설정에 맞는 버전을 사용해야 합니다.

설치 여부는 터미널에서 다음 명령어로 확인할 수 있습니다.

```bash
git --version
java -version
docker --version
docker compose version
```

각 명령어를 실행했을 때 버전 정보가 정상적으로 출력되면 됩니다.

---

## 2. 프로젝트 다운로드

터미널에서 프로젝트를 다운로드할 위치로 이동한 후
다음 명령어를 실행합니다.

```bash
git clone https://github.com/imhwan112/book-search.git
```

다운로드가 완료되면 프로젝트 디렉터리로 이동합니다.

```bash
cd book-search
```

---

## 3. PostgreSQL 실행

프로젝트에는 PostgreSQL을 Docker Compose로 실행하기 위한
`docker-compose.yml`이 포함되어 있습니다.

다음 명령어를 실행합니다.

```bash
docker compose up -d
```

`-d` 옵션은 백그라운드에서 컨테이너를 실행하기 위한 옵션입니다.

정상적으로 실행되었는지 확인합니다.

```bash
docker compose ps
```

PostgreSQL 컨테이너의 상태가 `Up` 또는 `running`으로 표시되면
정상적으로 실행된 것입니다.

---


## 4. pg_trgm 및 검색 인덱스 확인 (현시점 테이블 및 데이터 없음)

도서명 부분 검색 성능을 향상시키기 위해 PostgreSQL의
`pg_trgm` 확장 기능과 GIN 인덱스를 사용합니다.

PostgreSQL에서 다음 명령어를 실행합니다.

```sql
CREATE EXTENSION IF NOT EXISTS pg_trgm;
```

확장 기능이 정상적으로 생성되었는지 확인합니다.

```sql
SELECT extname
FROM pg_extension
WHERE extname = 'pg_trgm';
```

`pg_trgm`이 조회되면 정상입니다.

도서명 검색을 위한 인덱스는 다음과 같은 형태로 구성합니다.

```sql
CREATE INDEX IF NOT EXISTS idx_books_title_trgm
ON books
USING gin (title gin_trgm_ops);
```


---

## 5. Spring Boot 애플리케이션 실행 (해당 시점 테이블 생성)
테스트를 위한 테이블 생성을 위해 application.yml의 hibernate ddl-auto :create 로 변경해주시면 됩니다.

PostgreSQL이 정상적으로 실행된 상태에서
Spring Boot 애플리케이션을 실행합니다.

Gradle Wrapper를 사용하는 경우 다음 명령어를 실행합니다.


## 6. PostgreSQL에 데이터 삽입 아래 명령어 실행
COPY books (
    id,
    title,
    author,
    publisher,
    category,
    published_date,
    isbn,
    price,
    stock
)
FROM '/data/books.csv'
WITH (
    FORMAT CSV,
    HEADER TRUE,
    ENCODING 'UTF8'
)

## 7. swagger에서 도서 검색 API 테스트 방법
1. 해당 URL 접속 http://localhost:8080/swagger-ui/index.html
2. title, page, size 3개의 request 옵션이 존재하며 try-it-out 버튼 클릭 후 옵션값들 수정하면서 테스트하시면 됩니다.
3. swagger 사용법을 모르실 경우 아래와 같은 예시 명령어를 터미널 상에 입력하시면 됩니다.
   curl -X 'POST' \
  'http://localhost:8080/api/v1/books/search' \
  -H 'accept: */*' \
  -H 'Content-Type: application/json' \
  -d '{
  "title": "사전",
  "page": 0,
  "size": 20
}'

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
