package com.example.demo.repository;

import com.example.demo.model.ServiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;
import java.util.List;
import org.springframework.data.domain.Sort;

@Repository


public interface ServiceRepository extends JpaRepository<ServiceEntity, Long> {
    List<ServiceEntity> findByNameContainingIgnoreCase(String name, Sort sort);
    List<ServiceEntity> findByDescriptionContainingIgnoreCase(String description, Sort sort);
    List<ServiceEntity> findByPrice(Double price, Sort sort);
}