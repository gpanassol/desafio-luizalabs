package br.com.luiza.labs.challenge.repository;

import br.com.luiza.labs.challenge.entity.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface Favorites extends JpaRepository<Favorite, Integer> {
    List<Favorite> findAllProductByClientId(Integer idClient);
    List<Favorite> findByProductId(Integer productId);
    void deleteByProductIdAndClientId(Integer productId, Integer clientId);
}
