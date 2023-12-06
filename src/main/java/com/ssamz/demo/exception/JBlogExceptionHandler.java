package com.ssamz.demo.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@ControllerAdvice
@RestController
public class JBlogExceptionHandler {
    @ExceptionHandler(value = Exception.class)
    public String globalExceptionHandler(Exception e) {
        return "<h1>" + e.getMessage() + "<h1>";
    }
}
