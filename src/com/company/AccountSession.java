package com.company;

import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class AccountSession {

    private User user;
    private Encryption encryption = Encryption.singleton();
    private PasswordGenerator passwordGenerator;

    public AccountSession(User user){
        this.user = user;
        passwordGenerator = new PasswordGenerator();
    }

    public void logic(){
        while (true) {
            System.out.println("Please input the corresponding number\n  1. Add new Password\n  2. Find password\n  3. List websites");
            String outcome = queryUser();
            switch (outcome) {
                case "1":
                    createNewPass();
                    break;
                case "2":
                    findPass();
                    break;
                case "3":
                    listWebsite();
                    break;

                default:
                    System.out.println("Not a valid input");
            }
        }
    }


    private void createNewPass(){
        System.out.print("Creating new password. \n  Please give website name: ");
        String website = queryUser();
        System.out.print("  Please give Username: ");
        String username = queryUser();

        //todo fancy password gen

        String password = passwordGenerator.generatePass();

        System.out.println("Password created is:\n" +password);
//        System.out.println("web created is:\n" +website);
//        System.out.println("user created is:\n" +username);
        
        

        JSONObject account = new JSONObject();
        // writes all the website/pass info to a file
        account.put("website", encryption.encrypt(website));
        account.put("username", encryption.encrypt(username));
        account.put("password", encryption.encrypt(password));

        writePassFile(account);


    }

    private void writePassFile(JSONObject account){
        try {
            FileWriter file = new FileWriter("files/"+ user.getUserHash()+".json", true);
            file.write(account.toString()+System.lineSeparator());
            file.close();
        } catch (IOException e) {
            System.out.println("Failed to log new Password.");
            e.printStackTrace();
        }

    }

    private void findPass(){
        System.out.print("Finding Password. \n  Please give website name: ");
        String website = queryUser();
        searchWebsite(website);


    }

    private void searchWebsite(String website){
        try {
            File file = new File("files/"+ user.getUserHash()+".json");

            Scanner reader = new Scanner(file);
            boolean foundWeb = false;

            while (reader.hasNextLine() && !foundWeb){
            	
                JSONObject temp = new JSONObject(reader.nextLine());
                if (encryption.decrypt(temp.get("website").toString()).equalsIgnoreCase(website)){
                    System.out.println("Website: " + encryption.decrypt(temp.get("website").toString()));
                    System.out.println("Username: " + encryption.decrypt(temp.get("username").toString()));
                    System.out.println("Password: " + encryption.decrypt(temp.get("password").toString()));
                    foundWeb = true;
                }
            }
            if (!foundWeb){
                System.out.println("Website not found");
            }
        } catch (IOException e) {
            System.out.println("Website Not found.");
        }

    }

    private void listWebsite(){
        try {
            System.out.println("Websites: ");
            File file = new File("files/"+ user.getUserHash()+".json");

            Scanner reader = new Scanner(file);

            while (reader.hasNextLine() ){

                JSONObject temp = new JSONObject(reader.nextLine());
                System.out.println(encryption.decrypt(temp.get("website").toString()));
            }
        } catch (IOException e) {
            System.out.println("Websites Not found.");
        }

    }

    private String queryUser(){
        Scanner scanner = new Scanner(System.in);
        String response = scanner.nextLine();
        return response;
    }
}
