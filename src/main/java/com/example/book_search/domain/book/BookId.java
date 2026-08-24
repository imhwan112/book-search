package com.example.book_search.domain.book;

import java.util.Objects;

public record BookId(Long value) {

    public BookId {
        Objects.requireNonNull(value, "BookId must not be null");

        if (value <= 0) {
            throw new IllegalArgumentException("BookId must be greater than zero");
        }
    }
}