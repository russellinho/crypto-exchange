package com.russ.cryptoexchange.domains;

import java.text.DecimalFormat;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table
public class CUser {
    @Id
    @SequenceGenerator(
        name = "user_sequence",
        sequenceName = "user_sequence",
        allocationSize = 1
    )
    @GeneratedValue(
        strategy = GenerationType.SEQUENCE,
        generator = "user_sequence"
    )
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String walletAddress;
    private String routing;
    private Double fiat;

    public CUser() {}

    public CUser(Long id, String firstName, String lastName, String email, String walletAddress, String routing, Double fiat) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.walletAddress = walletAddress;
        this.routing = routing;
        this.fiat = fiat;
    }

    public CUser(String firstName, String lastName, String email, String walletAddress, String routing, Double fiat) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.walletAddress = walletAddress;
        this.routing = routing;
        this.fiat = fiat;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setWalletAddress(String walletAddress) {
        this.walletAddress = walletAddress;
    }

    public void setRouting(String routing) {
        this.routing = routing;
    }

    public void setFiat(Double fiat) {
        this.fiat = fiat;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public String getEmail() {
        return this.email;
    }

    public String getWalletAddress() {
        return this.walletAddress;
    }

    public String getRouting() {
        return this.routing;
    }

    public Double getFiat() {
        return this.fiat;
    }

    public String fiatToString() {
        return String.format("%.2f", getFiat());
    }
}
