package com.example.demo.model;

import jakarta.persistence.*;
import jakarta.persistence.criteria.Order;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "car")
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "car_id")
    private Long id;
    @Column(unique = true)
    private String vin;

    @Column(name = "mark_model")
    private String markModel;

    @Column(name = "year_manufacture")
    private int yearManufacture;

    @Column(name = "plate_num")
    private String plateNum;

    @Column(name = "last_service_date")
    private LocalDate lastServiceDate;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "client_id",nullable = false)
    private Client client;


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

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}
