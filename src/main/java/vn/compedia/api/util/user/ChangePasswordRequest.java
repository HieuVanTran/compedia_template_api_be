package vn.compedia.api.util.user;

import vn.compedia.api.entity.Account;

public class ChangePasswordRequest {
    private Account account;
    private String password;

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
