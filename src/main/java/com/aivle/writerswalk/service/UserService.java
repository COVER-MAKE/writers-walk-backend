package com.aivle.writerswalk.service;

import com.aivle.writerswalk.domain.Book;
import com.aivle.writerswalk.domain.User;
import com.aivle.writerswalk.exception.CustomException;
import com.aivle.writerswalk.repository.BookRepository;
import com.aivle.writerswalk.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final BookRepository bookRepository;

    public void signup(
            String email,
            String password
    ) {
        if (userRepository.existsByEmail(email)) {
            throw new CustomException("이미 존재하는 이메일입니다.", HttpStatus.CONFLICT);
        }

        String encodedPassword = passwordEncoder.encode(password);
        User user = User.builder()
                .email(email)
                .password(encodedPassword)
                .build();
        userRepository.save(user);
    }

    public User getCurrentUserProfile(String email) {
        return findUserByEmail(email);
    }

    public List<Book> getCurrentUserBooks(String email) {
        User user = findUserByEmail(email);
        return bookRepository.findByUserId(user.getId());
    }

    private User findUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException("유저를 찾을 수 없습니다.", HttpStatus.NOT_FOUND));
    }
}
