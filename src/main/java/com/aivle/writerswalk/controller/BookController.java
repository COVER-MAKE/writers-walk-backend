package com.aivle.writerswalk.controller;

import com.aivle.writerswalk.ApiResponse;
import com.aivle.writerswalk.domain.Book;
import com.aivle.writerswalk.dto.book.BookCreateRequest;
import com.aivle.writerswalk.dto.book.BookResponse;
import com.aivle.writerswalk.dto.book.BookUpdateRequest;
import com.aivle.writerswalk.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/books")
public class BookController {

    private final BookService bookService;

    @PostMapping
    public ApiResponse<BookResponse> createBook(@RequestBody BookCreateRequest request) {
        Book saved = bookService.createBook(request);
        return ApiResponse.success(new BookResponse(saved));
    }

    @GetMapping("/{id}")
    public ApiResponse<BookResponse> getBook(@PathVariable Long id) {
        Book book = bookService.getBook(id);
        return ApiResponse.success(new BookResponse(book));
    }

    @GetMapping
    public ApiResponse<List<BookResponse>> getAllBooks() {
        List<Book> list = bookService.getAllBooks();
        return ApiResponse.success(list.stream().map(BookResponse::new).toList());
    }

    @PutMapping("/{id}")
    public ApiResponse<BookResponse> updateBook(
            @PathVariable Long id,
            @RequestBody BookUpdateRequest request
    ) {
        Book updated = bookService.updateBook(id, request);
        return ApiResponse.success(new BookResponse(updated));
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return ApiResponse.success(null);
    }
}