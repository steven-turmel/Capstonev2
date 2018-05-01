package server;

public class ServerMain {

    public static void main(String[] args) {
        Thread myAC = new AccessController();
        myAC.start();
        System.out.println("DEBUG: Listener thread started.");
    }
}
