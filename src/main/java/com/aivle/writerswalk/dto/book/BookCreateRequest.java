package com.aivle.writerswalk.dto.book;

import com.aivle.writerswalk.domain.Genre;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
public class BookCreateRequest {
    private String title;
    private String content;
    private Genre genre;
}