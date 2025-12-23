package com.aivle.writerswalk.api;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.aivle.writerswalk.config.SecurityConfig;
import com.aivle.writerswalk.dto.TestResponseDto;
import com.aivle.writerswalk.exception.CustomException;
import com.aivle.writerswalk.exception.GlobalExceptionHandler;
import com.aivle.writerswalk.service.TestService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(TestController.class)
@Import({GlobalExceptionHandler.class, SecurityConfig.class})
@ActiveProfiles("test")
class TestControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TestService testService;

    @Test
    void healthCheckReturnsOk() throws Exception {
        mockMvc.perform(get("/api/test/health"))
                .andExpect(status().isOk())
                .andExpect(content().string("OK"));
    }

    @Test
    @WithMockUser
    void testSuccessReturnsApiResponse() throws Exception {
        TestResponseDto response = new TestResponseDto("테스트 유저", "API 연결 성공!");
        when(testService.getSuccessData()).thenReturn(response);

        mockMvc.perform(get("/api/test/success"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.message").value("success"))
                .andExpect(jsonPath("$.data.name").value("테스트 유저"))
                .andExpect(jsonPath("$.data.description").value("API 연결 성공!"));
    }

    @Test
    @WithMockUser
    void testErrorReturnsApiResponse() throws Exception {
        doThrow(new CustomException("의도적으로 발생시킨 테스트 에러입니다.", HttpStatus.BAD_REQUEST))
                .when(testService).triggerException();

        mockMvc.perform(get("/api/test/error"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.message").value("의도적으로 발생시킨 테스트 에러입니다."));
    }
}
