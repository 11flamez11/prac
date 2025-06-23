package com.example.demo.controller;

import com.example.demo.model.ServiceEntity;
import com.example.demo.service.ServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/services")
public class ServiceController {

    @Autowired
    private ServiceService serviceService;

    @GetMapping
    public List<ServiceEntity> getAllServices() {
        return serviceService.getAllServices();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServiceEntity> getServiceById(@PathVariable Long id) {
        return serviceService.getServiceById(id);
    }

    @PostMapping
    public ServiceEntity createService(@RequestBody ServiceEntity service) {
        return serviceService.createService(service);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ServiceEntity> updateService(@PathVariable Long id, @RequestBody ServiceEntity serviceDetails) {
        return serviceService.updateService(id, serviceDetails);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteService(@PathVariable Long id) {
        return serviceService.deleteService(id);
    }

    @GetMapping("/search")
    public List<ServiceEntity> searchServices(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String description,
            @RequestParam(required = false) Double price
    ) {
        return serviceService.searchServices(name, description, price);
    }
}
