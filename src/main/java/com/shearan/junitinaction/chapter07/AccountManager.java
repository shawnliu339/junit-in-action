package com.shearan.junitinaction.chapter07;

public interface AccountManager {
    Account findAccountForUser(String userId);
    void updateAccount(Account account);
}
