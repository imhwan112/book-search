package com.example.book_search.domain.book;

import java.time.LocalDate;
import java.util.Objects;

public record Book(
        BookId id,
        String title,
        String author,
        String publisher,
        String category,
        LocalDate publishedDate,
        String isbn,
        Integer price,
        Integer stock
) {

    public Book {
        Objects.requireNonNull(id, "id must not be null");
        Objects.requireNonNull(title, "title must not be null");
        Objects.requireNonNull(author, "author must not be null");

        if (title.isBlank()) {
            throw new IllegalArgumentException("title must not be blank");
        }

        if (price != null && price < 0) {
            throw new IllegalArgumentException("price must not be negative");
        }

        if (stock != null && stock < 0) {
            throw new IllegalArgumentException("stock must not be negative");
        }
    }
}