package br.com.luiza.labs.challenge.service.impl;

import br.com.luiza.labs.challenge.entity.Product;
import br.com.luiza.labs.challenge.repository.Products;
import br.com.luiza.labs.challenge.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    @Autowired
    private Products repository;

    @Override
    public Optional<Product> findProductById(Integer id) {
        return repository.findById(id);
    }

    @Override
    public Product salve(Product product) {
        return repository.save(product);
    }
}
