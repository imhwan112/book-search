package com.example.book_search.application.dto;

public record BookSearchQuery(
        String title,
        int page,
        int size
) {

    public int offset() {
        return page * size;
    }
}