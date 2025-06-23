package com.example.demo;

import com.example.demo.model.Car;
import com.example.demo.model.Client;
import com.example.demo.repository.CarRepository;
import com.example.demo.repository.ClientRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
}
