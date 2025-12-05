package com.aivle.writerswalk.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(description = "로그인 요청 DTO")
public class LoginRequest {

    @Schema(description = "사용자 이메일", example = "1234@naver.com")
    private String email;

    @Schema(description = "사용자 비밀번호", example = "password1234")
    private String password;
}