package com.kano.springbootmongoclothesapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

import com.kano.springbootmongoclothesapi.common.ApiResponse;
import org.springframework.web.bind.annotation.ResponseStatus;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    //验证错误
    public ApiResponse<Object> handleValidationExceptions(MethodArgumentNotValidException ex) {
        return new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "参数错误", null);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Object>> handleException(Exception e) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        ApiResponse<Object> response = new ApiResponse<>(status.value(), e.getMessage(), null);
        return new ResponseEntity<>(response, status);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse<Object> handleIllegalArgumentException(IllegalArgumentException e) {
        return new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), e.getMessage(), null);
    }
}