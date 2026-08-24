package com.example.book_search.application.port.out;

import com.example.book_search.application.dto.BookSearchQuery;
import com.example.book_search.domain.book.Book;
import com.example.book_search.application.dto.PageResult;


public interface BookSearchPort {

    PageResult<Book> search(
            BookSearchQuery query
    );
}