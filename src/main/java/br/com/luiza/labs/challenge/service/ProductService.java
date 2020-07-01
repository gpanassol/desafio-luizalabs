package br.com.luiza.labs.challenge.service;

import br.com.luiza.labs.challenge.entity.Product;

import java.util.Optional;

public interface ProductService {
    Optional<Product> findProductById(Integer id);
    Product salve(Product product);
}
