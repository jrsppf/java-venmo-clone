package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class JdbcAccountDao implements AccountDao {

    private JdbcTemplate jdbcTemplate;


    public JdbcAccountDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
        public BigDecimal getBalanceByUserId(int userId) {
        String sql = "SELECT balance FROM account WHERE user_id = ?";
        return jdbcTemplate.queryForObject(sql, BigDecimal.class, userId);
    }

    @Override
    public BigDecimal getBalanceByUsername(String username) {
        String sql = "SELECT balance FROM account JOIN tenmo_user ON account.user_id = tenmo_user.user_id WHERE username = ?";
        return jdbcTemplate.queryForObject(sql, BigDecimal.class, username);
    }


    @Override
    public Account getAccount(int userId) {
        Account account = new Account();
        String sql = "SELECT * FROM account WHERE user_id = ?";

        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, userId);
        while(rowSet.next()) {
            account = mapRowToAccount(rowSet);
        }
        return account;

    }

    @Override
    public String getUsernameToByAccountId(int accountId) {
        String sql = "SELECT username FROM tenmo_user JOIN account ON " +
                "tenmo_user.user_id = account.user_id JOIN transfer ON account.account_id = " +
                "transfer.account_to WHERE transfer.account_to = ? LIMIT 1";

        return jdbcTemplate.queryForObject(sql,String.class, accountId);
    }


    @Override
    public BigDecimal addToBalance(BigDecimal amountToAdd, String username) {
        BigDecimal newBalance = getBalanceByUsername(username).add(amountToAdd);
        String sql = "UPDATE account\n" +
                "SET balance = ?\n" +
                "FROM tenmo_user\n" +
                "WHERE account.user_id = tenmo_user.user_id AND tenmo_user.username = ?";
        jdbcTemplate.update(sql, newBalance, username);
        return null;
    }

    @Override
    public void subtractFromBalance(int userId, BigDecimal amountToSubtract) {
        BigDecimal newBalance = getBalanceByUserId(userId).subtract(amountToSubtract);
        String sql = "UPDATE account SET balance = ? WHERE user_id = ?";
        jdbcTemplate.update(sql, newBalance, userId);

    }

    private Account mapRowToAccount(SqlRowSet row) {
        int accountId = row.getInt("account_id");
        int userId = row.getInt("user_id");
        BigDecimal balance = row.getBigDecimal("balance");
        return new Account(accountId, userId, balance);
    }



}
