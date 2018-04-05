package server;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static server.AccessController.DEFAULT_NUM_FILES;
import

public class AuthenticationHandler {
    private String prefix = "C://temp//";
    private FileName filename;

    public AuthenticationHandler(FileName filename) {
        this.filename = filename;
    }

    protected boolean check(String username, byte[] hashpass) {
        byte[] addedHash = addBytes(username.getBytes(), hashpass);
        Integer checksum = hashpass.hashCode();
        int filecheck = checksum%DEFAULT_NUM_FILES;
        return checkFile(filecheck);
    }

    private byte[] addBytes(byte[] arr1, byte[] arr2) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        try {
            output.write(arr1);
            output.write(arr2);
        } catch (IOException e) {
            System.out.println("Error concatenating bytes");
            e.printStackTrace();
        }
        byte[] out = output.toByteArray();
        return out;
    }

    private boolean checkFile(int i) {

    }
}
