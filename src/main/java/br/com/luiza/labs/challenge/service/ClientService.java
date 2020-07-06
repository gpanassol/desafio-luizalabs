package br.com.luiza.labs.challenge.service;

import br.com.luiza.labs.challenge.entity.Client;
import java.util.List;
import java.util.Optional;

public interface ClientService {

    List<Client> findAll();
    List<Client> findPaginated(int pageNo);
    Optional<Client> findClientById(Integer id);
    Client save(Client client);
    Client updade(Client client);
    void delete(int id);

}
