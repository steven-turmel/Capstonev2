package server;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class API {
    private Socket socketClient;
    private DataOutputStream clientRequest;
    private DataInputStream response;
    private static int PORT = 1333;

    public boolean authenticate(String tempString, String md) {
        try {
            clientRequest.writeChars(tempString + ":" + md);
            return response.readBoolean();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("DEBUG: error writing request.");
            return false;
        }
    }

    public API() {
        try {
            System.out.println("DEBUG: Opening socket on port " + PORT);
            socketClient = new Socket("127.0.0.1", PORT);
            response = new DataInputStream(socketClient.getInputStream());
            clientRequest = new DataOutputStream(socketClient.getOutputStream());
        } catch (UnknownHostException u) {
            u.printStackTrace();
            System.out.println("DEBUG: Error opening client-side socket.");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("DEBUG: IOException hit opening socket.");
        }
    }
}
