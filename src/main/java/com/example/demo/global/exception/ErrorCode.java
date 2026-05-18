package com.example.demo.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    // Auth

    // User
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 사용자입니다."),


    // Admin
    DUPLICATED_LOGIN_ID(HttpStatus.CONFLICT, "중복된 관리자 아이디가 있습니다."),
    ADMIN_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 관리자입니다."),

    // Common
    INVALID_REQUEST(HttpStatus.BAD_REQUEST, "입력값이 올바르지 않습니다."),
    INTERNAL_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류가 발생했습니다."),

    // Application
    APPLICATION_ALREADY_EXISTS(HttpStatus.CONFLICT, "이미 지원서가 존재합니다."),
    APPLICATION_NOT_FOUND(HttpStatus.NOT_FOUND, "지원서를 찾을 수 없습니다."),
    APPLICATION_NOT_DELETABLE(HttpStatus.CONFLICT, "승인된 지원서는 철회할 수 없습니다."),
    INVALID_APPLICATION_PERIOD(HttpStatus.FORBIDDEN, "현재 사업 신청 기간이 아닙니다."),
    INVALID_BIRTH_DATE(HttpStatus.BAD_REQUEST, "유효하지 않은 생년월일입니다.");

    private final HttpStatus status;
    private final String message;
}
