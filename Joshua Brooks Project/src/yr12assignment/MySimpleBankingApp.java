package yr12assignment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Scanner;
import java.util.stream.Stream;
import helpers.Keyboard;

/**
 * This is a simple menu-driven program for simple banking.
 * Transactions are read from a file and loaded into an array.
 * This application allows the user to display information,
 * send money, add money and exit the program.
 */
public class MySimpleBankingApp {
    /**
     * Declare variables that can be used by every method here! 
     */
    public static double balance = 250.00;
    public static String[] transactions = new String[100];
    public static String[] contacts = {"melb1234", "wchurchill456"};
    public static String transferContact = "";
    public static void main(String[] args) {
        
        // we are calling this method first to load data
        // do not remove this
       init();
       
        try (Scanner scanner = new Scanner(System.in)) {
            boolean quit = false;
            
            while (!quit) {
                System.out.println("");
                System.out.println("Welcome to STEDS Banking");
                // TODO: provide a menu of options:
                System.out.println("~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~");
                System.out.println("Please select and input a letter from the following options, must be capitalised");
                System.out.println("");
                System.out.println("'A' to Display Account Information.");
                // A. Display Account Information
                System.out.println("'B' to Withdraw Money.");
                // B. Withdraw Money
                System.out.println("'C' to Add Money.");
                // C. Add Money
                System.out.println("'D' to Send Money.");
                // D. Send Money
                System.out.println("'E' to Quit.");
                // E. Quit
                char userChoice = (Keyboard.readChar());

                // TODO: while userChoice != 'E'
                // trigger appropriate action based on user choice (use switch case statement)
                // ask the user for their  next choice
                // if the user enters 'D' then the program will display exit message and stop.

                switch(userChoice){
                    case 'A':
                        disAccInfo();
                        System.out.println("");
                        System.out.println("Please input your next choice?");
                        break;
                    case 'B':
                        withdrawMoney();
                        System.out.println("");
                        System.out.println("Please input your next choice?");
                        break;
                    case 'C':
                        addMoney();
                        System.out.println("");
                        System.out.println("Please input your next choice? ");
                        break;
                    case 'D':
                        sendMoney();
                        System.out.println("");
                        System.out.println("Please input your next choice? ");
                        break;
                    case 'E':
                    System.out.println("Thank you for using this banking application!");
                    System.out.println("Have a good day!");
                    quit = true;
                    // since quit has been set to false at the start, when 'D' is input by the user, the program will stop.
                    break;
                }
            }
        }   
    }

    // TODO: create custom methods here for: disAccInfo, addMoney, sendMoney

    public static void disAccInfo() {
        System.out.println("Balance: $" + balance); // displays the balance
        System.out.println("");
        System.out.println("Your transactions:");
        try {
            BufferedReader br = new BufferedReader(new FileReader("src/yr12assignment/MySimpleTransactions.txt"));
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void addMoney() {        
            System.out.println("Please input how much you would like to add to your account:");
            double addMoney = Keyboard.readDouble();
            if (addMoney <= 0) {
                System.out.println("Unfortunetly you have entered a value of zero or lower, please try again later.");
                // This validation check checks if the user has input a value greater than zero, if this is the case then an error message is output.
            } else {
                balance = balance + addMoney;
                System.out.println("$" + addMoney + " has been added to your account.");
               // Here the program adds the amount of money to the users balance, then outputs a success message to the user.
            }
        }

    public static void withdrawMoney() {
        System.out.println("Please input how much you would like to withdraw from your account:");
        double withdrawMoney = Keyboard.readDouble();
        if (withdrawMoney <= 0) {
            System.out.println("Unfortunetly you have entered a value of zero or lower, please try again later.");
            // This validation check checks if the user has input a value greater than zero, if this is the case then an error message is output.   
        } else if (withdrawMoney > balance) {
            System.out.println("Unfortunetly you have entered a value greater than what you have in your account.");
            // This validation check checks if the user has input a value greater than what is in their account, if this is the case then an error message is output.
        } else {
            balance = balance - withdrawMoney;
            System.out.println("$" + withdrawMoney + " has been withdrawn from your account.");
        }
    } 
    
    public static void sendMoney() {
        System.out.println("Please enter the corresponding letter to the transfer contact, must be capital");
        System.out.println("'A' for " + contacts[0]);
        System.out.println("'B' for " + contacts[1]);
        char contactChoice = (Keyboard.readChar());
        switch(contactChoice){
            case 'A':
                transferContact = contacts[0];
                break;
            case 'B':
                transferContact = contacts[0];
                break;
        }
        System.out.println("Please enter how much money you would like to transfer:");
        double sendMoneyAmount = Keyboard.readDouble();
        if (sendMoneyAmount <= 0) {
            System.out.println("Unfortunetly you have entered a value of zero or lower, please try again later."); 
        } else if (balance < sendMoneyAmount) {
            System.out.println("Unfortunetly you have entered a value greater than what you have in your account.");
        } else {
            balance = balance - sendMoneyAmount;
            System.out.println("Success!");
            System.out.println("Successfully transfered $" + sendMoneyAmount + " to " + transferContact); 
        }
    }

    /**
     * an advanced function written by Ms C to read data from a file and store it into transactions
     */
    public static void init() {
        // make sure you have a text file
        File file = new File("lessons\\src\\yr12assignment", "MySimpleTransactions.txt");
        if (file.exists()) {
            int lines;
            try (Stream<String> stream = Files.lines(file.toPath(), StandardCharsets.UTF_8)) {
                lines = (int) stream.count();
                stream.close();
                if (lines > 0) {
                    Scanner fileScan = new Scanner(file);
                    for (int i = 0; i < lines; i++) {
                        transactions[i] = fileScan.nextLine();
                    }
                    fileScan.close();
                }
            } catch (IOException e) {
                System.out.println("Err: Did not manage to load data.");
            }
        }
    }
}