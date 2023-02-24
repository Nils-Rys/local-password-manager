package com.company;

public class Main {

    // Milestone 1 	Frame work, pseudocode, how to create keys and encryption method

    public static void main(String[] args) {
        User user = new User();
        Login login = new Login(user);
        CreateAccount createAccount = new CreateAccount();
        // TODO implement database
        //DatabaseManager databaseManager = new DatabaseManager();

        if (args.length == 0){
            System.out.println("Expecting at least one arg. H for help");
        }else{
            switch (args[0]){
                case "H":
                case "h":
                    System.out.println("Valid Args:\n   H - displays all valid args\n   L - Login to existing account\n   C - Create an account");
                    break;
                case "l":
                case "L":
                    // todo LOGIN
                    login.signIn();
                    break;
                case "c":
                case "C":
                    // todo create account
                    createAccount.create();
                    break;
                default:
                    System.out.println("Invalid Arg");
                    break;

            }
        }
	// write your code here
    }
}
