//This class will hash passwords and output it to a file
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * HashPassword class to instantiate an objects which hashes the passwords
 * for storing in files and to read the hashed passwords from the file
 */
public class HashPassword {
    /**
     * Takes in the password of an account and hashes the password into SHA256 format
     * @param input The password of an account
     * @return The bytes of a hashed password
     * @throws NoSuchAlgorithmException When an algorithm is unavailable in environment
     */
    public byte[] SHA256(String input) throws NoSuchAlgorithmException
    {
        MessageDigest msgDigest = MessageDigest.getInstance("SHA-256");
        return msgDigest.digest(input.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * Convert a hashed password back into a string for processing purposes
     * @param hash The list of bytes of the hashed password
     * @return The string of hashed password
     */
    public String HexString(byte[] hash)
    {
        BigInteger number = new BigInteger(1, hash);
        StringBuilder hexString = new StringBuilder(number.toString(16));
        while (hexString.length() < 32)
        {
            hexString.insert(0, '0');
        }
        return hexString.toString();
    }

}
