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
    private int lower, upper, special, number;

    public AccountSession(User user){
        this.user = user;
        passwordGenerator = new PasswordGenerator();
        lower = 10;
        upper = 2;
        special = 2;
        number = 2;
    }

    // Queries the user on what action they want to take. Add password, find password, or list websites
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
        boolean invalidInput = true;
        // checks if user wants to change the current stored password parameters
        while(invalidInput){
            System.out.print("Creating new password. \n  Current parameters are:\n    Lower Case: "+lower+"\n    Upper Case: "+upper+"\n    Special Chars: "+special+"\n    Numbers: "+number+"\n  Do you want to change custom password parameters: \n  1. Yes\n  2. No\n");
            String outcome = queryUser();
            switch (outcome) {
                case "1":
                    setNewParams();
                    break;
                case "2":
                    invalidInput = false;
                    break;


                default:
                    System.out.println("Not a valid input");
            }

        }
        System.out.print("Creating new password. \n  Please give website name: ");
        String website = queryUser();
        System.out.print("  Please give Username: ");
        String username = queryUser();


        String password = passwordGenerator.generatePass(lower, upper, special, number);

        System.out.println("Password created is:\n" +password);

        
        

        JSONObject account = new JSONObject();
        // writes all the website/pass info to a file
        account.put("website", encryption.encrypt(website));
        account.put("username", encryption.encrypt(username));
        account.put("password", encryption.encrypt(password));

        writePassFile(account);


    }

    // querys user for new password generation parameters
    private void setNewParams(){
        int counter = 0;
        while(counter < 4){
            switch (counter){
                case 0:
                    System.out.println("New Lower case character amount: ");
                    lower = getInt();
                    counter++;
                    break;
                case 1:
                    System.out.println("New Upper case character amount: ");
                    upper = getInt();
                    counter++;
                    break;
                case 2:
                    System.out.println("New Special character amount: ");
                    special = getInt();
                    counter++;
                    break;
                case 3:
                    System.out.println("New Number amount: ");
                    number = getInt();
                    counter++;
                    break;
                default:

            }
        }

    }

    // writes to the user file a new website, username and password
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

    // looks up and displays the website username and password based on the given website name
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

    // lists every website stored in the users file
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

    // querys user for string
    private String queryUser(){
        Scanner scanner = new Scanner(System.in);
        String response = scanner.nextLine();
        return response;
    }

    // querys user for integer
    private int getInt(){
        int response;
        while(true) {
            Scanner scanner = new Scanner(System.in);
            if (scanner.hasNextInt()) {
                response = scanner.nextInt();
                break;
            } else {
                System.out.println("Please input a valid Integer");
            }
        }
        return response;
    }
}
