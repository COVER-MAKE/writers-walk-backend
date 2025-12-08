package com.aivle.writerswalk.api;

import com.aivle.writerswalk.ApiResponse;
import com.aivle.writerswalk.domain.Book;
import com.aivle.writerswalk.dto.book.BookCoverUpdateRequest;
import com.aivle.writerswalk.dto.book.BookCreateRequest;
import com.aivle.writerswalk.dto.book.BookResponse;
import com.aivle.writerswalk.dto.book.BookUpdateRequest;
import com.aivle.writerswalk.service.BookService;
import lombok.RequiredArgsConstructor;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/books")
@Tag(name = "Book", description = "도서 관련 API")
public class BookController {

    private final BookService bookService;

    @PostMapping
    @Operation(summary = "도서 등록", description = "로그인한 사용자가 새 도서를 등록합니다. \"장르 (NOVEL, FANTASY, ESSAY, POETRY, HISTORY, SCIENCE)\"")
    public ApiResponse<BookResponse> createBook(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody BookCreateRequest request
    ) {
        Book saved = bookService.createBook(request, userDetails.getUsername());
        return ApiResponse.success(new BookResponse(saved));
    }

    @GetMapping("/{id}")
    @Operation(summary = "도서 단건 조회", description = "도서 상세 정보를 조회합니다.")
    public ApiResponse<BookResponse> getBook(@PathVariable Long id) {
        Book book = bookService.getBook(id);
        return ApiResponse.success(new BookResponse(book));
    }

    @GetMapping
    @Operation(summary = "도서 목록 조회", description = "등록된 도서 목록을 조회합니다.")
    public ApiResponse<List<BookResponse>> getAllBooks() {
        List<Book> list = bookService.getAllBooks();
        return ApiResponse.success(list.stream().map(BookResponse::new).toList());
    }

    @PutMapping("/{id}")
    @Operation(summary = "도서 수정", description = "로그인한 사용자가 본인이 등록한 도서를 수정합니다.")
    public ApiResponse<BookResponse> updateBook(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody BookUpdateRequest request
    ) {
        Book updated = bookService.updateBook(id, request, userDetails.getUsername());
        return ApiResponse.success(new BookResponse(updated));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "도서 삭제", description = "로그인한 사용자가 본인이 등록한 도서를 삭제합니다.")
    public ApiResponse<Void> deleteBook(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        bookService.deleteBook(id, userDetails.getUsername());
        return ApiResponse.success(null);
    }

    @PutMapping("/{id}/cover-url")
    @Operation(summary = "썸네일 이미지 등록", description = "AI가 생성한 이미지 URL을 저장합니다.")
    public ApiResponse<Void> updateCoverUrl(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody BookCoverUpdateRequest request
    ) {
        bookService.updateCoverUrl(id, request.getThumbnailUrl(), userDetails.getUsername());
        return ApiResponse.success(null);
    }
}