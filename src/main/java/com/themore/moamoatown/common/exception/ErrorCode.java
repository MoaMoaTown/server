package com.themore.moamoatown.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@AllArgsConstructor
@Getter
public enum ErrorCode {

    /* code: 409 */
    NICKNAME_ALREADY_EXISTS(CONFLICT, "이미 존재하는 닉네임입니다."),
    LOGINID_ALREADY_EXISTS(CONFLICT, "이미 존재하는 아이디입니다."),

    /* code: 500 */
    DATABASE_ERROR(INTERNAL_SERVER_ERROR, "데이터베이스 오류가 발생했습니다."),
    SIGNUP_FAILED(BAD_REQUEST, "회원 가입에 실패했습니다."),
    ;

    private final HttpStatus httpStatus;
    private final String detail;
}
