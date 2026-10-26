package com.asin.PayementPorto.exception;

import com.asin.PayementPorto.dto.ErrorResponse;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleRunTimeException(RuntimeException exception){
        ErrorResponse errorResponse=new com.asin.PayementPorto.dto.ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                exception.getMessage()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }
}
