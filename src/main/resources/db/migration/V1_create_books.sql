CREATE EXTENSION IF NOT EXISTS pg_trgm;

CREATE TABLE books
(
    id              BIGINT PRIMARY KEY,
    title           VARCHAR(500) NOT NULL,
    author          VARCHAR(200) NOT NULL,
    publisher       VARCHAR(200),
    category        VARCHAR(100),
    published_date  DATE,
    isbn            VARCHAR(20),
    price           INTEGER,
    stock           INTEGER NOT NULL DEFAULT 0
);

CREATE INDEX idx_books_title_trgm
    ON books
    USING GIN (title gin_trgm_ops);