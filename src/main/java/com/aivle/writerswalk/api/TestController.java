package com.aivle.writerswalk.api;

import com.aivle.writerswalk.ApiResponse;
import com.aivle.writerswalk.dto.TestResponseDto;
import com.aivle.writerswalk.service.TestService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
@RequiredArgsConstructor
public class TestController {

    private final TestService testService;

    /**
     * 정상 응답 테스트 API
     * GET /api/test/success
     */
    @GetMapping("/success")
    public ApiResponse<TestResponseDto> testSuccess() {
        TestResponseDto data = testService.getSuccessData();
        return ApiResponse.success(data);
    }

    /**
     * 예외 처리 테스트 API
     * GET /api/test/error
     */
    @GetMapping("/error")
    public ApiResponse<Void> testError() {
        testService.triggerException();
        return ApiResponse.success(null);
    }
}