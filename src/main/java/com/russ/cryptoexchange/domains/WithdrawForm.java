package com.russ.cryptoexchange.domains;

public class WithdrawForm {

    private CUser cUser;
    private Double amount;

    public WithdrawForm() {}

    public WithdrawForm(CUser cUser, Double amount) {
        this.cUser = cUser;
        this.amount = amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public void setCUser(CUser cUser) {
        this.cUser = cUser;
    }

    public Double getAmount() {
        return this.amount;
    }

    public CUser getCUser() {
        return this.cUser;
    }
}
