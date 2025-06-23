package com.example.demo.repository;

import com.example.demo.model.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
    List<OrderEntity> findByOrderDate(LocalDate orderDate);
    List<OrderEntity> findByCompletionDate(LocalDate completionDate);
    List<OrderEntity> findByStatusContainingIgnoreCase(String status);
    List<OrderEntity> findByTotalCostBetween(Double min, Double max);
}