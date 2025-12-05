package com.aivle.writerswalk.dto.book;

import com.aivle.writerswalk.domain.Book;
import lombok.Getter;

@Getter
public class BookResponse {

    private Long id;
    private String title;
    private String content;
    private String thumbnailUrl;
    private String genre;

    public BookResponse(Book book) {
        this.id = book.getId();
        this.title = book.getTitle();
        this.content = book.getContent();
        this.thumbnailUrl = book.getThumbnailUrl();
        this.genre = book.getGenre().name();
    }
}