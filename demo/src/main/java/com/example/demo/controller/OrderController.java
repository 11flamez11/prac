package com.example.demo.controller;

import com.example.demo.model.OrderEntity;
import com.example.demo.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping
    public List<OrderEntity> getAllOrders() {
        return orderService.getAllOrders();
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderEntity> getOrderById(@PathVariable Long id) {
        return orderService.getOrderById(id);
    }

    @PostMapping
    public ResponseEntity<OrderEntity> createOrder(@RequestBody OrderEntity order) {
        return orderService.createOrder(order);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderEntity> updateOrder(@PathVariable Long id, @RequestBody OrderEntity orderDetails) {
        return orderService.updateOrder(id, orderDetails);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        return orderService.deleteOrder(id);
    }

    @GetMapping("/search")
    public List<OrderEntity> searchOrders(
            @RequestParam(required = false) String orderDate,
            @RequestParam(required = false) String completionDate,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) Double minTotalCost,
            @RequestParam(required = false) Double maxTotalCost
    ) {
        return orderService.searchOrders(orderDate, completionDate, status, minTotalCost, maxTotalCost);
    }
}
