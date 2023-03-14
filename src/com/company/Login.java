package com.company;

import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class Login {
    private User user;
    private Encryption encryption;
    public Login(){
        user = new User();
        encryption = Encryption.singleton();
    }



    public void signIn(){
        try {
            File directory = new File("accounts");
            if (!directory.exists()){
                directory.mkdir();
            }
            File file = new File("accounts/accounts.json");

            System.out.print("Username: ");
            String username = queryUser();
            String hashedUsername = encryption.hashString(username);
            //System.out.println("you gave us " + username);
            System.out.print("Password: ");
            String password = queryUser();
            String hashedPassword = encryption.hashString(password);




            //TODO check if hashes are equivalent to stored hashes
            if (checkUser(file, hashedUsername, hashedPassword)){
                encryption.initializeKey(username, password);
                System.out.println("Logged In");
                user.setUserHash(hashedUsername);
                AccountSession accountSession = new AccountSession(user);
                accountSession.logic();
            }else {
                System.out.println("Account not found");
            }

        } catch (IOException e) {
            System.out.println("Account Not found.");
        }

    }

    private String queryUser(){
        Scanner scanner = new Scanner(System.in);
        String response = scanner.nextLine();
        return response;
    }

    private boolean checkUser(File file, String hashedUser, String hashedPass) throws FileNotFoundException {
        Scanner reader = new Scanner(file);
        boolean foundAccount = false;

        while (reader.hasNextLine() && !foundAccount){

            JSONObject temp = new JSONObject(reader.nextLine());
            if (temp.get("username").equals(hashedUser)){
                if (temp.get("password").equals(hashedPass)){
                    foundAccount = true;
                }
            }
        }
        return foundAccount;

    }
}
