package com.russ.cryptoexchange.domains;

public class ReceiveForm {
    private CUser cUser;

    public ReceiveForm() {}

    public ReceiveForm(CUser cUser) {
        this.cUser = cUser;
    }

    public void setCUser(CUser cUser) {
        this.cUser = cUser;
    }

    public CUser getCUser() {
        return this.cUser;
    }
}
