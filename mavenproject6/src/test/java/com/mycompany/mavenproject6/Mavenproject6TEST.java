package com.mycompany.mavenproject6;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class Mavenproject6Test {

    // ==========================================
    //           PART 1: REGISTRATION TESTS
    // ==========================================

    @Test
    public void testCheckUserName_Valid() {
        // Valid: Contains underscore and is <= 5 characters
        assertTrue(Mavenproject6.checkUserName("kyl_1"));
    }

    @Test
    public void testCheckUserName_Invalid_NoUnderscore() {
        // Invalid: No underscore
        assertFalse(Mavenproject6.checkUserName("kyle"));
    }

    @Test
    public void testCheckUserName_Invalid_TooLong() {
        // Invalid: Longer than 5 characters
        assertFalse(Mavenproject6.checkUserName("kyle_onuoha"));
    }

    @Test
    public void testCheckPasswordComplexity_Valid() {
        // Valid: >= 8 chars, has capital, has number, has special character
        assertTrue(Mavenproject6.checkPasswordComplexity("Ch@ng3Me"));
    }

    @Test
    public void testCheckPasswordComplexity_Invalid() {
        // Invalid: Missing uppercase, number, and special character
        assertFalse(Mavenproject6.checkPasswordComplexity("password"));
    }

    @Test
    public void testRegisterUser_Success() {
        String result = Mavenproject6.registerUser("kyl_1", "Ch@ng3Me", "+27731683002");
        assertEquals("Username and password successfully captured", result);
    }

    @Test
    public void testRegisterUser_FailedUsername() {
        String result = Mavenproject6.registerUser("kyleeeeee", "Ch@ng3Me", "+27731683002");
        assertTrue(result.contains("Username is not correctly formatted"));
    }


    // ==========================================
    //           PART 2: MESSAGE SYSTEM TESTS
    // ==========================================

    @Test
    public void testMessageIDGeneration() {
        Message msg = new Message();
        // Verifies the ID auto-generates correctly and satisfies checkMessageID()
        assertTrue(msg.checkMessageID());
    }

    @Test
    public void testCheckRecipientCell_ValidFromAssignment() {
        Message msg = new Message();
        
        // Testing with the exact Test Case 1 recipient cell number from your document video
        msg.setRecipient("+27731683002");
        assertTrue(msg.checkRecipientCell(), "Should pass validation for a valid international SA cell number");
    }

    @Test
    public void testCheckRecipientCell_Invalid() {
        Message msg = new Message();
        
        // Invalid: Missing the '+' prefix or wrong formatting rules
        msg.setRecipient("0731683002");
        assertFalse(msg.checkRecipientCell());
    }

    @Test
    public void testCreateMessageHash_AssignmentMatch() {
        Message msg = new Message();
        
        // Inject a controlled message ID sequence via regular string logic or direct observation
        // Because ID is random, let's verify if hash properly formats 
        // format rules: Last 2 of ID + Total Char Count + First 4 of First Word + Last 2 of Last Word
        msg.setMessageText("Hi Mike, can you join us for dinner tonight?");
        
        String hash = msg.createMessageHash();
        assertNotNull(hash);
        
        // Check structural properties: Total characters count in text string is 44
        // The first word is "Hi", the last word is "tonight?" (ends with 'HT?')
        assertTrue(hash.contains("44"));
        assertTrue(hash.contains("HI")); 
    }

    @Test
    public void testSentMessage_Actions() {
        Message msg = new Message();

        // Rubric Expectation: User selects '1' -> Send Message
        assertEquals("Message successfully sent.", msg.sentMessage("1"));

        // Rubric Expectation: User selects '2' -> Discard Message
        assertEquals("Press 0 to delete the message", msg.sentMessage("2"));

        // Rubric Expectation: User selects '3' -> Store Message
        assertEquals("Message successfully stored", msg.sentMessage("3"));
    }

    @Test
    public void testPrintMessages_LogsOnlySent() {
        Message msg = new Message();
        msg.setRecipient("+27731683002");
        msg.setMessageText("Test Message");
        
        // Before sending, status is null. printMessages() must return null
        assertNull(msg.printMessages());
        
        // After sending, status is "Sent". printMessages() must compile a structured text log layout
        msg.sentMessage("1");
        assertNotNull(msg.printMessages());
        assertTrue(msg.printMessages().contains("Recipient: +27731683002"));
    }
}
