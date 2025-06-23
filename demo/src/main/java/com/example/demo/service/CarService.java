package com.example.demo.service;

import com.example.demo.model.Car;
import com.example.demo.model.Client;
import com.example.demo.repository.CarRepository;
import com.example.demo.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CarService {

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private ClientRepository clientRepository;

    public List<Car> getAllCars() {
        return carRepository.findAll();
    }

    public ResponseEntity<Car> getCarById(Long id) {
        return carRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    public ResponseEntity<Car> createCar(Car car) {
        if (car.getClient() != null) {
            Optional<Client> client = clientRepository.findById(car.getClient().getId());
            if (client.isEmpty()) {
                return ResponseEntity.badRequest().build();
            }
            car.setClient(client.get());
        }
        return ResponseEntity.ok(carRepository.save(car));
    }

    public ResponseEntity<Car> updateCar(Long id, Car carDetails) {
        Optional<Car> carOptional = carRepository.findById(id);
        if (carOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Car car = carOptional.get();

        if (carDetails.getVin() != null) {
            car.setVin(carDetails.getVin());
        }
        if (carDetails.getMarkModel() != null) {
            car.setMarkModel(carDetails.getMarkModel());
        }
        if (carDetails.getYearManufacture() != 0) {
            car.setYearManufacture(carDetails.getYearManufacture());
        }
        if (carDetails.getPlateNum() != null) {
            car.setPlateNum(carDetails.getPlateNum());
        }
        if (carDetails.getLastServiceDate() != null) {
            car.setLastServiceDate(carDetails.getLastServiceDate());
        }

        if (carDetails.getClient() != null) {
            Optional<Client> client = clientRepository.findById(carDetails.getClient().getId());
            if (client.isEmpty()) {
                return ResponseEntity.badRequest().build();
            }
            car.setClient(client.get());
        }

        return ResponseEntity.ok(carRepository.save(car));
    }

    public ResponseEntity<Void> deleteCar(Long id) {
        if (!carRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        carRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    public List<Car> searchCars(String vin, String markModel, String plateNum, Integer yearManufacture) {
        if (vin != null) {
            return carRepository.findByVinContainingIgnoreCase(vin);
        } else if (markModel != null) {
            return carRepository.findByMarkModelContainingIgnoreCase(markModel);
        } else if (plateNum != null) {
            return carRepository.findByPlateNumContainingIgnoreCase(plateNum);
        } else if (yearManufacture != null) {
            return carRepository.findByYearManufacture(yearManufacture);
        } else {
            return carRepository.findAll();
        }
    }

    public ResponseEntity<List<Car>> getCarsByClientId(Long clientId) {
        List<Car> cars = carRepository.findByClientId(clientId);
        if (cars.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(cars);
    }
}
