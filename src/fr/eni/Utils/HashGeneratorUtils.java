package fr.eni.Utils;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


/**
 * La classe qui génère des jolies hash.
 * see projet cea
 */
public class HashGeneratorUtils {

    private HashGeneratorUtils() {
    }

    public static String generateSHA256(String message) {
        return hashString(message, "SHA256");
    }

    private static String hashString(String message , String algorithme) {

        MessageDigest digest = null;
        try {
            digest = MessageDigest.getInstance(algorithme);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        byte[] hashedBytes = digest.digest(message.getBytes(StandardCharsets.UTF_8));
        return convertByteArraytoHexString(hashedBytes);
    }

    private static String convertByteArraytoHexString(byte[] arrayBytes) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i< arrayBytes.length; i++) {
            sb.append(Integer.toString((arrayBytes[i] & 0xff ) + 0x100, 16).substring(1));
        }
        return sb.toString();
    }



}



