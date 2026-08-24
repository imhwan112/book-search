package com.example.book_search.adapter.in.web;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "API 오류 응답")
public record ErrorResponse(

        @Schema(
                description = "HTTP 상태 코드",
                example = "400"
        )
        int status,

        @Schema(
                description = "오류 메시지",
                example = "도서명 검색어는 2자 이상 100자 이하로 입력해주세요."
        )
        String message

) {
}