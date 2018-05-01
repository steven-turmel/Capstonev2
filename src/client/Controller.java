package client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import server.API;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Controller {

    private static API api = new API();
    @FXML
    private TextField userName;
    @FXML
    private PasswordField passwordField;

    @FXML
    public void handleSubmitButtonAction(ActionEvent event) {
        CharSequence tempName = userName.getCharacters();
        CharSequence tempPass = passwordField.getCharacters();
//        System.out.println(tempName.toString() + " " + tempPass.toString());
        System.out.println(encrypt(tempPass.toString()));
        boolean success = api.authenticate(tempName.toString(), encrypt(tempPass.toString()));
        if (!success) {
            System.out.println("failed");
        } else {
            System.out.println("authenticated");
        }
    }

    @FXML
    public void serverButton(ActionEvent event) {
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

