package com.example.demo.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    // 공통
    INVALID_PAGE(HttpStatus.BAD_REQUEST, "유효하지 않은 페이지 번호입니다."),

    // Auth

    // User
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 사용자입니다."),


    // Admin
    DUPLICATED_LOGIN_ID(HttpStatus.CONFLICT, "중복된 관리자 아이디가 있습니다."),
    ADMIN_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 관리자입니다."),
    ADMIN_UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "존재하지 않는 관리자입니다."),
    INVALID_PASSWORD(HttpStatus.UNAUTHORIZED, "비밀번호가 올바르지 않습니다."),

    // Common
    INVALID_REQUEST(HttpStatus.BAD_REQUEST, "입력값이 올바르지 않습니다."),
    INTERNAL_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류가 발생했습니다."),

    // Application
    APPLICATION_ALREADY_EXISTS(HttpStatus.CONFLICT, "이미 지원서가 존재합니다."),
    APPLICATION_NOT_FOUND(HttpStatus.NOT_FOUND, "지원서를 찾을 수 없습니다."),
    APPLICATION_NOT_DELETABLE(HttpStatus.CONFLICT, "제출 상태의 지원서만 취소할 수 있습니다."),
    INVALID_APPLICATION_PERIOD(HttpStatus.FORBIDDEN, "현재 사업 신청 기간이 아닙니다."),
    INVALID_BIRTH_DATE(HttpStatus.BAD_REQUEST, "유효하지 않은 생년월일입니다."),
    INVALID_CANCEL_PERIOD(HttpStatus.FORBIDDEN, "지원 취소는 마감 1시간 전까지만 가능합니다."),
    INVALID_APPLICATION_STATUS(HttpStatus.BAD_REQUEST, "SUBMITTED 상태의 지원서만 등록할 수 있습니다."),

    // Project
    PROJECT_ALREADY_EXISTS(HttpStatus.CONFLICT, "이미 선정업체로 등록된 지원서입니다."),
    PROJECT_NOT_FOUND(HttpStatus.NOT_FOUND, "선정업체를 찾을 수 없습니다.");

    private final HttpStatus status;
    private final String message;
}
