package com.example.planservice.controller;

import com.example.planservice.model.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.stream.Collectors;

import static com.example.planservice.constants.PlanConstants.BAD_REQUEST_001;
import static com.example.planservice.constants.PlanConstants.ERROR_MESSAGE;

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

    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e){
        return new ResponseEntity(ErrorResponse
                .builder()
                .errorCode(BAD_REQUEST_001)
                .errorMessage(ERROR_MESSAGE)
                .errorFieldsList(e.getBindingResult().getFieldErrors().stream().map(fe -> fe.getDefaultMessage()).collect(Collectors.toList()))
                .build(), HttpStatus.BAD_REQUEST);
    }
}
