package com.russ.cryptoexchange.domains;

public class Exchange {

    private Coin btc;
    private Coin eth;
    private Coin cel;

    public Exchange() {}

    public Exchange(Coin btc, Coin eth, Coin cel) {
        this.btc = btc;
        this.eth = eth;
        this.cel = cel;
    }

    public void setBtc(Coin btc) {
        this.btc = btc;
    }

    public void setEth(Coin eth) {
        this.eth = eth;
    }

    public void setCel(Coin cel) {
        this.cel = cel;
    }

    public Coin getBtc() {
        return this.btc;
    }

    public Coin getEth() {
        return this.eth;
    }

    public Coin getCel() {
        return this.cel;
    }
}
