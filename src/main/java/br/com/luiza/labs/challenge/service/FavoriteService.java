package br.com.luiza.labs.challenge.service;

import br.com.luiza.labs.challenge.entity.Favorite;
import java.util.List;

public interface FavoriteService {
    List<Favorite> findAllProductByIdClient(Integer id);
    Favorite save(Favorite favorite);
    Favorite updade(Favorite favorite);
    void delete(int clientID, int productID);
}
