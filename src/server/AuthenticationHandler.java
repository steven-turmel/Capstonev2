package server;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import static server.AccessController.DEFAULT_NUM_FILES;

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
        return checkFile(filecheck, addedHash);
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

    private boolean checkFile(int index, byte[] checksum) {
        try {
            File loadedFile = new File(prefix + filename.getNames(index));
            InputStream targetStream = FileUtils.openInputStream(loadedFile);
            byte[] filecontents = IOUtils.toByteArray(targetStream);
            for (int i = 0; i < filecontents.length; i+=128) {
                byte[] temp = splitBytes(filecontents, i);
                if (temp == checksum)
                    return true;
                else
                    ;
            }
        } catch (IOException e) {
            System.out.println("Error Loading File");
            e.printStackTrace();
        }
        return false;
    }

    private byte[] splitBytes(byte[] b, int initial) {
        byte[] out = new byte[128];
        for (int i = 0; i < 128; i++, initial++) {
            out[i] = b[initial];
        }
        return out;
    }

}
