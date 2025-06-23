package com.example.demo.controller;

import com.example.demo.model.Car;
import com.example.demo.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cars")
public class CarController {

    @Autowired
    private CarService carService;

    @GetMapping
    public List<Car> getAllCars() {
        return carService.getAllCars();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Car> getCarById(@PathVariable Long id) {
        return carService.getCarById(id);
    }

    @PostMapping
    public ResponseEntity<Car> createCar(@RequestBody Car car) {
        return carService.createCar(car);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Car> updateCar(@PathVariable Long id, @RequestBody Car carDetails) {
        return carService.updateCar(id, carDetails);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCar(@PathVariable Long id) {
        return carService.deleteCar(id);
    }

    @GetMapping("/search")
    public List<Car> searchCars(
            @RequestParam(required = false) String vin,
            @RequestParam(required = false) String markModel,
            @RequestParam(required = false) String plateNum,
            @RequestParam(required = false) Integer yearManufacture) {
        return carService.searchCars(vin, markModel, plateNum, yearManufacture);
    }

    @GetMapping("/by-client/{clientId}")
    public ResponseEntity<List<Car>> getCarsByClientId(@PathVariable Long clientId) {
        return carService.getCarsByClientId(clientId);
    }
}
