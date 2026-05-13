package com.example.demo.global.common.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter // Getter를 활용해 JSON 변환
@RequiredArgsConstructor // final 필드를 인자로 받는 생성자
public class ApiResponse<T> {

    private final boolean success; // 성공 여부
    private final T data; // 응답 데이터
    private final ApiError error; // 에러

    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(true, data, null);
    }

    public static <T> ApiResponse<T> error(ApiError error) {
        return new ApiResponse<>(false, null, error);
    }

}
