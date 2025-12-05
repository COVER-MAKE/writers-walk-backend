package com.aivle.writerswalk.repository;

import com.aivle.writerswalk.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}