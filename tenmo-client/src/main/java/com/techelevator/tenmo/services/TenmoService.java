package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.tenmo.model.Transfers;
import com.techelevator.tenmo.model.User;
import com.techelevator.util.BasicLogger;
import org.springframework.http.*;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

public class TenmoService {

    //Color variables
    private final String bold = ConsoleService.ANSI_BOLD;
    private final String purple = ConsoleService.ANSI_PURPLE;
    private final String red = ConsoleService.ANSI_RED;
    private final String colorReset = ConsoleService.ANSI_RESET;
    private final String green = ConsoleService.ANSI_GREEN;
    private final String yellow = ConsoleService.ANSI_YELLOW;

    private String BASE_URL = "http://localhost:8080/";
    private RestTemplate restTemplate = new RestTemplate();

    public TenmoService(String BASE_URL) {
        this.BASE_URL = BASE_URL;


    }

    public BigDecimal getAccount(AuthenticatedUser currentUser) {
        BigDecimal balance = null;
        try {
            ResponseEntity<Account> response = restTemplate.exchange(BASE_URL + "account/" +
                    currentUser.getUser().getId() + "/balance", HttpMethod.GET, makeGetEntity(currentUser), Account.class);
            balance = response.getBody().getBalance();
        } catch (RestClientException e) {
            System.out.println(bold + red + "There was an error retrieving your balance." + colorReset);
        }
        return balance;
    }


    public User[] getListOfAvailableUsers(AuthenticatedUser currentUser) {
        try {
            ResponseEntity<User[]> response = restTemplate.exchange(BASE_URL + "account/" +
                            currentUser.getUser().getId() + "/users", HttpMethod.GET,
                    makeGetEntity(currentUser), User[].class);
            return response.getBody();
        } catch (RestClientException e) {
            System.out.println(bold + red +"There was an error retrieving users." + colorReset);
        }
        return null;
    }


    public Transfers[] getTransferHistory(AuthenticatedUser currentUser) {
        try {
            ResponseEntity<Transfers[]> response = restTemplate.exchange
                    (BASE_URL + "account/" + currentUser.getUser().getId()
                            + "/transfers", HttpMethod.GET, makeGetEntity(currentUser), Transfers[].class);
            return response.getBody();
        } catch (RestClientResponseException | ResourceAccessException ex) {
            BasicLogger.log(ex.getMessage());
            System.out.println(ex.getMessage());
            return null;
        }
    }

    public void makeTransfer(Transfers newTransfer, String username, AuthenticatedUser currentUser) {
        try {
            Transfers transfer = restTemplate.postForObject(BASE_URL + "account/" +
                    currentUser.getUser().getId() + "/make-transfer/" + username, makeTransferEntity(newTransfer, currentUser), Transfers.class);
            System.out.println(bold + green + "Transfer Approved!" + colorReset);
        } catch (RestClientException e) {
            System.out.println(bold + red +"There was an error in your transfer request." + colorReset);
        }
    }

    public HttpEntity<User> makeUserEntity(User user, AuthenticatedUser currentUser) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(currentUser.getToken());
        return new HttpEntity<>(user, headers);
    }

    public HttpEntity<Transfers> makeTransferEntity(Transfers transfer, AuthenticatedUser currentUser) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(currentUser.getToken());
        return new HttpEntity<>(transfer, headers);
    }

    private HttpEntity<Void> makeGetEntity(AuthenticatedUser currentUser) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(currentUser.getToken());
        return new HttpEntity<>(headers);
    }

}
