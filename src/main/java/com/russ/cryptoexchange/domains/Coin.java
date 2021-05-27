package com.russ.cryptoexchange.domains;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class Coin {
    public static final int TOTAL_COINS = 3;
    @Id
    private Long id;
    private String name;
    private String description;
    private String thumbnail;
    private Double price;
    private Double quantity;

    public Coin(Long id, String name, String description, String thumbnail, Double price, Double quantity) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.thumbnail = thumbnail;
        this.price = price;
        this.quantity = quantity;
    }

    public Long getId() {
        return this.id;
    }
    
    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public String getThumbnail() {
        return this.thumbnail;
    }

    public Double getPrice() {
        return this.price;
    }

    public Double getQuantity() {
        return this.quantity;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }
}
