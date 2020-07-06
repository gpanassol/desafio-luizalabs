package br.com.luiza.labs.challenge.service;

import br.com.luiza.labs.challenge.entity.ProductClient;
import java.util.List;

public interface ProductClientService {
    List<ProductClient> findAllProductByIdClient(Integer id);
}
