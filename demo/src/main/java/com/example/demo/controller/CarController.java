package com.example.demo.controller;

import com.example.demo.dto.CarDto;
import com.example.demo.service.CarService;
import com.example.demo.validation.OnCreate;
import com.example.demo.validation.OnUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/cars")
public class CarController {

    @Autowired
    private CarService carService;

    @GetMapping
    public List<CarDto> getAllCars() {
        return carService.getAllCars();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CarDto> getCarById(@PathVariable Long id) {
        return carService.getCarById(id);
    }

    @PostMapping
    public ResponseEntity<CarDto> createCar(@RequestBody @Validated(OnCreate.class) CarDto carDto) {
        return carService.createCar(carDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CarDto> updateCar(@PathVariable Long id, @RequestBody @Validated(OnUpdate.class) CarDto carDto) {
        return carService.updateCar(id, carDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCar(@PathVariable Long id) {
        return carService.deleteCar(id);
    }

    @GetMapping("/search")
    public List<CarDto> searchCars(
            @RequestParam(required = false) String vin,
            @RequestParam(required = false) String markModel,
            @RequestParam(required = false) String plateNum,
            @RequestParam(required = false) Integer yearManufacture) {
        return carService.searchCars(vin, markModel, plateNum, yearManufacture);
    }

    @GetMapping("/by-client/{clientId}")
    public ResponseEntity<List<CarDto>> getCarsByClientId(@PathVariable Long clientId) {
        return carService.getCarsByClientId(clientId);
    }
}
