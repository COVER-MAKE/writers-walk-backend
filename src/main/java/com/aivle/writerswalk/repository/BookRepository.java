package com.aivle.writerswalk.repository;

import com.aivle.writerswalk.domain.Book;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findByUserId(Long userId);
}