package com.aivle.writerswalk.repository;

import com.aivle.writerswalk.domain.Book;
import com.aivle.writerswalk.domain.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {

    // 장르로 조회
    List<Book> findByGenre(Genre genre);

    // 제목 또는 내용 검색
    List<Book> findByTitleContainingOrContentContaining(String titleKeyword, String contentKeyword);
}