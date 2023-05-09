package com.techelevator.tenmo;

import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.tenmo.model.Transfers;
import com.techelevator.tenmo.model.User;
import com.techelevator.tenmo.model.UserCredentials;
import com.techelevator.tenmo.services.TenmoService;
import com.techelevator.tenmo.services.AuthenticationService;
import com.techelevator.tenmo.services.ConsoleService;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class App {

    private static final String API_BASE_URL = "http://localhost:8080/";

    //Color variables
    private final String bold = ConsoleService.ANSI_BOLD;
    private final String purple = ConsoleService.ANSI_PURPLE;
    private final String red = ConsoleService.ANSI_RED;
    private final String colorReset = ConsoleService.ANSI_RESET;
    private final String green = ConsoleService.ANSI_GREEN;
    private final String yellow = ConsoleService.ANSI_YELLOW;



    private final ConsoleService consoleService = new ConsoleService();
    private final AuthenticationService authenticationService = new AuthenticationService(API_BASE_URL);
    private final TenmoService tenmoService = new TenmoService(API_BASE_URL);

    private AuthenticatedUser currentUser;

    public static void main(String[] args) {
        App app = new App();
        app.run();
    }

    private void run() {
        consoleService.printGreeting();
        loginMenu();
        if (currentUser != null) {
            mainMenu();
        }
    }
    private void loginMenu() {
        int menuSelection = -1;
        while (menuSelection != 0 && currentUser == null) {
            consoleService.printLoginMenu();
            menuSelection = consoleService.promptForMenuSelection(green
                    + "Please choose an option: " + colorReset);
            if (menuSelection == 1) {
                handleRegister();
            } else if (menuSelection == 2) {
                handleLogin();
            } else if (menuSelection != 0) {
                System.out.println(red + "Invalid Selection" + colorReset);
                consoleService.pause();
            }
        }
    }

    private void handleRegister() {
        System.out.println(yellow + "Please register a new user account" + colorReset);
        UserCredentials credentials = consoleService.promptForCredentials();
        if (authenticationService.register(credentials)) {
            System.out.println(bold + purple + "Registration successful. You can now login." + colorReset);
        } else {
            consoleService.printErrorMessage();
        }
    }

    private void handleLogin() {
        UserCredentials credentials = consoleService.promptForCredentials();
        currentUser = authenticationService.login(credentials);
        if (currentUser == null) {
            consoleService.printErrorMessage();
        }
    }

    private void mainMenu() {
        int menuSelection = -1;
        while (menuSelection != 0) {
            consoleService.printMainMenu();
            menuSelection = consoleService.promptForMenuSelection(purple + "Please choose an option: "+ colorReset);
            if (menuSelection == 1) {
                viewCurrentBalance();
            } else if (menuSelection == 2) {
                viewTransferHistory();
            } else if (menuSelection == 3) {
                viewPendingRequests();
            } else if (menuSelection == 4) {
                sendBucks();
            } else if (menuSelection == 5) {
                requestBucks();
            } else if (menuSelection == 0) {
                continue;
            } else {
                System.out.println(red + "Invalid Selection" + colorReset);
            }
            consoleService.pause();
        }
    }



	private void viewCurrentBalance() {
        try {
            BigDecimal balance = tenmoService.getAccount(currentUser);
            System.out.println(yellow + "Your current balance is: " + colorReset
                    + "$" + balance);
        } catch (NullPointerException e) {
            System.out.println(red + "Balance Error" + colorReset);
        }
	}

	private void viewTransferHistory() {
        Transfers[] transfers = tenmoService.getTransferHistory(currentUser);

        System.out.println(yellow + "---------------------------------------------\n" +
                "Transfers                          \n" +
                "ID                 To                Amount" +
                "\n---------------------------------------------" + colorReset);
        for (Transfers transfer : transfers) {
            if(transfer.getUsernameTo().equalsIgnoreCase(currentUser.getUser().getUsername())) {
                System.out.println(transfer.getTransferId() + "            " +  "From: " + transfer.getUsernameFrom() + "           $" + transfer.getAmount());

            } else if (transfer.getUsernameFrom().equalsIgnoreCase(currentUser.getUser().getUsername())) {
                System.out.println(transfer.getTransferId() + "            " +  "To: " + transfer.getUsernameTo() + "           $" + transfer.getAmount());
            }
        }

        int transferId = -1;
        while (transferId != 0) {
            transferId = consoleService.promptForMenuSelection(purple + "Please enter transfer ID to view details (0 to cancel): " + colorReset);
            if (transferId == 0) {
                mainMenu();
            } else {
                boolean foundTransferId = false;

                for (Transfers transfer : transfers) {
                    if (transfer.getTransferId() == transferId) {
                        System.out.println(
                                "--------------------------------------------\r\n" +
                                "Transfer Details\r\n" +
                                "--------------------------------------------\r\n" +
                                "  Id: "+ transfer.getTransferId() + "\r\n" +
                                "  From: " + transfer.getUsernameFrom() + "\r\n" +
                                "  To: " + transfer.getUsernameTo() + "\r\n" +
                                "  Type: " + transfer.getTransferType() + "\r\n" +
                                "  Status: " + transfer.getTransferStatus() + "\r\n" +
                                "  Amount: $" + transfer.getAmount());
                        foundTransferId = true;
                    }
                }
                    if(!foundTransferId) {
                        System.out.println(bold + red + "No Matching Transfer ID" + colorReset);
                    }
                }
            }
        }


    private void displayTransfers(Transfers[] transfers) {
        if (transfers != null) {
            for (Transfers transfer : transfers) {
                System.out.println(transfer.getTransferId() + " " + transfer.getAmount());
            }
        }
    }
		


	private void viewPendingRequests() {
		// TODO Auto-generated method stub
		
	}

	private void sendBucks() {
		try {
            System.out.println(green + "List of Users: " + colorReset);
            System.out.println(yellow + "----------------------------------\n" +
                    "ID            Username             " +
                    "\n----------------------------------" + colorReset);
            User[] users = tenmoService.getListOfAvailableUsers(currentUser);


            for(User user : users) {
                System.out.println(green + user.getId() + "          " + user.getUsername() + colorReset);
            }

            System.out.println();
            String username = consoleService.promptForString(purple + "Who do you want to send money to?! Type in the Username: " + colorReset);
            for(User user: users) {
                if (user.getUsername().equalsIgnoreCase(username)) {
                    BigDecimal amount = consoleService.promptForBigDecimal(purple + "Enter Amount: $" + colorReset );
                    System.out.println("$" + amount);

                    Transfers newTransfer = new Transfers();
                    newTransfer.setAmount(amount);
                    newTransfer.setTransferTypeId(2);
                    newTransfer.setTransferStatusId(2);

                    tenmoService.makeTransfer(newTransfer, username, currentUser);

                }

                else {
                    System.out.println(red + "No valid username matched. If you still wish to send TE bucks, select option 4 from the menu and enter a matching username from the list." + colorReset);
                        mainMenu();
                }
            }

        } catch (NullPointerException e) {
            System.out.println(red + "User List Error" + colorReset);
        }
	}

	private void requestBucks() {
		// TODO Auto-generated method stub
		
	}

}
