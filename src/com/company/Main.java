package com.company;

import java.util.Scanner;

public class Main {

    // Milestone 1 	Frame work, pseudocode, how to create keys and encryption method

    public static void main(String[] args) {
        Login login = new Login();
        CreateAccount createAccount = new CreateAccount();
        Encryption encryption = Encryption.singleton();
        boolean session = true;
        // loop to query the user on what action they want to perform. Create account, Login, display valid arguments
        while (session){
            System.out.println("What do you intend to do? H for help");

            Scanner scanner = new Scanner(System.in);
            String response = scanner.nextLine();

	        
            switch (response){
                case "H":
                case "h":
                    System.out.println("Valid Args:\n   H - displays all valid args\n   L - Login to existing account\n   C - Create an account");
                    break;
                case "l":
                case "L":
                    login.signIn();
                    break;
                case "c":
                case "C":
                    createAccount.create();
                    break;
                default:
                    System.out.println("Invalid Arg");
                    break;
	
	            
	        }
        }
    }
}


