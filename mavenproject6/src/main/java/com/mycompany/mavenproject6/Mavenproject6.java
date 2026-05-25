package com.mycompany.mavenproject6;

import java.util.Scanner;
import java.util.regex.Pattern;

// PART 1: LOGIN & REGISTRATION CLASS
public class Mavenproject6 {

    public static boolean checkUserName(String username) {
        return username.contains("_") && username.length() <= 5;
    }

    public static boolean checkPasswordComplexity(String password) {
        boolean hasCapital = false;
        boolean hasNumber = false;
        boolean hasSpecial = false;

        for (int i = 0; i < password.length(); i++) {
            char ch = password.charAt(i);
            if (Character.isUpperCase(ch)) hasCapital = true;
            if (Character.isDigit(ch)) hasNumber = true;
            if (!Character.isLetterOrDigit(ch)) hasSpecial = true;
        }
        return password.length() >= 8 && hasCapital && hasNumber && hasSpecial;
    }

    public static boolean checkCellPhoneNumber(String cellPhoneNumber) {
        String phoneRegex = "^\\+27\\d{9}$";
        return Pattern.matches(phoneRegex, cellPhoneNumber);
    }

    public static String registerUser(String username, String password, String cellPhoneNumber) {
        if (!checkUserName(username)) {
            return "Username is not correctly formatted, please ensure that your username contains an underscore and is no more than 5 characters in length.";
        }
        if (!checkPasswordComplexity(password)) {
            return "Password is not correctly formatted, please ensure that the password contains at least 8 characters, a capital letter, a number, and a special character.";
        }
        if (!checkCellPhoneNumber(cellPhoneNumber)) {
            return "Cell phone number is incorrectly formatted or does not contain international code, please correct the number and try again.";
        }
        return "Cell phone number successfully audited.";
    }

    public static boolean loginUser(String savedUsername, String savedPassword,
                                    String enteredUsername, String enteredPassword) {
        return savedUsername.equals(enteredUsername) && savedPassword.equals(enteredPassword);
    }

    // --- MAIN PROGRAM CONTROL FLOW ---
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.println("HELLO USER REGISTER");
        System.out.println("ENTER YOUR FIRST NAME:");
        String firstName = input.nextLine();
        System.out.println("ENTER YOUR LAST NAME:");
        String lastName = input.nextLine();
        System.out.println("ENTER YOUR USERNAME:");
        String username = input.nextLine();
        System.out.println("ENTER YOUR PASSWORD:");
        String password = input.nextLine();
        System.out.println("ENTER YOUR CELLPHONE NUMBER (+27):");
        String cellPhoneNumber = input.nextLine();

        String registrationStatus = registerUser(username, password, cellPhoneNumber);
        System.out.println(registrationStatus);

        if (registrationStatus.equals("Cell phone number successfully audited.")) {
            System.out.println("Registration successful.");
            System.out.println("--- LOGIN ---");
            System.out.println("ENTER YOUR USERNAME:");
            String enteredUsername = input.nextLine();
            System.out.println("ENTER YOUR PASSWORD:");
            String enteredPassword = input.nextLine();

            if (loginUser(username, password, enteredUsername, enteredPassword)) {
                System.out.println("Welcome " + firstName + ", " + lastName + " it is great to see you again.");
                System.out.println("Welcome to QuickChat.");
                System.out.println("How many messages do you want to send?");
                int numMessages = input.nextInt();
                input.nextLine(); // Clear buffer

                boolean chatRunning = true;
                // Array tracking object instances for the required print summary layout
                Message[] messageLog = new Message[numMessages];
                int currentMessageIndex = 0;
                
                while (chatRunning) {
                    System.out.println("--- QUICKCHAT MENU ---");
                    System.out.println("1) Send Message");
                    System.out.println("2) Show Messages");
                    System.out.println("3) Quit");
                    System.out.print("Choose an option: ");
                    String option = input.nextLine();
                    
                    if (option.equals("1")) {
                        if (currentMessageIndex >= numMessages) {
                            System.out.println("You have reached the maximum message count limit allocated.");
                            continue;
                        }

                        System.out.println(" Message " + (currentMessageIndex + 1) + " of " + numMessages + " ---");
                        Message msg = new Message();

                        // Recipient Validation Loop
                        while (true) {
                            System.out.println("Enter Recipient Cell (e.g. +27123456789):");
                            String recipient = input.nextLine();
                            msg.setRecipient(recipient);
                            
                            if (msg.checkRecipientCell()) {
                                System.out.println("Cell number accepted.");
                                break;
                            } else {
                                System.out.println("Invalid cell number. Must start with +27 and be exactly 12 characters total.");
                            }
                        }

                        // Text Limit Verification Loop (Updated with exact dynamic error string)
                        while (true) {
                            System.out.println("Enter Message Text (max 250 chars):");
                            String text = input.nextLine();
                            
                            if (text.length() <= 250) {
                                msg.setMessageText(text);
                                System.out.println("Message ready to send");
                                break;
                            } else {
                                int excess = text.length() - 250;
                                System.out.println("Message exceeds 250 characters by " + excess + " characters please reduce the size.");
                            }
                        }

                        String hash = msg.createMessageHash();
                        System.out.println("Message Hash: " + hash);
                        
                        System.out.println("Select Action:\n1) Send Message\n2) Discard Message\n3) Store Message");
                        String action = input.nextLine();
                        
                        String summary = msg.sentMessage(action);
                        System.out.println(summary);
                        
                        // Save instance reference to historical collection trace
                        messageLog[currentMessageIndex] = msg;
                        currentMessageIndex++;
                    } 
                    else if (option.equals("2")) {
                        // Triggers the required string assembly printing process method loop
                        System.out.println("--- HISTORICAL TRANSACTION LOG ---");
                        boolean entriesFound = false;
                        for (Message m : messageLog) {
                            if (m != null && m.printMessage2() != null) {
                                System.out.println(m.printMessage2());
                                entriesFound = true;
                            }
                        }
                        if (!entriesFound) {
                            System.out.println("No messages sent yet during this session execution runtime.");
                        }
                    } 
                    else if (option.equals("3")) {
                        System.out.println("Exiting QuickChat system. Goodbye!");
                        chatRunning = false;
                    } 
                    else {
                        System.out.println("Please enter 1, 2, or 3.");
                    }
                }
            } else {
                System.out.println("Username or password incorrect, please try again.");
            }
        } else {
            System.out.println("Registration failed. Please restart and enter valid details.");
        }
        input.close();
    }
}

// PART 2: OBJECT-ORIENTED MESSAGE CLASS
class Message {
    private String messageID;
    private String recipient;
    private String messageText;
    private String dispatchStatus = null; 

    public static int totalMessages = 0;

    public Message() {
        long id = (long) (Math.random() * 9000000000L) + 1000000000L;
        this.messageID = String.valueOf(id);
    }

    public boolean checkMessageID() {
        return this.messageID.length() <= 10;
    }

    public boolean checkRecipientCell() {
        String phoneRegex = "^\\+27\\d{9}$";
        return Pattern.matches(phoneRegex, this.recipient);
    }

    // Fixed Hash layout calculations rule mapping
    public String createMessageHash() {
        String lastTwo = this.messageID.substring(this.messageID.length() - 2);
        int totalCharCount = this.messageText.length();
        
        String[] words = this.messageText.trim().split("\\s+");
        String firstWord = words[0];
        String lastWord = words[words.length - 1];
        
        return (lastTwo + totalCharCount + firstWord + lastWord).toUpperCase();
    }

    public String sentMessage(String option) {
        if (option.equals("1")) {
            totalMessages++;
            this.dispatchStatus = "Sent";
            return "Message successfully sent. Total Global Messages Sent: " + totalMessages;
        } else if (option.equals("2")) {
            this.dispatchStatus = "Discarded";
            return "Message Discarded";
        } else if (option.equals("3")) {
            this.dispatchStatus = "Stored";
            return "Message Stored";
        } else {
            return "Invalid Option";
        }
    }

    // REQUIRED ASSIGNMENT METHOD: Compiles structured log tracing sequence
    public String printMessage2() {
        if (this.dispatchStatus == null || !this.dispatchStatus.equals("Sent")) {
            return null; 
        }
        return "ID: " + this.messageID + 
               " | Hash: " + createMessageHash() + 
               " | To: " + this.recipient + 
               " | Content: " + this.messageText;
    }

    public static int returnTotalMessages() {
        return totalMessages;
    }

    // SETTERS
    public void setRecipient(String recipient) { 
        this.recipient = recipient; 
    }
    
    public void setMessageText(String messageText) { 
        this.messageText = messageText; 
    }
}