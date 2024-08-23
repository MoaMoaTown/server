package com.themore.moamoatown.common.aop;

import com.themore.moamoatown.common.exception.CustomException;
import com.themore.moamoatown.common.exception.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponse> handleCustomException(CustomException e) {
        log.error("CustomException occurred: {}", e.getErrorCode().getDetail());
        return ErrorResponse.toResponseEntity(e.getErrorCode());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException e) {
        log.error("IllegalArgumentException occurred: {}", e.getMessage());
        return ResponseEntity.status(BAD_REQUEST).body(
                ErrorResponse.builder()
                        .status(BAD_REQUEST.value())
                        .error(BAD_REQUEST.name())
                        .msg("부적절한 인자가 전달되었습니다.")
                        .build()
        );
    }
}

