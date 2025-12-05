package com.aivle.writerswalk.dto.book;

import com.aivle.writerswalk.domain.Genre;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BookCreateRequest {
    private String title;
    private String content;

    @Schema(
            description = "장르 (NOVEL, FANTASY, ESSAY, POETRY, HISTORY, SCIENCE)",
            example = "NOVEL"
    )
    private Genre genre;
}
