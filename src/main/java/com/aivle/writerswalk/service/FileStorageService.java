package com.aivle.writerswalk.service;

import com.aivle.writerswalk.exception.CustomException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class FileStorageService {

    private final String uploadDir = "data/images";

    /**
     * DALL-E URL에서 이미지를 다운로드하고 로컬 서버에 저장합니다.
     * @param dalleImageUrl DALL-E에서 생성된 임시 URL
     * @return 서버에 저장된 이미지의 파일 이름 (예: UUID.png)
     */
    public String storeFileFromUrl(String dalleImageUrl) {
        if (dalleImageUrl == null || dalleImageUrl.isEmpty()) {
            return null;
        }

        try {
            Path uploadPath = Paths.get(uploadDir).toAbsolutePath().normalize();
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            String fileExtension = ".png";
            String fileName = UUID.randomUUID() + fileExtension;
            Path targetLocation = uploadPath.resolve(fileName);

            URL url = new URL(dalleImageUrl);
            try (InputStream inputStream = url.openStream()) {
                Files.copy(inputStream, targetLocation, StandardCopyOption.REPLACE_EXISTING);
            }

            log.info("이미지 저장 성공: {}", uploadPath + "/" + fileName);
            return fileName;

        } catch (Exception ex) {
            log.error("파일 다운로드 및 저장 중 오류 발생: {}", ex.getMessage());
            throw new CustomException(
                    "DALL-E 이미지 저장에 실패했습니다. (URL 접근 또는 서버 파일 시스템 오류)",
                    ex,
                    HttpStatus.INTERNAL_SERVER_ERROR
            );}
    }
}
