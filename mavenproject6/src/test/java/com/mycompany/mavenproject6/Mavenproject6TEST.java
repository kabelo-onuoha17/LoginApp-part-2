package com.mycompany.mavenproject6;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class Mavenproject6TEST {

    // --- USERNAME VALIDATION TESTS ---
    @Test
    public void testCheckUserName_Valid() {
        assertTrue(Mavenproject6.checkUserName("u_ser"), "Should pass: contains underscore and <= 5 chars.");
    }

    @Test
    public void testCheckUserName_TooLong() {
        assertFalse(Mavenproject6.checkUserName("user_name"), "Should fail: exceeds 5 characters.");
    }

    @Test
    public void testCheckUserName_NoUnderscore() {
        assertFalse(Mavenproject6.checkUserName("user1"), "Should fail: missing an underscore.");
    }

    // --- PASSWORD COMPLEXITY TESTS ---
    @Test
    public void testCheckPasswordComplexity_Valid() {
        assertTrue(Mavenproject6.checkPasswordComplexity("Ch@nge123"), "Should pass: meets length, case, digit, and special char rules.");
    }

    @Test
    public void testCheckPasswordComplexity_TooShort() {
        assertFalse(Mavenproject6.checkPasswordComplexity("Ch@n1"), "Should fail: less than 8 characters.");
    }

    @Test
    public void testCheckPasswordComplexity_NoCapital() {
        assertFalse(Mavenproject6.checkPasswordComplexity("ch@nge123"), "Should fail: missing uppercase letter.");
    }

    @Test
    public void testCheckPasswordComplexity_NoNumber() {
        assertFalse(Mavenproject6.checkPasswordComplexity("Ch@ngesf"), "Should fail: missing a digit.");
    }

    @Test
    public void testCheckPasswordComplexity_NoSpecial() {
        assertFalse(Mavenproject6.checkPasswordComplexity("Change123"), "Should fail: missing a special character.");
    }

    // --- PHONE NUMBER VALIDATION TESTS ---
    @Test
    public void testCheckCellPhoneNumber_Valid() {
        assertTrue(Mavenproject6.checkCellPhoneNumber("+27123456789"), "Should pass: exact +27 prefix and 9 digits.");
    }

    @Test
    public void testCheckCellPhoneNumber_InvalidPrefix() {
        assertFalse(Mavenproject6.checkCellPhoneNumber("0123456789"), "Should fail: incorrect international format.");
    }

    @Test
    public void testCheckCellPhoneNumber_InvalidLength() {
        assertFalse(Mavenproject6.checkCellPhoneNumber("+2712345"), "Should fail: incomplete number string length.");
    }

    // --- REGISTRATION SUMMARY STATUS TESTS ---
    @Test
    public void testRegisterUser_Success() {
        String result = Mavenproject6.registerUser("m_sg", "P@ssword1", "+27123456789");
        assertEquals("Cell phone number successfully audited.", result);
    }

    @Test
    public void testRegisterUser_FailedUsername() {
        String result = Mavenproject6.registerUser("long_username", "P@ssword1", "+27123456789");
        assertTrue(result.contains("Username is not correctly formatted"));
    }

    // --- LOGIN VERIFICATION TESTS ---
    @Test
    public void testLoginUser_Success() {
        assertTrue(Mavenproject6.loginUser("abc", "123", "abc", "123"), "Matching credentials must authorize successfully.");
    }

    @Test
    public void testLoginUser_Failure() {
        assertFalse(Mavenproject6.loginUser("abc", "123", "abc", "wrong"), "Mismatched credentials must deny authorization.");
    }
}