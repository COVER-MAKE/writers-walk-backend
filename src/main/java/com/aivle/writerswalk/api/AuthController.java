package com.aivle.writerswalk.api;

import com.aivle.writerswalk.ApiResponse;
import com.aivle.writerswalk.dto.LoginRequest;
import com.aivle.writerswalk.dto.SignupRequest;
import com.aivle.writerswalk.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Tag(name = "Auth", description = "인증 관련 API")
public class AuthController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;

    @Operation(summary = "회원가입", description = "이메일과 비밀번호로 신규 사용자를 생성합니다.")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "회원가입 성공")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "409", description = "이미 존재하는 이메일")
    @PostMapping("/signup")
    public ApiResponse<String> signup(@RequestBody SignupRequest request) {
        userService.signup(request.getEmail(), request.getPassword());
        return ApiResponse.created("회원가입 성공");
    }

    @Operation(summary = "로그인", description = "이메일/비밀번호로 인증 후 세션을 발급합니다.")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "로그인 성공")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "인증 실패")
    @PostMapping("/login")
    public ApiResponse<Map<String, String>> login(
            @RequestBody LoginRequest request,
            HttpServletRequest httpRequest
    ) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );

            SecurityContext context = SecurityContextHolder.createEmptyContext();
            context.setAuthentication(authentication);
            SecurityContextHolder.setContext(context);
            HttpSession session = httpRequest.getSession(true);
            session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, context);

            Map<String, String> data = Map.of(
                    "login", authentication.getName(),
                    "message", "로그인에 성공했습니다."
            );
            return ApiResponse.success(data);
        } catch (AuthenticationException e) {
            return ApiResponse.error("아이디 또는 비밀번호가 일치하지 않습니다.", 401);
        }
    }

    @Operation(summary = "로그아웃", description = "현재 세션을 무효화합니다.")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "로그아웃 성공")
    @PostMapping("/logout")
    public ApiResponse<String> logout(
            HttpServletRequest request,
            jakarta.servlet.http.HttpServletResponse response
    ) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        new SecurityContextLogoutHandler().logout(request, response, authentication);
        return ApiResponse.success("로그아웃 성공");
    }

    @Operation(summary = "로그인 상태 확인", description = "유효한 세션이 있으면 사용자 이메일을 반환합니다.")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "로그인 상태/미로그인 상태 반환")
    @GetMapping("/check")
    public ApiResponse<String> checkLoginStatus(@AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails != null) {
            return ApiResponse.success(userDetails.getUsername());
        }
        return ApiResponse.error("로그인 상태가 아닙니다.", 401);
    }
}
