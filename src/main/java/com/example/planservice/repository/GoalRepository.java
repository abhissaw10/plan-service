package com.example.planservice.repository;

import com.example.planservice.entity.Goal;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface GoalRepository extends MongoRepository<Goal,String> {

    List<Goal> findByName(String name);
}
