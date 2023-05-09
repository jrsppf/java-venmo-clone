package com.techelevator.tenmo.dao;

import java.math.BigDecimal;
import java.util.List;

import com.techelevator.tenmo.model.Transfers;

public interface TransfersDao {

    public List<Transfers> getAllTransfers(int userId);

    public String sendTransfer(int userFrom, int userTo, BigDecimal amount);
    public String requestTransfer(int userFrom, int userTo, BigDecimal amount);
    public List<Transfers> getPendingRequests(int userId);
    public String updateTransferRequest(Transfers transfer, int statusId);
    public Transfers createTransfer(Transfers newTransfer);
    public int accountIdTo(String selectedUsername);
    public int accountIdFrom(int userId);
    public String getTransferStatusFromTransferId(int transferId);
    public String getTransferTypeTypeFromTransferId(int transferId);

    String requestTransfer(List<Transfers> accountIdFrom);
}