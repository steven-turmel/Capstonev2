/*
 *  @author  Steven Turmel
 *  @version 2.4
 *  @date    May 6, 2018
 *  @project Capstone_Project
 *  @file    AuthenticationHandler.java
 *
 */

package server;
import org.junit.jupiter.api.Test;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

public class AuthenticationHandlerTest {

    @Test
    void testTesting() {
        assertEquals(2, 2);
    }

    @Test
   void testCheck() {
        HashMap<String, String> hm = new HashMap();
        hm.put("username", encrypt("password"));
        StorageHandler sh = new StorageHandler();
        AuthenticationHandler ah = new AuthenticationHandler(13337, hm, sh);
        assertEquals(true, ah.check("username", encrypt("password")));
    }

    private String encrypt(String tempString) {
        MessageDigest md;
        String returnString = "empty string";
        byte[] digested = tempString.getBytes();
        try {
            md = MessageDigest.getInstance("SHA-256");
            md.update(digested);
            digested = md.digest();
            StringBuffer stringBuffer = new StringBuffer();
            for (byte bytes : digested) {
                stringBuffer.append(String.format("%02x", bytes & 0xff));
            }
            returnString = stringBuffer.toString();
        } catch (NoSuchAlgorithmException e) {
            System.out.println("Change algorithms");
            e.printStackTrace();
        }
        //System.out.println(digested);
        return returnString;
    }
}

