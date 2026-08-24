package com.example.book_search.adapter.out.persistence.postgres;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "books")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BookJpaEntity {

    @Id
    private Long id;

    @Column(nullable = false, length = 500)
    private String title;

    @Column(nullable = false, length = 200)
    private String author;

    @Column(length = 200)
    private String publisher;

    @Column(length = 100)
    private String category;

    @Column(name = "published_date")
    private LocalDate publishedDate;

    @Column(length = 20)
    private String isbn;

    private Integer price;

    @Column(nullable = false)
    private Integer stock;
}