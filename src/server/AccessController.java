package server;


import java.io.*;
import java.net.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Scanner;


public class AccessController extends Thread{
    private String filename;
    private static StorageHandler sh;
    private static AuthenticationHandler ah;
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private Scanner request;
    private DataOutputStream response;
    private boolean opened;

    AccessController() {
        filename = "C:\\temp\\shadow.pwd";
        sh = new StorageHandler(filename);
        ah = new AuthenticationHandler();
        opened = false;
    }


   /* public static void test() {
        AccessController ac = new AccessController();
    }*/

    private boolean authenticate(String username, String pword) {
        return (ah.check(username, pword, request()));
    }

    private HashMap request() {
        return sh.getPasswords();
    }

    @Override
    public void run() {
        acceptCommands();
    }

    private void close() {
        sh.writeToFile();
        if (opened) {
            try {
                serverSocket.close();
                clientSocket.close();
                request.close();
                response.close();
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("DEBUG: Error closing socket(s) or stream(s).");
            }
        }
    }

    private void listen() {
        try {
            serverSocket = new ServerSocket(1333);
            clientSocket = serverSocket.accept();
            request = new Scanner(clientSocket.getInputStream());
            response = new DataOutputStream(clientSocket.getOutputStream());
            String[] temp = request.nextLine().split(":");
            //System.out.println("DEBUG: username = " + temp[0]);
            //System.out.println("DEBUG: password = " + temp[1]);
            //response.writeBoolean(authenticate(temp[0], temp[1]));
            response.writeBoolean(true);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("DEBUG: Failed to open server socket.");
        }
    }

    private void acceptCommands() {
        Scanner sc = new Scanner(System.in);
        String input;
        boolean cont = true;
        String helpString = "Commands are 'new', 'close', 'listen', or 'help'";
        System.out.println(helpString);
        while (cont) {
            System.out.print("Please enter a command: ");
            input = sc.next().toLowerCase();
            switch (input) {
                case "debug":
                    System.out.println(request().toString());
                    break;
                case "new":
                    newUser(sc);
                    break;
                case "close":
                    close();
                    cont = false;
                    break;
                case "listen":
                    System.out.println("DEBUG: Opening socket, Thread will be blocked until request is received.");
                    listen();
                    break;
                case "help":
                    System.out.println(helpString);
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
        sh.write(user, encrypt(pass));
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
