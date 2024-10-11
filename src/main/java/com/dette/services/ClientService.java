package com.dette.services;

import java.util.List;

import com.dette.entities.Client;
import com.dette.repository.implement.ClientRepository;
import com.dette.services.servicespe.IClientService;

public class ClientService implements IClientService {
    private ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public void create(Client cl) {
        clientRepository.insert(cl);
    }

    @Override
    public List<Client> findAll() {
        return clientRepository.selectAll();
    }

    @Override
    public Client getBy(String name) {
        return clientRepository.selectBy(name);
    }

    @Override
    public int count() {
        return clientRepository.count();
    }

    @Override
    public void modifier(Client client) {
        clientRepository.update(client);
    }
}
