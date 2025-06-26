package com.example.demo.service;

import com.example.demo.dto.ClientDto;
import com.example.demo.mapper.ClientMapper;
import com.example.demo.model.Client;
import com.example.demo.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    public List<ClientDto> getAllClients() {
        return clientRepository.findAllByOrderByIdAsc()
                .stream()
                .map(ClientMapper::toDto)
                .collect(Collectors.toList());
    }

    public ResponseEntity<ClientDto> getClientById(Long id) {
        Optional<Client> client = clientRepository.findById(id);
        return client.map(c -> ResponseEntity.ok(ClientMapper.toDto(c)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    public ClientDto saveClient(ClientDto clientDto) {
        Client saved = clientRepository.save(ClientMapper.toEntity(clientDto));
        return ClientMapper.toDto(saved);
    }

    public ResponseEntity<ClientDto> updateClient(Long id, ClientDto clientDetails) {
        Optional<Client> optionalClient = clientRepository.findById(id);
        if (optionalClient.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Client client = optionalClient.get();

        if (clientDetails.getName() != null) {
            client.setName(clientDetails.getName());
        }
        if (clientDetails.getPhone() != null) {
            client.setPhone(clientDetails.getPhone());
        }
        if (clientDetails.getEmail() != null) {
            client.setEmail(clientDetails.getEmail());
        }
        if (clientDetails.getAddress() != null) {
            client.setAddress(clientDetails.getAddress());
        }

        Client updated = clientRepository.save(client);
        return ResponseEntity.ok(ClientMapper.toDto(updated));
    }


    public ResponseEntity<Void> deleteClient(Long id) {
        if (!clientRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        clientRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    public List<ClientDto> searchClients(String name, String phone, String email, Long clientId, String address) {
        List<Client> clients;

        if (clientId != null) {
            Optional<Client> clientOpt = clientRepository.findById(clientId);
            if (clientOpt.isPresent()) {
                clients = List.of(clientOpt.get());
            } else {
                clients = Collections.emptyList();
            }
        } else if (name != null && !name.isEmpty()) {
            clients = clientRepository.findByNameContainingIgnoreCase(name);
        } else if (phone != null && !phone.isEmpty()) {
            clients = clientRepository.findByPhoneContainingIgnoreCase(phone);
        } else if (email != null && !email.isEmpty()) {
            clients = clientRepository.findByEmailContainingIgnoreCase(email);
        } else if (address != null && !address.isEmpty()) {
            clients = clientRepository.findByAddressContainingIgnoreCase(address);
        } else {
            clients = clientRepository.findAll();
        }

        return clients.stream()
                .map(ClientMapper::toDto)
                .collect(Collectors.toList());
    }


}
