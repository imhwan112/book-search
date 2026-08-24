package com.example.book_search.adapter.in.web;

import com.example.book_search.application.dto.BookSearchResult;
import com.example.book_search.application.dto.PageResult;

import java.time.LocalDate;
import java.util.List;

public record BookSearchResponse(
        List<BookItem> content,
        int page,
        int size,
        long totalElements,
        int totalPages,
        boolean first,
        boolean last
) {

    public static BookSearchResponse from(
            PageResult<BookSearchResult> result
    ) {

        List<BookItem> content =
                result.content()
                        .stream()
                        .map(BookItem::from)
                        .toList();

        return new BookSearchResponse(
                content,
                result.page(),
                result.size(),
                result.totalElements(),
                result.totalPages(),
                result.first(),
                result.last()
        );
    }

    public record BookItem(
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

        private static BookItem from(
                BookSearchResult book
        ) {
            return new BookItem(
                    book.id(),
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
}