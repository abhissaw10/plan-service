package com.example.planservice.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ErrorResponse {
    private String errorCode;
    private String errorMessage;
    private List<String> errorFieldsList;
}
