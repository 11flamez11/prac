package com.example.demo.service;

import com.example.demo.dto.ServiceDto;
import com.example.demo.mapper.ServiceMapper;
import com.example.demo.model.ServiceEntity;
import com.example.demo.repository.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ServiceService {

    @Autowired
    private ServiceRepository serviceRepository;

    public List<ServiceDto> getAllServices() {
        return serviceRepository.findAll().stream()
                .map(ServiceMapper::toDto)
                .collect(Collectors.toList());
    }

    public ResponseEntity<ServiceDto> getServiceById(Long id) {
        return serviceRepository.findById(id)
                .map(service -> ResponseEntity.ok(ServiceMapper.toDto(service)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    public ServiceDto createService(ServiceDto serviceDto) {
        ServiceEntity service = ServiceMapper.toEntity(serviceDto);
        ServiceEntity savedService = serviceRepository.save(service);
        return ServiceMapper.toDto(savedService);
    }

    public ResponseEntity<ServiceDto> updateService(Long id, ServiceDto serviceDto) {
        Optional<ServiceEntity> serviceOptional = serviceRepository.findById(id);
        if (serviceOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        ServiceEntity existingService = serviceOptional.get();

        if (serviceDto.getName() != null) {
            existingService.setName(serviceDto.getName());
        }

        if (serviceDto.getDescription() != null) {
            existingService.setDescription(serviceDto.getDescription());
        }

        if (serviceDto.getPrice() != null) {
            existingService.setPrice(serviceDto.getPrice());
        }

        ServiceEntity updatedService = serviceRepository.save(existingService);

        ServiceDto updatedDto = new ServiceDto();
        updatedDto.setId(updatedService.getId());
        updatedDto.setName(updatedService.getName());
        updatedDto.setDescription(updatedService.getDescription());
        updatedDto.setPrice(updatedService.getPrice());

        return ResponseEntity.ok(updatedDto);
    }


    public ResponseEntity<Void> deleteService(Long id) {
        if (!serviceRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        serviceRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    public List<ServiceDto> searchServices(String name, String description, Double price) {
        List<ServiceEntity> results;

        if (name != null) {
            results = serviceRepository.findByNameContainingIgnoreCase(name);
        } else if (description != null) {
            results = serviceRepository.findByDescriptionContainingIgnoreCase(description);
        } else if (price != null) {
            results = serviceRepository.findByPrice(price);
        } else {
            results = serviceRepository.findAll();
        }

        return results.stream()
                .map(ServiceMapper::toDto)
                .collect(Collectors.toList());
    }
}
