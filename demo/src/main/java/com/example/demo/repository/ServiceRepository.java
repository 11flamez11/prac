package com.example.demo.repository;

import com.example.demo.model.ServiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ServiceRepository extends JpaRepository<ServiceEntity, Long> {
    List<ServiceEntity> findByNameContainingIgnoreCase(String name);
    List<ServiceEntity> findByDescriptionContainingIgnoreCase(String description);
    List<ServiceEntity> findByPrice(Double price);
}