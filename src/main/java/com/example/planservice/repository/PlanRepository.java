package com.example.planservice.repository;

import com.example.planservice.entity.Plan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlanRepository extends JpaRepository<Plan,Long> {

    List<Plan> findByProductName(String productName);
    List<Plan> findByProductOwner(String productOwner);
    List<Plan> findByProductNameAndProductOwner(String productName, String productOwner);
}
