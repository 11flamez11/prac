package com.example.demo.mapper;

import com.example.demo.dto.ClientDto;
import com.example.demo.model.Client;

public class ClientMapper {

    public static ClientDto toDto(Client client) {
        if (client == null) return null;

        ClientDto dto = new ClientDto();
        dto.setId(client.getId());
        dto.setName(client.getName());
        dto.setPhone(client.getPhone());
        dto.setEmail(client.getEmail());
        dto.setAddress(client.getAddress());
        return dto;
    }

    public static Client toEntity(ClientDto dto) {
        if (dto == null) return null;

        Client client = new Client();
        client.setId(dto.getId());
        client.setName(dto.getName());
        client.setPhone(dto.getPhone());
        client.setEmail(dto.getEmail());
        client.setAddress(dto.getAddress());
        return client;
    }
}
