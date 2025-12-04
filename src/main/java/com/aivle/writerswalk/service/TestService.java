package com.aivle.writerswalk.service;

import com.aivle.writerswalk.dto.TestResponseDto;
import com.aivle.writerswalk.exception.CustomException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class TestService {

    public TestResponseDto getSuccessData() {
        return new TestResponseDto("테스트 유저", "API 연결 성공!");
    }

    public void triggerException() {
        // HttpStatus.BAD_REQUEST (400)를 담아서 던집니다.
        throw new CustomException("의도적으로 발생시킨 테스트 에러입니다.", HttpStatus.BAD_REQUEST);
    }
}