package common;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public class PasswordUtils {

    // Method to generate a random salt
    public static byte[] generateSalt() {
        byte[] salt = new byte[16]; // 16 bytes is a good size for a salt
        SecureRandom random = new SecureRandom();
        random.nextBytes(salt);
        return salt;
    }

    // Method to hash a password using SHA-256 and a salt
    public static String hashPassword(String password, byte[] salt) {
        String hashedPassword = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(salt);
            byte[] hashedBytes = md.digest(password.getBytes());
            hashedPassword = Base64.getEncoder().encodeToString(hashedBytes);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return hashedPassword;
    }

    

    // Method to compare a candidate password to a stored hash
    public static boolean verifyPassword(String candidatePassword, String storedHash, byte[] salt) {
        String candidateHash = hashPassword(candidatePassword, salt);
        return storedHash.equals(candidateHash);
    }
    
    public static void main(String[] args) {
        // Example usage
        String password = "1234";

        // Generate a salt for the user
        byte[] salt = generateSalt();

        // Hash the password with the salt
        String hashedPassword = hashPassword(password, salt);

        // Print out the salt and hashed password for storage
        System.out.println("Salt: " + Base64.getEncoder().encodeToString(salt));
        System.out.println("Hashed Password: " + hashedPassword);
        
        
        byte[] get_salt = Base64.getDecoder().decode("EULWBhWDgbo8NA2cK4SBUw==");
        hashedPassword = hashPassword(password, get_salt);
        System.out.println("Salt: " + Base64.getEncoder().encodeToString(get_salt));
        System.out.println("Hashed Password: " + hashedPassword);
        
        /*
        String storedHash = "StoredHashHere"; // Retrieve the stored hashed password from the database
        byte[] get_salt = Base64.getDecoder().decode("StoredSaltHere"); // Retrieve the stored salt from the database

        String candidatePassword = "CandidatePassword123"; // Password entered by the user during login

        // Verify the password
        if (verifyPassword(candidatePassword, storedHash, get_salt)) {
            System.out.println("Password is correct!");
        } else {
            System.out.println("Password is incorrect!");
        }
        */
    }

    
    
}

