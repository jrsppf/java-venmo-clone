package com.techelevator.tenmo.services;


import com.techelevator.tenmo.dao.UserDao;
import org.springframework.stereotype.Component;

@Component
public class UserService {

    private final UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public Integer findIdByUsername(String username) {
        return userDao.findIdByUsername(username);
    }


}
