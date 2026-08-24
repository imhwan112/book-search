package com.example.book_search.application.dto;

import java.util.List;

public record PageResult<T>(
        List<T> content,
        int page,
        int size,
        long totalElements
) {

    public int totalPages() {
        if (size <= 0) {
            return 0;
        }

        return (int) Math.ceil(
                (double) totalElements / size
        );
    }

    public boolean first() {
        return page == 0;
    }

    public boolean last() {
        return page >= totalPages() - 1;
    }
}