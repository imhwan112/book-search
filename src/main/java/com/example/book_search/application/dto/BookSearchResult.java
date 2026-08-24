package com.example.book_search.application.dto;

import com.example.book_search.domain.book.Book;

import java.time.LocalDate;

public record BookSearchResult(
        Long id,
        String title,
        String author,
        String publisher,
        String category,
        LocalDate publishedDate,
        String isbn,
        Integer price,
        Integer stock
) {

    public static BookSearchResult from(Book book) {
        return new BookSearchResult(
                book.id().value(),
                book.title(),
                book.author(),
                book.publisher(),
                book.category(),
                book.publishedDate(),
                book.isbn(),
                book.price(),
                book.stock()
        );
    }
}