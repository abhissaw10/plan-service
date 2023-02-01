package com.example.planservice.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;

@Builder
@Data
public class Plan {
    @Id
    private String id;
    private String goal;
    private String productName;
    private String productOwner;
    private String financialYear;
}
