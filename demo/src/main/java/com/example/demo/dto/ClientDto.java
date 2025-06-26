package com.example.demo.dto;

import com.example.demo.validation.OnCreate;
import com.example.demo.validation.OnUpdate;
import jakarta.persistence.Column;
import jakarta.validation.constraints.*;

import java.util.List;

public class ClientDto {

    private List <CarDto> cars;
    @NotNull(groups = OnUpdate.class, message = "ID обязателен при обновлении")
    private Long id;

    @NotBlank(groups = {OnCreate.class, OnUpdate.class}, message = "Имя не должно быть пустым")
    @Size(max = 100, groups = {OnCreate.class, OnUpdate.class}, message = "Имя не должно превышать 100 символов")
    private String name;

    @Pattern(
            regexp = "^\\+?[0-9]{10,15}$",
            groups = {OnCreate.class, OnUpdate.class},
            message = "Телефон должен содержать от 10 до 15 цифр"
    )
    private String phone;

    @Email(groups = {OnCreate.class, OnUpdate.class}, message = "Email должен быть валидным")
    @NotBlank(groups = {OnCreate.class}, message = "Email обязателен при создании")
    private String email;

    @Size(max = 255, groups = {OnCreate.class, OnUpdate.class}, message = "Адрес не должен превышать 255 символов")
    private String address;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

}
