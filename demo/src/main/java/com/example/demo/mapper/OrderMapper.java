package com.example.demo.mapper;

import com.example.demo.dto.OrderDto;
import com.example.demo.model.OrderEntity;

public class OrderMapper {

    public static OrderDto toDto(OrderEntity entity) {
        OrderDto dto = new OrderDto();
        dto.setId(entity.getId());
        if (entity.getCar() != null) {
            dto.setCarId(entity.getCar().getId());
        }
        if (entity.getService() != null) {
            dto.setServiceId(entity.getService().getId());
        }
        dto.setOrderDate(entity.getOrderDate());
        dto.setCompletionDate(entity.getCompletionDate());
        dto.setStatus(entity.getStatus());
        dto.setTotalCost(entity.getTotalCost());
        return dto;
    }

    public static OrderEntity toEntity(OrderDto dto) {
        OrderEntity entity = new OrderEntity();
        entity.setId(dto.getId());
        entity.setOrderDate(dto.getOrderDate());
        entity.setCompletionDate(dto.getCompletionDate());
        entity.setStatus(dto.getStatus());
        entity.setTotalCost(dto.getTotalCost());
        return entity;
    }
}
