package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfers;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcTransferDao implements TransfersDao {

    private JdbcTemplate jdbcTemplate;
    private JdbcAccountDao jdbcAccountDao;
    private JdbcUserDao jdbcUserDao;
    private JdbcTransferDao jdbcTransferDao;

    public JdbcTransferDao(JdbcTemplate jdbcTemplate, JdbcUserDao jdbcUserDao, JdbcAccountDao jdbcAccountDao) {
        this.jdbcTemplate = jdbcTemplate;
        this.jdbcUserDao = jdbcUserDao;
        this.jdbcAccountDao = jdbcAccountDao;

    }



    @Override
    public List<Transfers> getAllTransfers(int userId) {
        List<Transfers> transferHistory = new ArrayList<>();
        String sql = "SELECT transfer_id, transfer_type_id, transfer_status_id, account_from, account_to, amount FROM transfer\n" +
                "JOIN account ON account.account_id IN (transfer.account_from, transfer.account_to)\n" +
                "WHERE account.user_id = ?";
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, userId);
        while (rowSet.next()) {
            Transfers transfer = mapResultToTransfer(rowSet);
            transfer.setUsernameFrom(jdbcUserDao.getUserById(userId).getUsername());
            transfer.setUsernameTo(jdbcAccountDao.getUsernameToByAccountId(transfer.getAccountIdTo()));
            transferHistory.add(transfer);
            transfer.setTransferType(getTransferTypeTypeFromTransferId(transfer.getTransferId()));
            transfer.setTransferStatus(getTransferStatusFromTransferId(transfer.getTransferId()));

        }
        return transferHistory;
    }


    @Override
    public String sendTransfer(int userFrom, int userTo, BigDecimal amount) {
        return null;
    }

    @Override
    public String requestTransfer(int userFrom, int userTo, BigDecimal amount) {
        return null;
    }

    @Override
    public List<Transfers> getPendingRequests(int userId) {
        return null;
    }

    @Override
    public String updateTransferRequest(Transfers transfer, int statusId) {
        return null;
    }

    @Override
    public Transfers createTransfer(Transfers newTransfer) {
        String sql = "INSERT INTO transfer (transfer_id, transfer_type_id, transfer_status_id, account_from, account_to, amount) VALUES (DEFAULT, ?, ?, ?, ?, ?) RETURNING transfer_id";
        Integer transferId = jdbcTemplate.queryForObject(sql, Integer.class,  newTransfer.getTransferTypeId(),
                newTransfer.getTransferStatusId(), newTransfer.getAccountIdFrom(), newTransfer.getAccountIdTo(), newTransfer.getAmount());
        newTransfer.setTransferId(transferId);
        return newTransfer;
    }



    @Override
    public String getTransferStatusFromTransferId(int transferId) {
        String sql = "SELECT transfer_status_desc \n" +
                "FROM transfer_status\n" +
                "JOIN transfer ON transfer_status.transfer_status_id = transfer.transfer_status_id\n" +
                "WHERE transfer_id = ?";
        return jdbcTemplate.queryForObject(sql, String.class, transferId);
    }

    @Override
    public String getTransferTypeTypeFromTransferId(int transferId) {
        String sql = "SELECT transfer_type_desc \n" +
                "FROM transfer_type\n" +
                "JOIN transfer ON transfer_type.transfer_type_id = transfer.transfer_type_id\n" +
                "WHERE transfer_id = ?";
        return jdbcTemplate.queryForObject(sql, String.class, transferId);
    }

    @Override
    public int accountIdTo(String selectedUsername) {
        String sql = "SELECT account_id FROM account\n" +
                "JOIN tenmo_user ON account.user_id = tenmo_user.user_id\n" +
                "WHERE tenmo_user.username = ?";

        return jdbcTemplate.queryForObject(sql, Integer.class, selectedUsername);


    }

    @Override
    public int accountIdFrom(int userId) {
        String sql = "SELECT account_id FROM account\n" +
                "JOIN tenmo_user ON account.user_id = tenmo_user.user_id\n" +
                "WHERE tenmo_user.user_id = ?";
        return jdbcTemplate.queryForObject(sql,Integer.class, userId);

    }

    @Override
    public String requestTransfer(List<Transfers> accountIdFrom) {
        return null;
    }



    private Transfers mapResultToTransfer(SqlRowSet results) {
        Transfers transfer = new Transfers();
        transfer.setTransferId(results.getInt("transfer_id"));
        transfer.setTransferTypeId(results.getInt("transfer_type_id"));
        transfer.setTransferStatusId(results.getInt("transfer_status_id"));
        transfer.setAccountIdFrom(results.getInt("account_from"));
        transfer.setAccountIdTo(results.getInt("account_to"));
        transfer.setAmount(results.getBigDecimal("amount"));



        return transfer;

    }


}
