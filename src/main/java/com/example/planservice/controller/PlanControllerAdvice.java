package com.example.planservice.controller;

import com.example.planservice.model.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice(basePackageClasses = {PlanController.class,GoalController.class})
public class PlanControllerAdvice {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFoundException(ResourceNotFoundException r){
        return new ResponseEntity<>( ErrorResponse
                .builder()
                .errorCode(r.getErrorCode())
                .errorMessage(r.getErrorMessage())
                .build(), HttpStatus.NOT_FOUND);
    }
}
