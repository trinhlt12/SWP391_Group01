package com.embanthe.util;

import org.mindrot.jbcrypt.BCrypt;

import java.security.SecureRandom;

public class PasswordUtil {

    private static final int LOG_ROUNDS = 12;

    /**
     * Hash password using BCrypt
     */
    public static String hash(String plainPassword) {
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt(LOG_ROUNDS));
    }

    /**
     * Verify password against hashed password
     */
    public static boolean check(String plainPassword, String hashedPassword) {
        try {
            return BCrypt.checkpw(plainPassword, hashedPassword);
        } catch (Exception e) {
            return false;
        }
    }
    public static String generateRandomPassword(int length) {
        String chars = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz!@#";
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(chars.charAt(random.nextInt(chars.length())));
        }
        return sb.toString();
    }

    // Generate hash for testing
    public static void main(String[] args) {
        String password = "123456";
        String hashed = hash(password);

        System.out.println("=== PASSWORD HASH GENERATOR ===");
        System.out.println("Plain Password: " + password);
        System.out.println("Hashed Password: " + hashed);
        System.out.println("\n✅ Verification: " + check(password, hashed));
        System.out.println("❌ Wrong Password: " + check("wrong", hashed));

        System.out.println("\n📋 Copy this hash to SQL INSERT:");
        System.out.println(hashed);
    }
}