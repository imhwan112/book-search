package com.example.book_search.application.port.in;

import com.example.book_search.application.dto.BookSearchQuery;
import com.example.book_search.application.dto.BookSearchResult;
import com.example.book_search.application.dto.PageResult;


public interface BookSearchUseCase {

    PageResult<BookSearchResult> search(
            BookSearchQuery query
    );
}