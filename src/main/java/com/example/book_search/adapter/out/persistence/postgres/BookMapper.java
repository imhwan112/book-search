package com.example.book_search.adapter.out.persistence.postgres;

import com.example.book_search.domain.book.Book;
import com.example.book_search.domain.book.BookId;


public final class BookMapper {

    private BookMapper() {
    }

    public static Book toDomain(
            BookJpaEntity entity
    ) {
        return new Book(
                new BookId(entity.getId()),
                entity.getTitle(),
                entity.getAuthor(),
                entity.getPublisher(),
                entity.getCategory(),
                entity.getPublishedDate(),
                entity.getIsbn(),
                entity.getPrice(),
                entity.getStock()
        );
    }
}