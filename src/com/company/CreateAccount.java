package com.company;
import org.json.JSONObject;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class CreateAccount {
    private Encryption encryption;
    public CreateAccount(){
        encryption = Encryption.singleton();
    }


    // creates account
    public void create(){
        System.out.print("Username: ");
        Scanner scanner = new Scanner(System.in);
        String username = scanner.nextLine();
        // hashes username and password
        String hashedUsername = encryption.hashString(username);
        System.out.print("Password: ");
        String password = scanner.nextLine();
        //TODO create a password checking function
        createAccountFile();
        String hashedPassword = encryption.hashString(password);
        
        // creates files for user
        createUserFile(hashedUsername);
        JSONObject account = new JSONObject();
        account.put("username", hashedUsername);
        account.put("password", hashedPassword);
        writeAccountFile(account);

    }

    // creates account file
    private void createAccountFile(){
        try {
            File directory = new File("accounts");
            if (!directory.exists()){
                directory.mkdir();
            }
            File myObj = new File("accounts/accounts.json");
            if (myObj.createNewFile()) {

            } else {
                //System.out.println("Failed to create Accounts file");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

    }

    // writes new account to the account file
    private void writeAccountFile(JSONObject account){
        try {
            FileWriter file = new FileWriter("accounts/accounts.json", true);
            file.write(account.toString() + System.lineSeparator());
            file.close();
            //System.out.println("Account created");
        } catch (IOException e) {
            System.out.println("Failed to add account.");
            e.printStackTrace();
        }

    }

    // creates the user file
    private void createUserFile(String hashedUser){
        try {
            File directory = new File("files");
            if (!directory.exists()){
                directory.mkdir();
            }
            File file = new File("files/"+hashedUser+".json");
            if (file.createNewFile()) {
                System.out.println("Account Creation Succesful");
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

    }
}
