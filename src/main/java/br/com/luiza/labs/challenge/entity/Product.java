package br.com.luiza.labs.challenge.entity;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue
    @Column(name="id")
    private Integer id;

    @Column(name="price")
    private BigDecimal price;

    @Column(name="url")
    private String url;

    @Column(name="marca")
    private String marca;

    @Column(name="title")
    private String title;

    @Column(name="review_score")
    private Double reviewScore;

    public Product(){}

    public Product(Integer id, BigDecimal price, String url, String marca, String title, Double reviewScore) {
        this.id = id;
        this.price = price;
        this.url = url;
        this.marca = marca;
        this.title = title;
        this.reviewScore = reviewScore;
    }
}
