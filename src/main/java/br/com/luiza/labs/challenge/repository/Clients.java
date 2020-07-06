package br.com.luiza.labs.challenge.repository;

import br.com.luiza.labs.challenge.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Clients extends JpaRepository<Client, Integer> {
}
