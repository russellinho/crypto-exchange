package com.russ.cryptoexchange.domains;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table
public class Wallet {
    @Id
    @GeneratedValue(generator = "address-generator")
    @GenericGenerator(name = "address-generator", strategy = "com.russ.cryptoexchange.WalletGenerator")
    private String address;
    private Double btc;
    private Double eth;
    private Double cel;

    public Wallet() {
        this.btc = 0.0;
        this.eth = 0.0;
        this.cel = 0.0;
    }

    public Wallet(String address, Double btc, Double eth, Double cel) {
        this.address = address;
        this.btc = btc;
        this.eth = eth;
        this.cel = cel;
    }
    
    public String getAddress() {
        return this.address;
    }

    public Double getBtc() {
        return this.btc;
    }

    public Double getEth() {
        return this.eth;
    }

    public Double getCel() {
        return this.cel;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setBtc(Double btc) {
        this.btc = btc;
    }

    public void setEth(Double eth) {
        this.eth = eth;
    }

    public void setCel(Double cel) {
        this.cel = cel;
    }
}
