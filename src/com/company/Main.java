package com.company;

public class Main {

    // Milestone 1 	Frame work, pseudocode, how to create keys and encryption method

    public static void main(String[] args) {
        User user = new User();
        Login login = new Login(user);
        CreateAccount createAccount = new CreateAccount();
        Encryption encryption = Encryption.singleton();
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
                case "t":
                    encryption.initializeKey("larry", "password");
                    String temp = encryption.encrypt("This is a long test, over 16 chars");
                    System.out.println("Encryption: " + temp);
                    temp = encryption.decrypt(temp);
                    System.out.println("Decryption: " + temp);
                    System.out.println("----------------------------------------------------------------");

                    temp = encryption.encrypt("This is a long test, over 16 chars aseteastasehtals;htdkjlsahtaskljtehsajkdthalksjthase");
                    System.out.println("encryption: " + temp);
                    temp = encryption.decrypt(temp);
                    System.out.println("decryption: " + temp);
                    System.out.println("----------------------------------------------------------------");

                    temp = encryption.encrypt("this is a test a");
                    System.out.println("encryption: " + temp);
                    temp = encryption.decrypt(temp);
                    System.out.println("decryption: " + temp);
                    System.out.println("----------------------------------------------------------------");

                    temp = encryption.encrypt("this is a test athis is a test athis is a test a");
                    System.out.println("encryption: " + temp);
                    temp = encryption.decrypt(temp);
                    System.out.println("decryption: " + temp);
                    System.out.println("----------------------------------------------------------------");

                    temp = encryption.encrypt("a");
                    System.out.println("encryption: " + temp);
                    temp = encryption.decrypt(temp);
                    System.out.println("decryption: " + temp);
                    System.out.println("----------------------------------------------------------------");
                    break;
                default:
                    System.out.println("Invalid Arg");
                    break;

            }
        }
	// write your code here
    }
}


