package com.example.demo.dto;

import com.example.demo.validation.OnCreate;
import com.example.demo.validation.OnUpdate;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

public class CarDto {

    @NotNull(groups = OnUpdate.class, message = "ID обязателен при обновлении")
    private Long id;

    @NotBlank(groups = {OnCreate.class}, message = "VIN не должен быть пустым")
    @Size(max = 17, groups = {OnCreate.class, OnUpdate.class}, message = "VIN не должен превышать 17 символов")
    private String vin;

    @NotBlank(groups = {OnCreate.class}, message = "Марка и модель не должны быть пустыми")
    @Size(max = 100, groups = {OnCreate.class, OnUpdate.class}, message = "Марка и модель не должны превышать 100 символов")
    private String markModel;

    @Min(value = 1886, groups = {OnCreate.class, OnUpdate.class}, message = "Год выпуска не может быть раньше 1886")
    @Max(value = 2100, groups = {OnCreate.class, OnUpdate.class}, message = "Год выпуска не может быть позже 2100")
    private int yearManufacture;

    @NotBlank(groups = {OnCreate.class}, message = "Номерной знак не должен быть пустым")
    @Size(max = 15, groups = {OnCreate.class, OnUpdate.class}, message = "Номерной знак не должен превышать 15 символов")
    private String plateNum;

    private LocalDate lastServiceDate;

    @NotNull(groups = OnCreate.class, message = "ID клиента обязателен при создании")
    private Long clientId;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public String getMarkModel() {
        return markModel;
    }

    public void setMarkModel(String markModel) {
        this.markModel = markModel;
    }

    public int getYearManufacture() {
        return yearManufacture;
    }

    public void setYearManufacture(int yearManufacture) {
        this.yearManufacture = yearManufacture;
    }

    public String getPlateNum() {
        return plateNum;
    }

    public void setPlateNum(String plateNum) {
        this.plateNum = plateNum;
    }

    public LocalDate getLastServiceDate() {
        return lastServiceDate;
    }

    public void setLastServiceDate(LocalDate lastServiceDate) {
        this.lastServiceDate = lastServiceDate;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }
}
