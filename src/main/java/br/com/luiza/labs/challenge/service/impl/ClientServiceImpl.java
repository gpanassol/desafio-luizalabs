package br.com.luiza.labs.challenge.service.impl;

import br.com.luiza.labs.challenge.entity.Client;
import br.com.luiza.labs.challenge.repository.Clients;
import br.com.luiza.labs.challenge.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

    @Value("${page.size.default}")
    private Integer pagesize;

    @Autowired
    private Clients repository;

    @Override
    public List<Client> findAll() {
        return repository.findAll();
    }

    @Override
    public List<Client> findPaginated(int pageNo) {
        return repository.findAll(PageRequest.of(pageNo, pagesize)).toList();
    }

    @Override
    public Optional<Client> findClientById(Integer id) {
        return repository.findById(id);
    }

    @Override
    public Client save(Client client) {
        return repository.save(client);
    }

    @Override
    public Client updade(Client client) {
        return repository.save(client);
    }

    @Override
    public void delete(int id) {
        repository.deleteById(id);
    }
}
