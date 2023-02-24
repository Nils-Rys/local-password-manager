package com.company;
import org.json.JSONObject;
import java.io.File;
import java.io.FileWriter;
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

        createAccountFile();
        String hashedPassword = encryption.encryptThisString(password);
        createUserFile(hashedUsername);

        JSONObject account = new JSONObject();
        account.put("username", hashedUsername);
        account.put("password", hashedPassword);
        writeAccountFile(account);

    }

    private void createAccountFile(){
        try {
            File directory = new File("accounts");
            if (!directory.exists()){
                directory.mkdir();
            }
            File myObj = new File("accounts/accounts.json");
            if (myObj.createNewFile()) {

            } else {
                System.out.println("Failed to create Accounts file");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

    }

    private void writeAccountFile(JSONObject account){
        try {
            FileWriter file = new FileWriter("accounts/accounts.json", true);
            file.write(account.toString() + System.lineSeparator());
            file.close();
            System.out.println("Account created");
        } catch (IOException e) {
            System.out.println("Failed to add account.");
            e.printStackTrace();
        }

    }

    private void createUserFile(String hashedUser){
        try {
            File directory = new File("files");
            if (!directory.exists()){
                directory.mkdir();
            }
            File file = new File("files/"+hashedUser+".json");
            if (file.createNewFile()) {
                System.out.println("File created: " + file.getName());
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

    }
}
