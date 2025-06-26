package com.example.demo.repository;

import com.example.demo.model.Car;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Sort;

public interface CarRepository extends JpaRepository<Car, Long> {

    List<Car> findByVinContainingIgnoreCase(String vin);

    List<Car> findByMarkModelContainingIgnoreCase(String markModel);

    List<Car> findByPlateNumContainingIgnoreCase(String plateNum);

    List<Car> findByYearManufacture(int yearManufacture);
    List<Car> findByLastServiceDate(LocalDate lastServiceDate);

    default List<Car> findAllSortedById() {
        return findAll(Sort.by(Sort.Direction.ASC, "id"));
    }
    default List<Car> findByClientIdSorted(Long clientId) {
        return findByClientId(clientId, Sort.by(Sort.Direction.ASC, "id"));
    }

    List<Car> findByClientId(Long clientId, Sort sort);
}
