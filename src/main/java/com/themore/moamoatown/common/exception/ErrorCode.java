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
    BALANCE_NOT_FOUND(NOT_FOUND, "재산 정보를 찾을 수 없습니다."),
    DESCRIPTION_NOT_FOUND(NOT_FOUND, "단어 설명을 찾을 수 없습니다."),
    KNOWLEDGE_NOT_FOUND(NOT_FOUND, "지식을 찾을 수 없습니다."),
    UNAUTHORIZED_ERROR(UNAUTHORIZED, "접근 권한이 없습니다."),
    NO_ACCOUNTS_FOUND(NOT_FOUND, "해당 멤버의 계좌 정보를 찾을 수 없습니다."),

    /* code: 409 */
    NICKNAME_ALREADY_EXISTS(CONFLICT, "이미 존재하는 닉네임입니다."),
    LOGINID_ALREADY_EXISTS(CONFLICT, "이미 존재하는 아이디입니다."),

    /* code: 500 */
    DATABASE_ERROR(INTERNAL_SERVER_ERROR, "데이터베이스 오류가 발생했습니다."),
    SIGNUP_FAILED(BAD_REQUEST, "회원 가입에 실패했습니다."),
    TOWN_CREATE_FAILED(BAD_REQUEST, "타운 만들기에 실패했습니다."),
    WORD_INSERT_FAILED(INTERNAL_SERVER_ERROR, "단어 삽입에 실패했습니다."),
    GPT_REQUEST_FAILED(INTERNAL_SERVER_ERROR, "GPT 요청에 실패했습니다."),
    UPDATE_PROFILE_FAILED(INTERNAL_SERVER_ERROR, "프로필 변경에 실패했습니다."),
    JOB_CREATE_FAILED(BAD_REQUEST, "역할 만들기에 실패했습니다."),
    WISH_INSERT_FAILED(BAD_REQUEST,"잔액이 부족합니다"),
    CLOTH_INSERT_FAILED(BAD_REQUEST,"잔액이 부족합니다"),
    JOB_APPLY_INSERT_FAILED(BAD_REQUEST,"역할 요청에 실패했습니다"),
    JOB_REQUEST_ALLOW_FAILED(BAD_REQUEST, "역할 선정에 실패했습니다."),
    UPDATE_FAILED(INTERNAL_SERVER_ERROR, "데이터 업데이트에 실패했습니다."),
    INVESTMENT_INSERT_FAILED(INTERNAL_SERVER_ERROR, "기본 투자 데이터 삽입에 실패했습니다."),
    ACCOUNT_INSERT_FAILED(INTERNAL_SERVER_ERROR, "기본 계좌 데이터 삽입에 실패했습니다."),
    QUEST_INSERT_FAILED(INTERNAL_SERVER_ERROR, "퀘스트 데이터를 삽입하는 데 실패했습니다."),
    WISH_CREATE_FAILED(BAD_REQUEST, "역할 만들기에 실패했습니다."),
    WISH_DELETE_FAILED(INTERNAL_SERVER_ERROR, "위시 삭제에 실패했습니다."),
    WISH_COMPLETE_FAILED(INTERNAL_SERVER_ERROR, "위시 상품 완료 처리에 실패했습니다."),
    QUEST_CREATE_FAILED(BAD_REQUEST, "퀘스트 만들기에 실패했습니다."),
    QUEST_SELECTED_FAILED(INTERNAL_SERVER_ERROR, "퀘스트 담당자 선정에 실패했습니다."),
    QUEST_COMPLETE_FAILED(INTERNAL_SERVER_ERROR, "퀘스트 완료 처리에 실패했습니다.")
    ;

    private final HttpStatus httpStatus;
    private final String detail;
}
