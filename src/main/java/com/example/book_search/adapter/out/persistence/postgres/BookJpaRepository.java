package com.example.book_search.adapter.out.persistence.postgres;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BookJpaRepository
        extends JpaRepository<BookJpaEntity, Long> {

    @Query(
            value = """
                    SELECT *
                    FROM books
                    WHERE title ILIKE CONCAT('%', :title, '%')
                    ORDER BY id DESC
                    """,
            countQuery = """
                    SELECT COUNT(*)
                    FROM books
                    WHERE title ILIKE CONCAT('%', :title, '%')
                    """,
            nativeQuery = true
    )
    Page<BookJpaEntity> searchByTitle(
            @Param("title") String title,
            Pageable pageable
    );
}