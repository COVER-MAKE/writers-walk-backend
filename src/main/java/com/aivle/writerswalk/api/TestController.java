package com.aivle.writerswalk.api;

import com.aivle.writerswalk.ApiResponse;
import com.aivle.writerswalk.dto.TestResponseDto;
import com.aivle.writerswalk.service.TestService;
import lombok.RequiredArgsConstructor;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
@RequiredArgsConstructor
@Tag(name = "Test", description = "테스트용 API")
public class TestController {

    private final TestService testService;

    /**
     * 정상 응답 테스트 API
     * GET /api/test/success
     */
    @Operation(summary = "정상 응답 확인", description = "성공 응답을 반환하는 테스트 API입니다.")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "성공 응답")
    @GetMapping("/success")
    public ApiResponse<TestResponseDto> testSuccess() {
        TestResponseDto data = testService.getSuccessData();
        return ApiResponse.success(data);
    }

    /**
     * 예외 처리 테스트 API
     * GET /api/test/error
     */
    @Operation(summary = "예외 흐름 확인", description = "GlobalExceptionHandler 동작을 확인하기 위한 테스트 API입니다.")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "예외 발생 예시")
    @GetMapping("/error")
    public ApiResponse<Void> testError() {
        testService.triggerException();
        return ApiResponse.success(null);
    }
}
