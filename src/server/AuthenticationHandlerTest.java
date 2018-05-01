package server;
import org.junit.jupiter.api.Test;

import java.io.UnsupportedEncodingException;
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
        AuthenticationHandler ah = new AuthenticationHandler();
        HashMap<String, String> hm = new HashMap();
        hm.put("username", encrypt("password"));
        assertEquals(true, ah.check("username", encrypt("password"), hm));
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

