/*
 *  @author  Steven Turmel
 *  @version 2.4
 *  @date    May 6, 2018
 *  @project Capstone_Project
 *  @file    Controller.java
 *
 */

package client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import server.Encoder;

public class Controller {

    private static API api = new API();
    @FXML
    private TextField userName;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Label authentication;

    @FXML
    public void handleSubmitButtonAction(ActionEvent event) {
        CharSequence tempName = userName.getCharacters();
        CharSequence tempPass = passwordField.getCharacters();
        boolean success = api.authenticate(tempName.toString(), Encoder.encrypt(tempPass.toString()));
        if (!success) {
            authentication.setText("Authentication Failed.");
            authentication.setVisible(true);
        } else {
            authentication.setText("Authenticated!");
            authentication.setVisible(true);
        }
    }

    /*private String encrypt(String tempString) {
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
    }*/
}

