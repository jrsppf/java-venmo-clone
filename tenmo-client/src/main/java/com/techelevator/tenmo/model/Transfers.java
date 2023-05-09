package com.techelevator.tenmo.model;

import java.math.BigDecimal;

public class Transfers {


    private int transferStatusId;
    private int accountIdFrom;
    private int accountIdTo;
    private int userIdFrom;
    private int userIdTo;
    private String transferType;
    private String transferStatus;
    private String usernameTo;
    private String usernameFrom;
    private BigDecimal amount;

    private int transferId;
    private int transferTypeId;

    public String getTransferType() {
        return transferType;
    }

    public void setTransferType(String transferType) {
        this.transferType = transferType;
    }

    public String getTransferStatus() {
        return transferStatus;
    }

    public void setTransferStatus(String transferStatus) {
        this.transferStatus = transferStatus;
    }

    public String getUsernameTo() {
        return usernameTo;
    }

    public void setUsernameTo(String usernameTo) {
        this.usernameTo = usernameTo;
    }

    public String getUsernameFrom() {
        return usernameFrom;
    }

    public void setUsernameFrom(String usernameFrom) {
        this.usernameFrom = usernameFrom;
    }



    public int getUserIdFrom() {
        return userIdFrom;
    }

    public void setUserIdFrom(int userIdFrom) {
        this.userIdFrom = userIdFrom;
    }

    public int getUserIdTo() {
        return userIdTo;
    }

    public void setUserIdTo(int userIdTo) {
        this.userIdTo = userIdTo;
    }


        public int getTransferId() {
            return transferId;
        }

        public void setTransferId(int transferId) {
            this.transferId = transferId;
        }

        public int getTransferTypeId() {
            return transferTypeId;
        }

        public void setTransferTypeId(int transferTypeId) {
            this.transferTypeId = transferTypeId;
        }

        public int getTransferStatusId() {
            return transferStatusId;
        }

        public void setTransferStatusId(int transferStatusId) {
            this.transferStatusId = transferStatusId;
        }

        public int getAccountIdFrom() {
            return accountIdFrom;
        }

        public void setAccountIdFrom(int accountIdFrom) {
            this.accountIdFrom = accountIdFrom;
        }

        public int getAccountIdTo() {
            return accountIdTo;
        }

        public void setAccountIdTo(int accountIdTo) {
            this.accountIdTo = accountIdTo;
        }

        public BigDecimal getAmount() {
            return amount;
        }

        public void setAmount(BigDecimal amount) {
            this.amount = amount;
        }

}
