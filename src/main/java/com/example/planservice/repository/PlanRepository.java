package com.example.planservice.repository;

import com.example.planservice.entity.Plan;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlanRepository extends MongoRepository<Plan,String> {

    List<Plan> findByProductName(String productName);
    List<Plan> findByProductOwner(String productOwner);
    List<Plan> findByProductNameAndProductOwner(String productName, String productOwner);
}
