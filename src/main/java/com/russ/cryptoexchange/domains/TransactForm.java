package com.russ.cryptoexchange.domains;

public class TransactForm {
    private CUser cUser;
    private Wallet wallet;
    private String selectedCoin;
    private Double coinAmount;
    private boolean buy;

    public TransactForm() {}

    public TransactForm(CUser cUser, Wallet wallet) {
        this.cUser = cUser;
        this.wallet = wallet;
    }

    public TransactForm(CUser cUser, Wallet wallet, String selectedCoin, Double coinAmount, boolean buy) {
        this.cUser = cUser;
        this.wallet = wallet;
        this.selectedCoin = selectedCoin;
        this.coinAmount = coinAmount;
        this.buy = buy;
    }

    public void setCUser(CUser cUser) {
        this.cUser = cUser;
    }

    public void setWallet(Wallet wallet) {
        this.wallet = wallet;
    }

    public void setSelectedCoin(String selectedCoin) {
        this.selectedCoin = selectedCoin;
    }

    public void setCoinAmount(Double coinAmount) {
        this.coinAmount = coinAmount;
    }

    public void setBuy(boolean buy) {
        this.buy = buy;
    }

    public CUser getCUser() {
        return this.cUser;
    }

    public Wallet getWallet() {
        return this.wallet;
    }

    public String getSelectedCoin() {
        return this.selectedCoin;
    }

    public Double getCoinAmount() {
        return this.coinAmount;
    }

    public boolean getBuy() {
        return this.buy;
    }
}
