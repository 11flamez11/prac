package com.example.demo.service;

import com.example.demo.dto.CarDto;
import com.example.demo.mapper.CarMapper;
import com.example.demo.model.Car;
import com.example.demo.model.Client;
import com.example.demo.repository.CarRepository;
import com.example.demo.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CarService {

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private ClientRepository clientRepository;

    public List<CarDto> getAllCars() {
        return carRepository.findAll(Sort.by(Sort.Direction.ASC, "id")).stream()
                .map(CarMapper::toDto)
                .collect(Collectors.toList());
    }

    public ResponseEntity<CarDto> getCarById(Long id) {
        return carRepository.findById(id)
                .map(car -> ResponseEntity.ok(CarMapper.toDto(car)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    public ResponseEntity<CarDto> createCar(CarDto carDto) {
        if (carDto.getClientId() == null || !clientRepository.existsById(carDto.getClientId())) {
            return ResponseEntity.badRequest().body(null);
        }

        Car car = CarMapper.toEntity(carDto);
        clientRepository.findById(carDto.getClientId()).ifPresent(car::setClient);
        Car savedCar = carRepository.save(car);
        return ResponseEntity.ok(CarMapper.toDto(savedCar));
    }

    public ResponseEntity<CarDto> updateCar(Long id, CarDto carDto) {
        Optional<Car> carOptional = carRepository.findById(id);
        if (carOptional.isPresent()) {
            Car carToUpdate = carOptional.get();

            if (carDto.getVin() != null) {
                carToUpdate.setVin(carDto.getVin());
            }
            if (carDto.getMarkModel() != null) {
                carToUpdate.setMarkModel(carDto.getMarkModel());
            }
            if (carDto.getYearManufacture() != 0) {
                carToUpdate.setYearManufacture(carDto.getYearManufacture());
            }
            if (carDto.getPlateNum() != null) {
                carToUpdate.setPlateNum(carDto.getPlateNum());
            }
            if (carDto.getLastServiceDate() != null) {
                carToUpdate.setLastServiceDate(carDto.getLastServiceDate());
            }

            if (carDto.getClientId() != null && (carToUpdate.getClient() == null || !carToUpdate.getClient().getId().equals(carDto.getClientId()))) {
                Optional<Client> clientOptional = clientRepository.findById(carDto.getClientId());
                if (clientOptional.isEmpty()) {
                    return ResponseEntity.badRequest().build();
                }
                carToUpdate.setClient(clientOptional.get());
            }

            Car updatedCar = carRepository.save(carToUpdate);
            return ResponseEntity.ok(CarMapper.toDto(updatedCar));
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    public ResponseEntity<Void> deleteCar(Long id) {
        if (carRepository.existsById(id)) {
            carRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public List<CarDto> searchCars(String vin, String markModel, String plateNum, Integer yearManufacture) {
        return carRepository.findAll().stream()
                .filter(car -> (vin == null || car.getVin().equalsIgnoreCase(vin)) &&
                        (markModel == null || car.getMarkModel().equalsIgnoreCase(markModel)) &&
                        (plateNum == null || car.getPlateNum().equalsIgnoreCase(plateNum)) &&
                        (yearManufacture == null || car.getYearManufacture() == yearManufacture))
                .map(CarMapper::toDto)
                .collect(Collectors.toList());
    }

    public ResponseEntity<List<CarDto>> getCarsByClientId(Long clientId) {
        List<CarDto> cars = carRepository.findByClientId(clientId, Sort.by(Sort.Direction.ASC, "id")).stream()
                .map(CarMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(cars);
    }
}
