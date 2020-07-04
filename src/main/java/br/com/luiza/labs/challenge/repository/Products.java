package br.com.luiza.labs.challenge.repository;

import br.com.luiza.labs.challenge.entity.Product;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface Products extends PagingAndSortingRepository<Product, Integer> {
}
