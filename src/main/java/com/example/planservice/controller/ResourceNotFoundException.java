package com.example.planservice.controller;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResourceNotFoundException extends RuntimeException{
    private String errorCode;
    private String errorMessage;
}
