package com.example.demo.service;

import com.example.demo.model.Car;
import com.example.demo.model.OrderEntity;
import com.example.demo.model.ServiceEntity;
import com.example.demo.repository.CarRepository;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private ServiceRepository serviceRepository;

    public List<OrderEntity> getAllOrders() {
        return orderRepository.findAll();
    }

    public ResponseEntity<OrderEntity> getOrderById(Long id) {
        return orderRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    public ResponseEntity<OrderEntity> createOrder(OrderEntity order) {
        if (order.getCar() == null || order.getService() == null) {
            return ResponseEntity.badRequest().build();
        }

        Optional<Car> car = carRepository.findById(order.getCar().getId());
        Optional<ServiceEntity> service = serviceRepository.findById(order.getService().getId());

        if (car.isEmpty() || service.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        order.setCar(car.get());
        order.setService(service.get());

        return ResponseEntity.ok(orderRepository.save(order));
    }

    public ResponseEntity<OrderEntity> updateOrder(Long id, OrderEntity orderDetails) {
        Optional<OrderEntity> orderOptional = orderRepository.findById(id);
        if (orderOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        OrderEntity order = orderOptional.get();

        if (orderDetails.getCar() != null) {
            Optional<Car> car = carRepository.findById(orderDetails.getCar().getId());
            if (car.isEmpty()) return ResponseEntity.badRequest().build();
            order.setCar(car.get());
        }

        if (orderDetails.getService() != null) {
            Optional<ServiceEntity> service = serviceRepository.findById(orderDetails.getService().getId());
            if (service.isEmpty()) return ResponseEntity.badRequest().build();
            order.setService(service.get());
        }

        if (orderDetails.getOrderDate() != null) {
            order.setOrderDate(orderDetails.getOrderDate());
        }

        if (orderDetails.getCompletionDate() != null) {
            order.setCompletionDate(orderDetails.getCompletionDate());
        }

        if (orderDetails.getStatus() != null) {
            order.setStatus(orderDetails.getStatus());
        }

        if (orderDetails.getTotalCost() != null) {
            order.setTotalCost(orderDetails.getTotalCost());
        }

        return ResponseEntity.ok(orderRepository.save(order));
    }


    public ResponseEntity<Void> deleteOrder(Long id) {
        if (!orderRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        orderRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    public List<OrderEntity> searchOrders(String orderDate, String completionDate, String status, Double minCost, Double maxCost) {
        if (orderDate != null) {
            return orderRepository.findByOrderDate(LocalDate.parse(orderDate));
        } else if (completionDate != null) {
            return orderRepository.findByCompletionDate(LocalDate.parse(completionDate));
        } else if (status != null) {
            return orderRepository.findByStatusContainingIgnoreCase(status);
        } else if (minCost != null && maxCost != null) {
            return orderRepository.findByTotalCostBetween(minCost, maxCost);
        } else {
            return orderRepository.findAll();
        }
    }
}
