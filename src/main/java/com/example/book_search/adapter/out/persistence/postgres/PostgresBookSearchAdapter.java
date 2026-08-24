package com.example.book_search.adapter.out.persistence.postgres;

import com.example.book_search.adapter.out.persistence.postgres.BookJpaRepository;
import com.example.book_search.application.dto.BookSearchQuery;
import com.example.book_search.application.dto.PageResult;
import com.example.book_search.application.port.out.BookSearchPort;
import com.example.book_search.domain.book.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PostgresBookSearchAdapter
        implements BookSearchPort {

    private final BookJpaRepository bookJpaRepository;

    @Override
    public PageResult<Book> search(
            BookSearchQuery query
    ) {

        PageRequest pageable =
                PageRequest.of(
                        query.page(),
                        query.size()
                );

        Page<Book> result =
                bookJpaRepository
                        .searchByTitle(
                                query.title(),
                                pageable
                        )
                        .map(BookMapper::toDomain);

        return new PageResult<>(
                result.getContent(),
                result.getNumber(),
                result.getSize(),
                result.getTotalElements()
        );
    }
}