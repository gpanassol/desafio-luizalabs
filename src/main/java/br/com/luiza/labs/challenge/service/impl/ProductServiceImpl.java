package br.com.luiza.labs.challenge.service.impl;

import br.com.luiza.labs.challenge.entity.Product;
import br.com.luiza.labs.challenge.repository.Products;
import br.com.luiza.labs.challenge.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    @Value("${page.size.default}")
    private Integer pagesize;

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

    @Override
    public List<Product> findPaginated(int page) {
        return repository.findAll(PageRequest.of(page, pagesize)).toList();
    }
}
