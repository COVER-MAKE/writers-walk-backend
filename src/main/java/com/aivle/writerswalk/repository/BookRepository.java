package com.aivle.writerswalk.repository;

import com.aivle.writerswalk.domain.Book;
import com.aivle.writerswalk.domain.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {

    // 사용자가 작성한 책 조회
    List<Book> findByUserId(Long userId);

    // 장르별 조회
    List<Book> findByGenre(Genre genre);

    // 제목 또는 내용에 키워드가 포함된 도서 검색
    List<Book> findByTitleContainingOrContentContaining(String titleKeyword, String contentKeyword);
}