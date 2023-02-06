package com.example.planservice.repository;

import com.example.planservice.model.Initiative;
import org.springframework.data.repository.CrudRepository;

public interface InitiativeRepository extends CrudRepository<Initiative,Long> {
}
