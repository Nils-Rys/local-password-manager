package com.company;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

public class Login {
    private User user;
    private Encryption encryption;
    public Login(User user){
        this.user = user;
        encryption = new Encryption();
    }



    public void signIn(){
        System.out.print("Username: ");
        Scanner scanner = new Scanner(System.in);
        String username = scanner.nextLine();
        String hashedUsername = encryption.encryptThisString(username);
        //System.out.println("you gave us " + username);
        System.out.print("Password: ");
        String password = scanner.nextLine();
        String hashedPassword = encryption.encryptThisString(password);


        //TODO check if hashes are equivalent to stored hashes
        boolean exists= true;
        if (exists){
            user.setUserHash(hashedUsername);
        }


    }
}
