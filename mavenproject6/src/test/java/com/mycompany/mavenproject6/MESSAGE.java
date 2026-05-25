package com.mycompany.mavenproject6;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author kabelo
 */
public class MESSAGE {

    private Message message;

    @BeforeEach
    public void setUp() {
        // Runs cleanly before each isolated test to prevent cross-contamination of states
        message = new Message();
    }

    @Test
    public void testMessageIDBounds() {
        assertTrue(message.checkMessageID(), "The generated tracking ID string must scale at 10 characters or less.");
    }

    @Test
    public void testRecipientCellVerification() {
        message.setRecipient("+27831234567");
        assertTrue(message.checkRecipientCell(), "Standard pattern matching rules should recognize valid formatting.");
        
        message.setRecipient("+26312345678");
        assertFalse(message.checkRecipientCell(), "Mismatched country phone prefixes should instantly fail verification.");
    }

    @Test
    public void testCreateMessageHash() {
        // Hardcoding a baseline text structure to predict character counters and extraction results
        message.setMessageText("Java programming is fun");
        
        String hashResult = message.createMessageHash();
        
        // Character count of "Java programming is fun" = 23 characters
        // First word = "Java", Last word = "fun"
        // Target hash ends with: "23JAVAFUN"
        assertTrue(hashResult.endsWith("23JAVAFUN"), "Hash must contain text character length, first word, and last word up-cased.");
    }

    @Test
    public void testSentMessage_Actions() {
        int initialGlobalCount = Message.returnTotalMessages();
        
        // Test Option 1: Send
        String sendResponse = message.sentMessage("1");
        assertTrue(sendResponse.contains("successfully sent"));
        assertEquals(initialGlobalCount + 1, Message.returnTotalMessages(), "Global counter must register sent traffic.");
        assertNotNull(message.printMessage2(), "Sent records should be discoverable inside print tracking methods.");

        // Test Option 2: Discard
        Message discardMessage = new Message();
        String discardResponse = discardMessage.sentMessage("2");
        assertEquals("Message Discarded", discardResponse);
        assertNull(discardMessage.printMessage2(), "Discarded instances must not print structural payload summaries.");

        // Test Option 3: Store
        Message storeMessage = new Message();
        String storeResponse = storeMessage.sentMessage("3");
        assertEquals("Message Stored", storeResponse);
        assertNull(storeMessage.printMessage2(), "Stored instances must remain hidden from direct dynamic sent-logs.");
    }

    @Test
    public void testPrintMessage2_Format() {
        message.setRecipient("+27123456789");
        message.setMessageText("Unit Testing");
        message.sentMessage("1"); // Toggles status flag to enable log compilation

        String outputLog = message.printMessage2();
        
        assertNotNull(outputLog, "Active log pipelines must construct clear terminal summary string sequences.");
        assertTrue(outputLog.contains("To: +27123456789"), "Generated summary string must contain the recipient data field.");
        assertTrue(outputLog.contains("Content: Unit Testing"), "Generated summary string must map explicit message data fields.");
    }
}