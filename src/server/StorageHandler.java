/*
 *  @author  Steven Turmel
 *  @version 2.4
 *  @date    May 6, 2018
 *  @project Capstone_Project
 *  @file    StorageHandler.java
 *
 */

package server;

import java.io.*;

import java.util.HashMap;


public class StorageHandler implements LogListener {

    private String filename;
    private static String logfile = "C:\\temp\\events.log";

    protected HashMap<String,String> initialize(String filename) {
        HashMap<String, String> tempPass;
        this.filename = filename;
        File file = new File(this.filename);
        if (file.exists()) {
            //If the file is present, load in from it:
             tempPass = readFromFile();
        }  else {
            //If the file is not there, create the empty hashmap anyway.
            tempPass = new HashMap(100);
        }
        return tempPass;
    }

    protected boolean writeToFile( HashMap<String, String> pwords) {
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

    protected HashMap<String, String> readFromFile() {
        //This method is used to load the (legit) passwords hashmap to the file
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

    @Override
    public void logHandler(LogEvent e) {
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter(logfile, true));
            out.write("Date: " + e.getDate() + " - Event: " + e.getLogString());
            out.close();
        } catch (IOException i) {
            //Debug entry
            System.out.println("DEBUG: There was an error writing the data to file:");
            i.printStackTrace();
        }
    }
}
