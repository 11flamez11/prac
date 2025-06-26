package com.example.demo.repository;

import com.example.demo.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClientRepository extends JpaRepository<Client, Long> {

    List<Client> findByNameContainingIgnoreCase(String name);

    List<Client> findByPhoneContainingIgnoreCase(String phone);

    List<Client> findByEmailContainingIgnoreCase(String email);

    List<Client> findByAddressContainingIgnoreCase(String address);

    List<Client> findAllByOrderByIdAsc();

}
