package com.aivle.writerswalk.dto.book;

import com.aivle.writerswalk.domain.Genre;
import lombok.Getter;

@Getter
public class BookUpdateRequest {
    private String title;
    private String content;
    private Genre genre;
}