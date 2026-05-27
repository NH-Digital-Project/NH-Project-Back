package com.example.demo.global.common.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class S3Service {

    // v2의 S3Client 주입
    private final S3Client s3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    // 파일 업로드 후 S3 URL 반환
    public String uploadFile(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            return null;
        }

        String originalFilename = file.getOriginalFilename();
        String ext = "";
        if (originalFilename != null) {
            int dotIndex = originalFilename.lastIndexOf(".");
            if (dotIndex != -1) {
                ext = originalFilename.substring(dotIndex);
            }
        }
        String storeFileName = "images/" + UUID.randomUUID().toString() + ext;

        try {
            // v2 빌더 패턴을 이용한 업로드 객체 생성
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucket)
                    .key(storeFileName)
                    .contentType(file.getContentType())
                    .build();

            // RequestBody 형식을 통해 인풋 스트림 전달
            s3Client.putObject(putObjectRequest,
                    RequestBody.fromInputStream(file.getInputStream(), file.getSize()));

            // v2 표준 엔드포인트 URL 추출 방식 적용
            return s3Client.utilities().getUrl(builder -> builder.bucket(bucket).key(storeFileName)).toString();
        } catch (IOException e) {
            throw new com.example.demo.global.exception.CustomException(com.example.demo.global.exception.ErrorCode.INTERNAL_ERROR);
        }
    }

    // 파일 삭제
    public void deleteFile(String fileUrl) {
        if (fileUrl == null || fileUrl.isBlank()) {
            return;
        }
        try {
            String splitStr = ".com/";
            int index = fileUrl.lastIndexOf(splitStr);
            if (index != -1) {
                String fileName = fileUrl.substring(index + splitStr.length());

                // v2 빌더 패턴을 이용한 삭제 요청
                DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
                        .bucket(bucket)
                        .key(fileName)
                        .build();

                s3Client.deleteObject(deleteObjectRequest);
            }
        } catch (Exception e) {
            // 삭제 실패 예외 처리 로직 생략
        }
    }
}