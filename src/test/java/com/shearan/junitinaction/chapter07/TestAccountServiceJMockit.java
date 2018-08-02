package com.shearan.junitinaction.chapter07;

import mockit.Mocked;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

public class TestAccountServiceJMockit {

    @Mocked
    private AccountManager mockAccountManager;

    @Before
    public void setUp() { }

    @Test
    public void testTransferOK() {
        final Account senderAccount = new Account("1", 200);
        final Account beneficiaryAccount = new Account("2", 100);

        new mockit.Expectations() {{
            mockAccountManager.findAccountForUser("1"); result = senderAccount;
            mockAccountManager.findAccountForUser("2"); result = beneficiaryAccount;
            mockAccountManager.updateAccount(senderAccount);
            mockAccountManager.updateAccount(beneficiaryAccount);
        }};

        AccountService accountService = new AccountService();
        accountService.setAccountManager(mockAccountManager);
        accountService.transfer("1", "2", 50);

        assertEquals(150, senderAccount.getBalance());
        assertEquals(150, beneficiaryAccount.getBalance());
    }
}
