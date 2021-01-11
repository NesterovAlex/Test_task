package com.nesterov.demo.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "discounts")
public class Discount implements DBEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  long id;
    @Column
    private long level;

    public Discount(){}

    public Discount(long level) {
        this.level = level;
    }

    public Discount(long id, long level) {
        this.id = id;
        this.level = level;
    }

    @OneToMany(
            mappedBy = "discount",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Product> products = new ArrayList<>();

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getLevel() {
        return level;
    }

    public void setLevel(long level) {
        this.level = level;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Discount discount = (Discount) o;
        return id == discount.id &&
                level == discount.level;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, level);
    }
}
