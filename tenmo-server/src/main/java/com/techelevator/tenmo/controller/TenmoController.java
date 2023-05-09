package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.*;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfers;
import com.techelevator.tenmo.services.TransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;

@RequestMapping("account/")
@RestController
public class TenmoController {

    private TransfersDao transfersDao;
    private TransferService transferService;
    private AccountDao accountDao;
    private UserDao userDao;

    public TenmoController(TransfersDao transfersDao, TransferService transferService, AccountDao accountDao, UserDao userDao) {
    this.accountDao =accountDao;
    this.userDao =userDao;
    this.transfersDao =transfersDao;
    this.transferService =transferService;
}

    @GetMapping("{id}/balance")
    public Account getAccount(@PathVariable int id) {
        Account account = accountDao.getAccount(id);

        return account;
    }

    @RequestMapping(path="{id}/transfers", method = RequestMethod.GET)
    public List<Transfers> getAllTransfers(@PathVariable int id) {
        return transfersDao.getAllTransfers(id);

    }

    @RequestMapping(path = "{id}/users", method = RequestMethod.GET)
    public List<User> listOfAvailableUsers(@PathVariable int id) {
        return transferService.listOfRecipientUsers(id);
    }

    @PostMapping("{id}/make-transfer/{username}")
    public Transfers createTransfer(@PathVariable Integer id, @RequestBody Transfers newTransfer, @PathVariable String username) {
        newTransfer.setAccountIdFrom(transfersDao.accountIdFrom(id));
        newTransfer.setAccountIdTo(transfersDao.accountIdTo(username));

        transfersDao.createTransfer(newTransfer);
        transferService.validateTransferAmount(newTransfer.getAmount(),id);
        accountDao.addToBalance(newTransfer.getAmount(), username);

        return newTransfer;
    }

}
