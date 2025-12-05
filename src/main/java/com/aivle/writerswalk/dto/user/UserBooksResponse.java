package com.aivle.writerswalk.dto.user;

import com.aivle.writerswalk.dto.book.BookResponse;
import java.util.List;
import lombok.Getter;

@Getter
public class UserBooksResponse {

    private final int totalCount;
    private final List<BookResponse> books;

    public UserBooksResponse(List<BookResponse> books) {
        this.totalCount = books.size();
        this.books = books;
    }
}