/*
 *  @author  Steven Turmel
 *  @version 2.4
 *  @date    May 6, 2018
 *  @project Capstone_Project
 *  @file    AccessController.java
 *
 */

package server;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class AccessController extends Thread{
    private String filename;
    private static StorageHandler sh;
    private static AuthenticationHandler ah;
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private BufferedReader in;
    private PrintWriter out;
    private boolean opened;
    static final int BUFFER_SIZE = 20000;
    static final int SERIAL_NUMBER = 57175;
    private static final int NUM_PASSWORDS_GENERATED = 15;

    AccessController() {
        filename = "C:\\temp\\shadow.pwd";
        sh = new StorageHandler();
        ah = new AuthenticationHandler(SERIAL_NUMBER, sh.initialize(filename), sh);
        opened = false;
    }

    private boolean authenticate(String username, String pword) {
        return (ah.check(username, pword));
    }

    @Override
    public void run() {
        acceptCommands();
    }

    private void close() {
        sh.writeToFile(ah.getPass());
        if (opened) {
            try {
                serverSocket.close();
                clientSocket.close();
                in.close();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("DEBUG: Error closing socket(s) or stream(s).");
            }
        }
    }

    private void listen() {
        /* This method is implemented to open the socket, receive the request,
         * and then authenticate the username and password entered on the client-side
         */
        try {
            //This block of code sets up to send and receive information to the client program.
            serverSocket = new ServerSocket(1333);
            clientSocket = serverSocket.accept();
            opened = true;
            clientSocket.setKeepAlive(true);
            clientSocket.setTcpNoDelay(true);
            clientSocket.setSendBufferSize(BUFFER_SIZE);
            clientSocket.setReceiveBufferSize(BUFFER_SIZE);
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            /* Here is where the username and password are read in,
             * and then passed to the AuthenticationHandler to validate
             */
            String temp = in.readLine();
            String[] tempArr = temp.split(":");
            System.out.println(authenticate(tempArr[0], tempArr[1]));
            out.println(authenticate(tempArr[0], tempArr[1]));
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("DEBUG: Failed to open server socket.");
        }
    }

    private void acceptCommands() {
        Scanner sc = new Scanner(System.in);
        String input;
        boolean cont = true;
        String helpString = "Commands are 'new', 'close', 'listen', 'exit', or 'help'";
        System.out.println(helpString);
        while (cont) {
            System.out.print("Please enter a command: ");
            input = sc.next().toLowerCase();
            switch (input) {
                case "debug":
                    //System.out.println(ah.getPass().toString());
                    ah.debug();
                    break;
                case "new":
                    newUser(sc);
                    break;
                case "close":
                    close();
                    break;
                case "listen":
                    System.out.println("Opening socket, Thread will be blocked until a request is received.");
                    listen();
                    break;
                case "help":
                    System.out.println(helpString);
                    break;
                case "exit":
                    System.out.println("Exiting program...");
                    cont = false;
                    break;
                default:
                    System.out.println("Command not recognized. Use command 'help' for a listing of commands.");
                    break;
            }
        }
        sc.close();
    }

    private void newUser(Scanner sc) {
        System.out.print("Enter a username: ");
        String user = sc.next();

        System.out.print("Enter a password: ");
        String pass = sc.next();
        ah.write(user, Encoder.encrypt(pass));
    }

}
