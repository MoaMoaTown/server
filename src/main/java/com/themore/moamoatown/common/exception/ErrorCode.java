package com.themore.moamoatown.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@AllArgsConstructor
@Getter
public enum ErrorCode {

    /* code: 401 or 400 or 404 */
    LOGIN_FAILED(UNAUTHORIZED, "로그인에 실패했습니다. 아이디와 비밀번호를 확인해주세요."),
    TOWN_NOT_FOUND(NOT_FOUND, "타운을 찾을 수 없습니다."),

    /* code: 409 */
    NICKNAME_ALREADY_EXISTS(CONFLICT, "이미 존재하는 닉네임입니다."),
    LOGINID_ALREADY_EXISTS(CONFLICT, "이미 존재하는 아이디입니다."),

    /* code: 500 */
    DATABASE_ERROR(INTERNAL_SERVER_ERROR, "데이터베이스 오류가 발생했습니다."),
    SIGNUP_FAILED(BAD_REQUEST, "회원 가입에 실패했습니다."),
    TOWN_CREATE_FAILED(BAD_REQUEST, "타운 만들기에 실패했습니다.")
    ;

    private final HttpStatus httpStatus;
    private final String detail;
}
