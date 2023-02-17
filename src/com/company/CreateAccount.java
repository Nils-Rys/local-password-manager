package com.company;

import java.util.Scanner;

public class CreateAccount {
    private Encryption encryption;
    public CreateAccount(){
        encryption = new Encryption();
    }


    // TODO creates account
    public void create(){
        System.out.print("Username: ");
        Scanner scanner = new Scanner(System.in);
        String username = scanner.nextLine();
        String hashedUsername = encryption.encryptThisString(username);
        //System.out.println("you gave us " + username);
        System.out.print("Password: ");
        String password = scanner.nextLine();

        //TODO create a password checking function

        String hashedPassword = encryption.encryptThisString(password);


    }
}
