package com.russ.cryptoexchange.domains;

public class SendForm {
    private CUser cUser;
    private Wallet wallet;
    private String recipientAddress;
    private String selectedCoin;
    private Double amount;

    public SendForm() {}

    public SendForm(CUser cUser, Wallet wallet, String recipientAddress, String selectedCoin) {
        this.cUser = cUser;
        this.wallet = wallet;
        this.recipientAddress = recipientAddress;
        this.selectedCoin = selectedCoin;
        this.amount = null;
    }

    public void setCUser(CUser cUser) {
        this.cUser = cUser;
    }

    public void setWallet(Wallet wallet) {
        this.wallet = wallet;
    }

    public void setRecipientAddress(String recipientAddress) {
        this.recipientAddress = recipientAddress;
    }

    public void setSelectedCoin(String selectedCoin) {
        this.selectedCoin = selectedCoin;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public CUser getCUser() {
        return this.cUser;
    }

    public Wallet getWallet() {
        return this.wallet;
    }

    public String getRecipientAddress() {
        return this.recipientAddress;
    }

    public String getSelectedCoin() {
        return this.selectedCoin;
    }

    public Double getAmount() {
        return this.amount;
    }
}
