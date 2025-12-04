package com.aivle.writerswalk.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 책 key

    @Column(nullable = false, length = 300)
    private String title; // 책 제목

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content; // 책 내용

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Genre genre; // 책 장르 (ENUM)

    @Column(length = 256)
    private String thumbnailUrl; // AI 표지 URL

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user; // 책 작성자

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt; // 작성일

    @UpdateTimestamp
    private LocalDateTime updatedAt; // 수정일

    public void updateThumbnailUrl(String url) {
        this.thumbnailUrl = url;
    }
}