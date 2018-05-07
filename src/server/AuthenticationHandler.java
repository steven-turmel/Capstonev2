/*
 *  @author  Steven Turmel
 *  @version 2.4
 *  @date    May 6, 2018
 *  @project Capstone_Project
 *  @file    AuthenticationHandler.java
 *
 */
package server;

import java.util.*;


public class AuthenticationHandler {
    private HashMap<String, String> ersatzPasswords;
    private HashMap<String, String> pwords;
    private LogListener logListener;
    private static char[] ALPHABET = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890!@#$%^&*()".toCharArray();
    private static int NUM_USERNAMES = 15;


    protected boolean check(String username, String hashpass) {
        if (hashpass.equals(pwords.get(username))) {
            writeLog("Successful logon by " + username);
            return true;
        } else if (hashpass.equals(ersatzPasswords.get(username))) {
           writeLog("Ersatz password used. System may be compromised.");
           return true;
        } else {
            writeLog("Invalid login attempt.");
            return false;
        }
    }

    protected AuthenticationHandler(int serial, HashMap<String, String> pwords, LogListener listener) {
        ersatzPasswords = new HashMap();
        generateErsatzPasswords(serial);
        this.pwords = filterPasswords(pwords);
        this.logListener = listener;
        ersatzPasswords.putIfAbsent("gemini", Encoder.encrypt("unicorn1"));
    }

    private void generateErsatzPasswords(int serialNumber) {
        ArrayList<String> keys = generateRandomUsernames(serialNumber);
        StringBuilder passwordBuilder;
        Random rand = new Random(serialNumber);
        for (int i = 0; i < NUM_USERNAMES; i++) {
            String tempPass = "";
            passwordBuilder = new StringBuilder();
            for (int j = 0; j < rand.nextInt(15) + 10; j++) {
                char c = ALPHABET[rand.nextInt(ALPHABET.length)];
                passwordBuilder.append(c);
            }
            tempPass = passwordBuilder.toString();
            ersatzPasswords.put(keys.get(i), Encoder.encrypt(tempPass));
        }
    }

    private ArrayList<String> generateRandomUsernames(int serialNumber) {
        ArrayList<String> usernames = new ArrayList<>();
        StringBuilder usernameBuilder = new StringBuilder();
        Random rand = new Random(serialNumber *2);
        for (int i = 0; i < NUM_USERNAMES; i++) {
            String tempUser = "";
            for (int j = 0; j < rand.nextInt(15) + 10; j++) {
                char c = ALPHABET[rand.nextInt(ALPHABET.length)];
                usernameBuilder.append(c);
            }
            tempUser = usernameBuilder.toString();
            usernames.add(tempUser);
        }
        return usernames;
    }

    protected void debug() {
        System.out.println("DEBUG: " + ersatzPasswords.toString());
        System.out.println("DEBUG: " + pwords.toString());
    }

    protected HashMap<String, String> getPass() {
        pwords.putAll(ersatzPasswords);
        return pwords;
    }

    protected void write(String uname, String pass) {
        pwords.putIfAbsent(uname, pass);
    }

    private void writeLog(String logString) {
        logListener.logHandler(new LogEvent(this, logString));
    }

    private HashMap<String, String> filterPasswords(HashMap<String, String> rawInput) {
        List<String> keys = new ArrayList(rawInput.keySet());
        List<String> badkeys = new ArrayList<>(ersatzPasswords.keySet());
        HashMap<String, String> output = rawInput;
        for (int j = 0; j < badkeys.size(); j++) {
            output.remove(badkeys.get(j));
        }
        output.remove("gemini");
        return output;
    }
}
