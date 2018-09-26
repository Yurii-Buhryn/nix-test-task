package com.nix.phoneservice.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@Slf4j
@ControllerAdvice
public class ExceptionController {

    //TODO : Exception and statuses should be changed in case of real application

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> processException(RuntimeException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
}
