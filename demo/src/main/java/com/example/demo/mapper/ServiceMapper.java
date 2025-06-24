package com.example.demo.mapper;

import com.example.demo.dto.ServiceDto;
import com.example.demo.model.ServiceEntity;

public class ServiceMapper {

    public static ServiceDto toDto(ServiceEntity entity) {
        if (entity == null) return null;
        ServiceDto dto = new ServiceDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());
        dto.setPrice(entity.getPrice());
        return dto;
    }

    public static ServiceEntity toEntity(ServiceDto dto) {
        if (dto == null) return null;
        ServiceEntity entity = new ServiceEntity();
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setPrice(dto.getPrice());
        return entity;
    }
}
