package server;

import java.io.*;
import java.util.HashMap;

public class StorageHandler {

    private String filename;
    private HashMap<String, String> pwords;

    StorageHandler(String filename) {
        this.filename = filename;
        File file = new File(this.filename);
        if (file.exists()) {
            //If the file is present, load in from it:
            pwords = readFromFile();
        } else {
            //If the file is not there, create the empty hashmap anyway.
            pwords = new HashMap(100);
        }
    }

    protected void write(String username, String password) {
        pwords.put(username, password);
    }

    protected boolean writeToFile() {
        try {
            FileOutputStream fos = new FileOutputStream(filename);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(pwords);
            oos.close();
            fos.close();
            return true;
        } catch (IOException e) {
            //Debug entry
            System.out.println("DEBUG: There was an error writing the data to file:");
            e.printStackTrace();
            return false;
        }
    }

    protected HashMap getPasswords() {
        return pwords;
    }


    protected HashMap<String, String> readFromFile() {
        HashMap<String, String> temp;
        try {
            FileInputStream fis = new FileInputStream(filename);
            ObjectInputStream ois = new ObjectInputStream(fis);
            temp = (HashMap) ois.readObject();
            return temp;
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("DEBUG: File not found exception thrown.");
            return null;
        } catch (ClassNotFoundException c) {
            c.printStackTrace();
            System.out.println("DEBUG: You shouldn't get this error. Class not found exception.");
            return null;
        }
    }
}
