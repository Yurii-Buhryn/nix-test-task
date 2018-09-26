package com.nix.orderservice.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

//TODO : Exception handling should be done in better way this is just for demo

@Slf4j
@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> processException(RuntimeException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
}
