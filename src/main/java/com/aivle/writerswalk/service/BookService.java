package com.aivle.writerswalk.service;

import com.aivle.writerswalk.domain.Book;
import com.aivle.writerswalk.dto.book.BookCreateRequest;
import com.aivle.writerswalk.dto.book.BookUpdateRequest;
import com.aivle.writerswalk.domain.User;
import com.aivle.writerswalk.exception.CustomException;
import com.aivle.writerswalk.repository.BookRepository;
import com.aivle.writerswalk.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import com.aivle.writerswalk.domain.Genre;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BookService {

    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    @Transactional
    public Book createBook(
            BookCreateRequest request,
            String userEmail
    ) {
        User user = getUser(userEmail);
        Book book = Book.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .genre(request.getGenre())
                .user(user)
                .build();

        return bookRepository.save(book);
    }

    @Transactional
    public Book updateBook(
            Long id,
            BookUpdateRequest request,
            String userEmail
    ) {
        Book book = getOwnedBook(id, userEmail);

        book.update(
                request.getTitle(),
                request.getContent(),
                request.getGenre()
        );

        return bookRepository.save(book);
    }

    @Transactional
    public void updateCoverUrl(Long id, String imageUrl, String userEmail) {
        Book book = getOwnedBook(id, userEmail);
        book.updateThumbnailUrl(imageUrl); // Book 엔티티에 이 메서드가 있어야 함
    }

    public Book getBook(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new CustomException("해당 책을 찾을 수 없습니다.", HttpStatus.NOT_FOUND));
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @Transactional
    public void deleteBook(
            Long id,
            String userEmail
    ) {
        Book book = getOwnedBook(id, userEmail);
        bookRepository.delete(book);
    }

    private Book getOwnedBook(Long id, String userEmail) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new CustomException("해당 책을 찾을 수 없습니다.", HttpStatus.NOT_FOUND));

        if (book.getUser() == null || !book.getUser().getEmail().equals(userEmail)) {
            throw new CustomException("본인이 등록한 책만 수정/삭제할 수 있습니다.", HttpStatus.FORBIDDEN);
        }
        return book;
    }

    private User getUser(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException("로그인 정보가 유효하지 않습니다.", HttpStatus.UNAUTHORIZED));
    }
    // ---------------------------------------------
// 🔍 장르별 조회
// ---------------------------------------------
    public List<Book> getBooksByGenre(Genre genre) {
        return bookRepository.findByGenre(genre);
    }

    // ---------------------------------------------
// 🔍 검색 기능 (제목 + 내용)
// ---------------------------------------------
    public List<Book> searchBooks(String keyword) {
        return bookRepository.findByTitleContainingOrContentContaining(keyword, keyword);
    }
}