package com.techelevator.tenmo.services;


import com.techelevator.tenmo.model.UserCredentials;

import java.math.BigDecimal;
import java.util.Scanner;

public class ConsoleService {
    // Console utilities (colors, etc)
    public static final String ANSI_BOLD = "\u001B[1m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_PURPLE = "\033[0;35m";


    private final Scanner scanner = new Scanner(System.in);

    public int promptForMenuSelection(String prompt) {
        int menuSelection;
        System.out.print(prompt);
        try {
            menuSelection = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            menuSelection = -1;
        }
        return menuSelection;
    }

    public void printGreeting() {
        System.out.println(ANSI_BOLD + ANSI_GREEN +"⠀⠀⠀⠀⣠⡤⢤⡄⠀⠀⠀⠀ ");
        System.out.println("⠀⠀⠀⣾⣿⠂⠀⣇⣀⠀⠀⠀");
        System.out.println("⠀⣠⠖⠉⠀⠀⠀⠀⠀⠉⠙⢢");
        System.out.println("⠀⣴⠃⠀⢰⣮⠿⠿⢍⣻⡒⣶⠃");
        System.out.println("⢀⡿⡄⠀⠈⠳⢤⣀⠀⠀⠉⠁⠀");
        System.out.println("⠈⢗⠝⡢⣄⡀⠀⠀⠉⠓⠢⡀⠀");
        System.out.println("⠀⠀⠀⠙⠮⢔⣝⣗⠦⣄⠀⠀⠘⡆");
        System.out.println("⠀⠀⠀⡀⠀⠀⠀⠉⢳⠬⡇⠀⠀⡱");
        System.out.println("⢀⡶⡾⠉⠒⠢⠤⠤⠼⠟⠁⠀⢠⡇");
        System.out.println("⠺⣍⡣⣄⣀⣀⣀⠀⠀⣠⣤⣴⡟⠁");
        System.out.println("⠀⠈⠙⠲⠵⢽⡹⡇⠀⢹⠟⠉⠀⠀");
        System.out.println("⠀⠀⠀⠀⠀⢸⡦⣷⢖⣾⠀⠀⠀⠀");
        System.out.println("⠀⠀⠀⠀⠀⠈⠛⠓⠋⠁⠀⠀⠀⠀" + ANSI_RESET);
        System.out.println(ANSI_BOLD + ANSI_BLUE + "**********************************");
        System.out.println(ANSI_BOLD + ANSI_BLUE + "**********************************");
        System.out.println(ANSI_BOLD + ANSI_GREEN + "******* WELCOME TO TENMO! *******" + ANSI_RESET);
        System.out.println(ANSI_YELLOW + "*** A Tech Elevator Exclusive ***" + ANSI_RESET);
        System.out.println(ANSI_BOLD + ANSI_BLUE + "**********************************");
        System.out.println(ANSI_BOLD + ANSI_BLUE + "**********************************" + ANSI_RESET);

    }

    public void printLoginMenu() {
        System.out.println();
        System.out.println(ANSI_YELLOW + "1: Register");
        System.out.println("2: Login");
        System.out.println("0: Exit" + ANSI_RESET);
        System.out.println();
    }

    public void printMainMenu() {
        System.out.println();
        System.out.println(ANSI_PURPLE + "1: View your current balance");
        System.out.println("2: View your past transfers");
        System.out.println("3: View your pending requests");
        System.out.println("4: Send TE bucks");
        System.out.println("5: Request TE bucks" + ANSI_RESET);
        System.out.println(ANSI_RED + "0: Exit"+ ANSI_RESET);
        System.out.println();
    }

    public void transactionMenu() {
        System.out.println();
        System.out.println(ANSI_YELLOW + "1: View Transfer Details");
        System.out.println("0: Exit" + ANSI_RESET);
        System.out.println();
    }

    public UserCredentials promptForCredentials() {
        String username = promptForString(ANSI_PURPLE + "Username: ");
        String password = promptForString("Password: "+ ANSI_RESET);
        return new UserCredentials(username, password);
    }

    public String promptForString(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    public int promptForInt(String prompt) {
        System.out.print(prompt);
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println(ANSI_RED + "Please enter a number." + ANSI_RESET);
            }
        }
    }

    public BigDecimal promptForBigDecimal(String prompt) {
        System.out.print(prompt);
        while (true) {
            try {
                return new BigDecimal(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println(ANSI_RED + "Please enter a decimal number."+ ANSI_RESET);
            }
        }
    }

    public void pause() {
        System.out.println(ANSI_RED + "Press Enter to continue..." + ANSI_RESET);
        scanner.nextLine();
    }

    public void printErrorMessage() {
        System.out.println(ANSI_RED + "An error occurred. Check the log for details." + ANSI_RESET);
    }



}
