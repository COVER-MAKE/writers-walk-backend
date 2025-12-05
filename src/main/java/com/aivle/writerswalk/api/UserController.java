package com.aivle.writerswalk.api;

import com.aivle.writerswalk.ApiResponse;
import com.aivle.writerswalk.domain.Book;
import com.aivle.writerswalk.domain.User;
import com.aivle.writerswalk.dto.book.BookResponse;
import com.aivle.writerswalk.dto.user.UserBooksResponse;
import com.aivle.writerswalk.dto.user.UserResponse;
import com.aivle.writerswalk.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Tag(name = "User", description = "Mypage관련 API")
public class UserController {

    private final UserService userService;

    @GetMapping("/me")
    @Operation(summary = "내 정보 조회", description = "로그인한 사용자의 기본 정보를 조회합니다.")
    public ApiResponse<UserResponse> getMyPage(@AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.getCurrentUserProfile(userDetails.getUsername());
        return ApiResponse.success(new UserResponse(user));
    }

    @GetMapping("/me/books")
    @Operation(summary = "내가 작성한 책 목록 조회", description = "로그인한 사용자가 작성한 책 목록을 조회합니다.")
    public ApiResponse<UserBooksResponse> getMyBooks(@AuthenticationPrincipal UserDetails userDetails) {
        List<Book> books = userService.getCurrentUserBooks(userDetails.getUsername());
        List<BookResponse> bookResponses = books.stream().map(BookResponse::new).toList();
        return ApiResponse.success(new UserBooksResponse(bookResponses));
    }
}
