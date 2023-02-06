package com.example.planservice.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Builder
@Data
@RedisHash("Initiative")
public class Initiative implements Serializable {
    @Id
    private Long id;
    private String title;
    private String successCriteria;
    private LocalDate startDate;
    private LocalDate endDate;
    private String status;
    private Integer jiraId;
    private List<Initiative> dependencies;

}
