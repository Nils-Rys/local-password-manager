package com.company;

import java.io.File;
import java.io.IOException;
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
        createFile(hashedUsername);


    }

    private void createFile(String hashedUser){
        try {
            File myObj = new File("files/"+hashedUser+".json");
            if (myObj.createNewFile()) {
                System.out.println("File created: " + myObj.getName());
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

    }
}
