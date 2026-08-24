package com.example.book_search.application.service;

import com.example.book_search.application.dto.BookSearchQuery;
import com.example.book_search.application.dto.BookSearchResult;
import com.example.book_search.application.dto.PageResult;
import com.example.book_search.application.port.in.BookSearchUseCase;
import com.example.book_search.application.port.out.BookSearchPort;
import com.example.book_search.domain.book.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BookSearchService implements BookSearchUseCase {

    private final BookSearchPort bookSearchPort;

    @Override
    public PageResult<BookSearchResult> search(
            BookSearchQuery query
    ) {

        PageResult<Book> page =
                bookSearchPort.search(query);

        return new PageResult<>(
                page.content()
                        .stream()
                        .map(BookSearchResult::from)
                        .toList(),
                page.page(),
                page.size(),
                page.totalElements()
        );
    }
}