package server;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class StorageHandler {

    private String prefix = "C://temp//";
    private FileName filename;
    private Path file;

    protected StorageHandler(FileName filename) {
        this.filename = filename;
        file = Paths.get(prefix + filename.getNames(0));
    }

    protected void write(String username, String password) {
        byte[] usernameBytes = username.getBytes();
        byte[] passwordBytes = password.getBytes();
        byte[] bytecode = addBytes(usernameBytes, passwordBytes);
        try {
            Files.write(file, bytecode);
        } catch (IOException e) {
            System.out.println("There was an error writing the data to file:");
            e.printStackTrace();
        }
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

}
