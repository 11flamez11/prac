package com.example.demo.repository;

import com.example.demo.model.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;


public interface CarRepository extends JpaRepository<Car, Long> {

    List<Car> findByVinContainingIgnoreCase(String vin);

    List<Car> findByMarkModelContainingIgnoreCase(String markModel);

    List<Car> findByPlateNumContainingIgnoreCase(String plateNum);

    List<Car> findByYearManufacture(int yearManufacture);

    List<Car> findByClientId(Long clientId);

}
