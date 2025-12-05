package com.aivle.writerswalk.dto.user;

import com.aivle.writerswalk.domain.User;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class UserResponse {

    private final Long id;
    private final String email;
    private final LocalDateTime createdAt;

    public UserResponse(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.createdAt = user.getCreatedAt();
    }
}
