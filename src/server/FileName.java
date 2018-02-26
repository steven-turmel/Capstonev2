package server;

import java.io.Serializable;

public class FileName implements Serializable{

    private String[] filenames;

    protected FileName(String[] names) {
        filenames = names;
    }

    protected String getNames(int index) {
        return filenames[index];
    }


}
