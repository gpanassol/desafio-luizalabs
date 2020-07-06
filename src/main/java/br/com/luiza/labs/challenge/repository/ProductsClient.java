package br.com.luiza.labs.challenge.repository;

import br.com.luiza.labs.challenge.entity.ProductClient;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProductsClient extends JpaRepository<ProductClient, Integer> {
    List<ProductClient> findAllProductByClientId(Integer idClient);
}
