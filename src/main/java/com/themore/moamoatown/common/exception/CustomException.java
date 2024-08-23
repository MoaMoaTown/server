package com.themore.moamoatown.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CustomException extends RuntimeException {

    // 전달할 에러 코드
    private final ErrorCode errorCode;
}
