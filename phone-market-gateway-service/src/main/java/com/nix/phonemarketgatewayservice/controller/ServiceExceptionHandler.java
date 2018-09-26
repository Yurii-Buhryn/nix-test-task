package com.nix.phonemarketgatewayservice.controller;

import com.netflix.hystrix.exception.HystrixRuntimeException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ServiceExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(HystrixRuntimeException.class)
    @ResponseBody
    ResponseEntity<String> handleControllerException(HttpServletRequest req, Throwable ex) {
        if (ex instanceof HystrixRuntimeException) {
            HttpStatusCodeException exc = (HttpStatusCodeException) ex.getCause();
            return new ResponseEntity<>(exc.getResponseBodyAsString(), exc.getStatusCode());
        }
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}


