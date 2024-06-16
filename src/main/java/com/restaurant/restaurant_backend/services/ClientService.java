package com.restaurant.restaurant_backend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.restaurant.restaurant_backend.entity.Client;
import com.restaurant.restaurant_backend.repository.ClientRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ClientService {
    @Autowired
    private ClientRepository clientRepository;

    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    public Client saveClient(Client client) {
        return clientRepository.save(client);
    }

    public Client updateClient(Client client) {
        Optional<Client> existingClient = clientRepository.findById(client.getId());
        if (existingClient.isPresent()) {
            return clientRepository.save(client);
        }
        return null;
    }

    public boolean deleteClient(Long id) {
        if (clientRepository.existsById(id)) {
            clientRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public Optional<Client> getClientById(Long id) {
        return clientRepository.findById(id);
    }
}
