package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;

import java.math.BigDecimal;

public interface AccountDao {
    BigDecimal getBalanceByUserId(int userId);

    BigDecimal getBalanceByUsername(String username);

    BigDecimal addToBalance(BigDecimal amountToAdd, String username);

    void subtractFromBalance(int userId, BigDecimal amountToSubtract);

    Account getAccount(int userId);

    String getUsernameToByAccountId(int accountId);
}
