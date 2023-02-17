package com.company;

import java.util.Scanner;

public class CreateAccount {
    public CreateAccount(){

    }


    // TODO creates account
    public void create(){
        System.out.print("Username: ");
        Scanner scanner = new Scanner(System.in);
        String username = scanner.nextLine();
        String hashedUsername = encryptThisString(username);
        //System.out.println("you gave us " + username);
        System.out.print("Password: ");
        String password = scanner.nextLine();

        //TODO create a password checking function

        String hashedPassword = encryptThisString(password);

    }
}
