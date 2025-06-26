package com.example.demo.service;

import com.example.demo.dto.OrderDto;
import com.example.demo.mapper.OrderMapper;
import com.example.demo.model.Car;
import com.example.demo.model.Client;
import com.example.demo.model.OrderEntity;
import com.example.demo.model.ServiceEntity;
import com.example.demo.repository.CarRepository;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Sort;
import java.time.LocalDate;
import java.util.Collections;
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


    public List<OrderDto> getAllOrders() {
        return orderRepository.findAll(Sort.by(Sort.Direction.ASC, "id"))
                .stream()
                .map(OrderMapper::toDto)
                .toList();
    }

    public ResponseEntity<OrderDto> getOrderById(Long id) {
        Optional<OrderEntity> orderOpt = orderRepository.findById(id);
        if (orderOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(OrderMapper.toDto(orderOpt.get()));
    }

    public ResponseEntity<OrderDto> createOrder(OrderDto orderDto) {
        if (orderDto.getCarId() == null || orderDto.getServiceId() == null) {
            return ResponseEntity.badRequest().build();
        }

        Optional<Car> carOpt = carRepository.findById(orderDto.getCarId());
        Optional<ServiceEntity> serviceOpt = serviceRepository.findById(orderDto.getServiceId());

        if (carOpt.isEmpty() || serviceOpt.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        OrderEntity order = OrderMapper.toEntity(orderDto);
        order.setCar(carOpt.get());
        order.setService(serviceOpt.get());

        OrderEntity savedOrder = orderRepository.save(order);
        return ResponseEntity.ok(OrderMapper.toDto(savedOrder));
    }

    public ResponseEntity<OrderDto> updateOrder(Long id, OrderDto orderDto) {
        Optional<OrderEntity> orderOpt = orderRepository.findById(id);
        if (orderOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        OrderEntity order = orderOpt.get();

        if (orderDto.getCarId() != null) {
            Optional<Car> carOpt = carRepository.findById(orderDto.getCarId());
            if (carOpt.isEmpty()) return ResponseEntity.badRequest().build();
            order.setCar(carOpt.get());
        }

        if (orderDto.getServiceId() != null) {
            Optional<ServiceEntity> serviceOpt = serviceRepository.findById(orderDto.getServiceId());
            if (serviceOpt.isEmpty()) return ResponseEntity.badRequest().build();
            order.setService(serviceOpt.get());
        }

        if (orderDto.getOrderDate() != null) {
            order.setOrderDate(orderDto.getOrderDate());
        }

        if (orderDto.getCompletionDate() != null) {
            order.setCompletionDate(orderDto.getCompletionDate());
        }

        if (orderDto.getStatus() != null) {
            order.setStatus(orderDto.getStatus());
        }

        if (orderDto.getTotalCost() != null) {
            order.setTotalCost(orderDto.getTotalCost());
        }

        OrderEntity updatedOrder = orderRepository.save(order);
        return ResponseEntity.ok(OrderMapper.toDto(updatedOrder));
    }

    public ResponseEntity<Void> deleteOrder(Long id) {
        if (!orderRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        orderRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    public List<OrderDto> searchOrders(Long orderId, String orderDate, String completionDate, String status, Double minCost, Double maxCost) {
        List<OrderEntity> orders;
        if (orderId != null) {
            Optional<OrderEntity> orderOpt = orderRepository.findById(orderId);
            if (orderOpt.isPresent()) {
                orders = List.of(orderOpt.get());
            } else {
                orders = Collections.emptyList();
            }
        }else if (orderDate != null) {
            orders = orderRepository.findByOrderDate(LocalDate.parse(orderDate));
        } else if (completionDate != null) {
            orders = orderRepository.findByCompletionDate(LocalDate.parse(completionDate));
        } else if (status != null) {
            orders = orderRepository.findByStatusContainingIgnoreCase(status);
        } else if (minCost != null && maxCost != null) {
            orders = orderRepository.findByTotalCostBetween(minCost, maxCost);
        } else {
            orders = orderRepository.findAll();
        }

        return orders.stream()
                .map(OrderMapper::toDto)
                .toList();
    }

}
