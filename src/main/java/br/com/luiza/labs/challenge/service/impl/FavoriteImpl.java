package br.com.luiza.labs.challenge.service.impl;

import br.com.luiza.labs.challenge.entity.Favorite;
import br.com.luiza.labs.challenge.repository.Favorites;
import br.com.luiza.labs.challenge.service.FavoriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FavoriteImpl implements FavoriteService {

    @Autowired
    private Favorites repository;

    @Override
    public List<Favorite> findAllProductByIdClient(Integer id) {
        return repository.findAllProductByClientId(id);
    }

    @Override
    public Favorite save(Favorite favorite) {
        return repository.save(favorite);
    }

    @Override
    public Favorite updade(Favorite favorite) {
        return repository.save(favorite);
    }

    @Override
    public void delete(int productID, int clientID) {
        repository.deleteByProductIdAndClientId(productID, clientID);
    }
}
