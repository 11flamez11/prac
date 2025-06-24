package com.example.demo.mapper;

import com.example.demo.dto.CarDto;
import com.example.demo.model.Car;
import com.example.demo.model.Client;

public class CarMapper {

    public static CarDto toDto(Car car) {
        CarDto dto = new CarDto();
        dto.setId(car.getId());
        dto.setVin(car.getVin());
        dto.setMarkModel(car.getMarkModel());
        dto.setYearManufacture(car.getYearManufacture());
        dto.setPlateNum(car.getPlateNum());
        dto.setLastServiceDate(car.getLastServiceDate());
        if (car.getClient() != null) {
            dto.setClientId(car.getClient().getId());
        }
        return dto;
    }


    public static Car toEntity(CarDto dto) {
        Car car = new Car();
        car.setId(dto.getId());
        car.setVin(dto.getVin());
        car.setMarkModel(dto.getMarkModel());
        car.setYearManufacture(dto.getYearManufacture());
        car.setPlateNum(dto.getPlateNum());
        car.setLastServiceDate(dto.getLastServiceDate());
        return car;
    }

}
