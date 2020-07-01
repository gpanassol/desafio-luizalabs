package br.com.luiza.labs.challenge.repository;

import br.com.luiza.labs.challenge.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Products extends JpaRepository<Product, Integer> {
}
