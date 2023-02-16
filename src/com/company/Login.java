package com.company;

import java.util.Scanner;

public class Login {
    public Login(){

    }

    public void signIn(){
        System.out.print("Username: ");
        Scanner scanner = new Scanner(System.in);
        String username = scanner.nextLine();
        //System.out.println("you gave us " + username);
        System.out.print("Password: ");
        String password = scanner.nextLine();


    }
}
