package com.example.demo.dto;
import com.example.demo.validation.OnCreate;
import com.example.demo.validation.OnUpdate;
import jakarta.validation.constraints.*;
import java.time.LocalDate;

public class OrderDto {

    @NotNull(groups = OnUpdate.class, message = "ID обязателен для обновления")
    private Long id;

    @NotNull(groups = {OnCreate.class}, message = "ID машины обязателен")
    private Long carId;

    @NotNull(groups = {OnCreate.class}, message = "ID услуги обязателен")
    private Long serviceId;

    @NotNull(groups = {OnCreate.class}, message = "Дата заказа обязательна")
    private LocalDate orderDate;

    private LocalDate completionDate;

    private String status;

    private Double totalCost;

    public interface Create {}
    public interface Update {}

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public Long getCarId() {
        return carId;
    }
    public void setCarId(Long carId) {
        this.carId = carId;
    }

    public Long getServiceId() {
        return serviceId;
    }
    public void setServiceId(Long serviceId) {
        this.serviceId = serviceId;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }
    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public LocalDate getCompletionDate() {
        return completionDate;
    }
    public void setCompletionDate(LocalDate completionDate) {
        this.completionDate = completionDate;
    }

    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }

    public Double getTotalCost() {
        return totalCost;
    }
    public void setTotalCost(Double totalCost) {
        this.totalCost = totalCost;
    }
}
