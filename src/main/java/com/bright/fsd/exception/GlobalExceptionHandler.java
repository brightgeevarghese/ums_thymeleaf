package com.bright.fsd.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public String handleMethodArgumentNotValidException(MethodArgumentNotValidException e, Model model) {
        model.addAttribute("errors", e.getBindingResult().getAllErrors().stream().map(
                fieldError -> fieldError.getDefaultMessage() + ", " + fieldError.getObjectName()
        ));
        return "error";
    }
}
