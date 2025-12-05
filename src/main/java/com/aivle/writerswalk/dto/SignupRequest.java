package com.aivle.writerswalk.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Schema(description = "로그인 요청 DTO")
@Getter
public class SignupRequest {

    @Schema(description = "사용자 로그인 email", example = "1234@naver.com")
    private String email;

    @Schema(description = "사용자 비밀번호", example = "password1234")
    private String password;
}
