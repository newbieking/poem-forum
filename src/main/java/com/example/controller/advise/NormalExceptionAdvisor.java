package com.example.controller.advise;

import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@ControllerAdvice
public class NormalExceptionAdvisor {


    @ExceptionHandler({
            IllegalArgumentException.class,
            IllegalStateException.class,
            ServletRequestBindingException.class})
    String handler() {
        return "400";
    }

    @ExceptionHandler(NoResourceFoundException.class)
    String NoFoundHandler() {
        return "404";
    }

}
