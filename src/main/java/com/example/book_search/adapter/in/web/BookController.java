package com.example.book_search.adapter.in.web;

import com.example.book_search.application.dto.BookSearchQuery;
import com.example.book_search.application.dto.BookSearchResult;
import com.example.book_search.application.dto.PageResult;
import com.example.book_search.application.port.in.BookSearchUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/books")
@RequiredArgsConstructor
@Tag(
        name = "Book",
        description = "도서 검색 API"
)
public class BookController {

    private final BookSearchUseCase bookSearchUseCase;

    @Operation(
            summary = "도서명 검색",
            description = """
                    도서명을 기준으로 도서를 검색합니다.
                    
                    - title을 입력하면 해당 문자열이 포함된 도서를 검색합니다.
                    - title을 생략하면 전체 도서를 조회합니다.
                    - page는 0부터 시작합니다.
                    - size는 페이지당 조회 건수입니다.
                    - 최대 100건까지 조회할 수 있습니다.
                    """
    )
    // API 응답값 구체화 필요
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "도서 검색 성공"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "잘못된 요청",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    implementation = ErrorResponse.class
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "서버 에러 발생"
            ),
    })
    @PostMapping("/search")
    public ResponseEntity<PageResult<BookSearchResult>> search(
            @Valid @RequestBody BookSearchRequest request
    ) {

        BookSearchQuery query = new BookSearchQuery(
                request.title(),
                request.page(),
                request.size()
        );

        return ResponseEntity.ok(
                bookSearchUseCase.search(query)
        );
    }
}