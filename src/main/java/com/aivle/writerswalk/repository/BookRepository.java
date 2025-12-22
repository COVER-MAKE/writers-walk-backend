package com.aivle.writerswalk.repository;

import com.aivle.writerswalk.domain.Book;
import com.aivle.writerswalk.domain.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.EntityGraph;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {

    // 사용자가 작성한 책 조회
    @EntityGraph(attributePaths = "user")
    List<Book> findByUserId(Long userId);

    // 장르별 조회
    @EntityGraph(attributePaths = "user")
    List<Book> findByGenre(Genre genre);

    // 제목 또는 내용에 키워드가 포함된 도서 검색
    @EntityGraph(attributePaths = "user")
    List<Book> findByTitleContainingOrContentContaining(String titleKeyword, String contentKeyword);

    @Override
    @EntityGraph(attributePaths = "user")
    List<Book> findAll();

    @Override
    @EntityGraph(attributePaths = "user")
    Optional<Book> findById(Long id);
}