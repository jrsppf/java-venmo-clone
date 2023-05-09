package com.techelevator.tenmo.services;


import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.JdbcUserDao;
import com.techelevator.tenmo.exception.InsufficientFundsException;
import com.techelevator.tenmo.model.User;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class TransferService {

    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_BOLD = "\u001B[1m";
    public static final String ANSI_RESET = "\u001B[0m";


    private JdbcUserDao jdbcUserDao;
    private UserService userService;
    private AccountDao accountDao;


    TransferService(JdbcUserDao jdbcUserDao, UserService userService, AccountDao accountDao) {
        this.jdbcUserDao = jdbcUserDao;
        this.userService = userService;
        this.accountDao = accountDao;
    }


    public List<User> listOfRecipientUsers(int id) {
        List<User> availableUsers = jdbcUserDao.findAll();
        availableUsers.removeIf(user -> user.getId() == id);


        return availableUsers;

    }

    public void validateTransferAmount(BigDecimal amount, Integer userId) {
        try {
            BigDecimal balance = accountDao.getBalanceByUserId(userId);
            if (balance.compareTo(amount) >= 0 && amount.compareTo(BigDecimal.valueOf(0)) >= 0) {
                accountDao.subtractFromBalance(userId, amount);
            } else {
                throw new InsufficientFundsException(ANSI_BOLD + ANSI_RED + "You don't have the cash!" + ANSI_RESET);
            }
        } catch (InsufficientFundsException ex) {
            throw new InsufficientFundsException(ANSI_BOLD + ANSI_RED +"You don't have the cash!"+ ANSI_RESET);
        }

    }

}




