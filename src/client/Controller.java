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
        //System.out.println(encrypt(tempPass.toString()));
        api.authenticate(tempName.toString(), encrypt(tempPass.toString()));

    }

    private String encrypt(String tempString) {
        MessageDigest md;
        String returnString = "empty string";
        try {
            md = MessageDigest.getInstance("MD5");
            md.update(tempString.getBytes());
            byte[] digested = md.digest();
            StringBuffer stringBuffer = new StringBuffer();
            for (byte bytes : digested) {
                stringBuffer.append(String.format("%02x", bytes & 0xff));
            }
            returnString = stringBuffer.toString();
        }catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
            return returnString;
        }
    }

