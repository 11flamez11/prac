package com.example.demo.service;

import com.example.demo.model.ServiceEntity;
import com.example.demo.repository.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ServiceService {

    @Autowired
    private ServiceRepository serviceRepository;

    public List<ServiceEntity> getAllServices() {
        return serviceRepository.findAll();
    }

    public ResponseEntity<ServiceEntity> getServiceById(Long id) {
        return serviceRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    public ServiceEntity createService(ServiceEntity service) {
        return serviceRepository.save(service);
    }

    public ResponseEntity<ServiceEntity> updateService(Long id, ServiceEntity serviceDetails) {
        Optional<ServiceEntity> serviceOptional = serviceRepository.findById(id);
        if (serviceOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        ServiceEntity service = serviceOptional.get();

        if (serviceDetails.getName() != null) {
            service.setName(serviceDetails.getName());
        }

        if (serviceDetails.getDescription() != null) {
            service.setDescription(serviceDetails.getDescription());
        }

        if (serviceDetails.getPrice() != null) {
            service.setPrice(serviceDetails.getPrice());
        }

        return ResponseEntity.ok(serviceRepository.save(service));
    }


    public ResponseEntity<Void> deleteService(Long id) {
        if (!serviceRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        serviceRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    public List<ServiceEntity> searchServices(String name, String description, Double price) {
        if (name != null) {
            return serviceRepository.findByNameContainingIgnoreCase(name);
        } else if (description != null) {
            return serviceRepository.findByDescriptionContainingIgnoreCase(description);
        } else if (price != null) {
            return serviceRepository.findByPrice(price);
        } else {
            return serviceRepository.findAll();
        }
    }
}
