package ru.sbrf.cu.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class LibraryExceptionHandler {

    @ExceptionHandler(value = NotFoundException.class)
    protected ResponseEntity notFound(RuntimeException e) {
        return ResponseEntity.notFound().build();
    }
}
