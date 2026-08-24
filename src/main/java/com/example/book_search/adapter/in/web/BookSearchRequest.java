package com.example.book_search.adapter.in.web;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

public record BookSearchRequest(

        @Schema(
                description = "검색할 도서명. 입력한 문자열이 포함된 도서를 검색합니다. 생략하면 전체 도서를 조회합니다.",
                example = "사전",
                nullable = true,
                maxLength = 100
        )
        @Size(
                min = 2,
                max = 100,
                message = "도서명 검색어는 2자 이상 100자 이하로 입력해주세요."
        )
        String title,

        @Schema(
                description = "조회할 페이지 번호. 0부터 시작합니다.",
                example = "0",
                defaultValue = "0",
                minimum = "0"
        )
        @Min(
                value = 0,
                message = "페이지 번호는 0 이상이어야 합니다."
        )
        Integer page,

        @Schema(
                description = "페이지당 조회할 도서 수. 최대 100건까지 조회할 수 있습니다.",
                example = "20",
                defaultValue = "20",
                minimum = "1",
                maximum = "100"
        )
        @Min(
                value = 1,
                message = "페이지 크기는 1 이상이어야 합니다."
        )
        @Max(
                value = 100,
                message = "페이지 크기는 최대 100까지 가능합니다."
        )
        Integer size

) {

    public BookSearchRequest {
        page = page == null ? 0 : page;
        size = size == null ? 20 : size;
    }
}