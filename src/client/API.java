/*
 *  @author  Steven Turmel
 *  @version 2.4
 *  @date    May 6, 2018
 *  @project Capstone_Project
 *  @file    API.java
 *
 */

package client;

import java.io.*;
import java.net.Socket;

public class API {
    private Socket socketClient;
    private PrintWriter out;
    private BufferedReader in;
    private static int PORT = 1333;

    public boolean authenticate(String tempString, String md) {
        try {
            socketClient = new Socket("127.0.0.1", PORT);
            out = new PrintWriter(socketClient.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socketClient.getInputStream()));
            out.println(tempString + ":" + md);
            String temp = in.readLine();
            socketClient.close();
            in.close();
            out.close();
            return Boolean.valueOf(temp);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("DEBUG: error writing request.");
            return false;
        }
    }
}
