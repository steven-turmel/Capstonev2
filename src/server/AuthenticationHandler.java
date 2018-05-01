package server;

import java.util.HashMap;

public class AuthenticationHandler {
    private HashMap ersatzPasswords;


    protected boolean check(String username, String hashpass, HashMap<String, String> list) {
       if (hashpass.equals(list.get(username)))
           return true;
       else
           return false;
    }

    protected AuthenticationHandler() {
        generateErsatzPasswords();
    }

    private void generateErsatzPasswords() {
        //code to be generated
    }
}
