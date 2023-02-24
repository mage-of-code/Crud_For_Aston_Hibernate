package org.bokhoeva.model;

import jakarta.validation.constraints.NotBlank;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "unit")
public class Unit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotBlank(message = "введите единицу измерения")
    @Column
    private String nomination;

    @OneToMany(mappedBy = "unit")
    private List<Product> products;

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public int getId() {
        return id;
    }
    public String getNomination() {
        return nomination;
    }

    public void setNomination(String nomination) {
        this.nomination = nomination;
    }
}
