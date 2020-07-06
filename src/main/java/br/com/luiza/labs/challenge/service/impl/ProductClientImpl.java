package br.com.luiza.labs.challenge.service.impl;

import br.com.luiza.labs.challenge.entity.ProductClient;
import br.com.luiza.labs.challenge.repository.ProductsClient;
import br.com.luiza.labs.challenge.service.ProductClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductClientImpl implements ProductClientService {

    @Autowired
    private ProductsClient repository;

    @Override
    public List<ProductClient> findAllProductByIdClient(Integer id) {
        return repository.findAllProductByClientId(id);
    }
}
