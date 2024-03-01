package com.ivanfang.fangtimes.exception;

import com.ivanfang.fangtimes.pojo.Result;
import jakarta.validation.ConstraintViolationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class MyExceptionHandler {

    @ExceptionHandler
    public Result handleException(Exception e) {
        e.printStackTrace();
        return Result.failed(e.getMessage());
    }

}
