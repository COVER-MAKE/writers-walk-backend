package com.aivle.writerswalk.service;

import com.aivle.writerswalk.domain.User;
import com.aivle.writerswalk.exception.CustomException;
import com.aivle.writerswalk.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

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
}
