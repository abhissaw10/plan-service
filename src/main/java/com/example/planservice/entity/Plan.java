package com.example.planservice.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.stereotype.Repository;

import java.util.List;

@Builder
@Data
@Repository
@AllArgsConstructor
@NoArgsConstructor
public class Plan {
    @Id
    private String id;
    private List<String> goals;
    private String productName;
    private String productOwner;
    private String financialYear;
}
