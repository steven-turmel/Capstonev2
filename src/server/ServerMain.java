/*
 *  @author  Steven Turmel
 *  @version 2.4
 *  @date    May 6, 2018
 *  @project Capstone_Project
 *  @file    ServerMain.java
 *
 */

package server;

public class ServerMain {

    public static void main(String[] args) {
        Thread myAC = new AccessController();
        myAC.start();
        //System.out.println("DEBUG: Listener thread started.");
    }
}
