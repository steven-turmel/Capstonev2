package server;


import java.util.Random;

public class AccessController {
    protected static FileName filenames;
    private String[] extensions;
    private Random rand;
    private static final int DEFAULT_NUM_FILES = 10;
    protected static int numberOfFiles;
    private char[] alphabet;
    private StorageHandler sh;
    private static AuthenticationHandler ah;

    private AccessController() {
        numberOfFiles = DEFAULT_NUM_FILES;
        sh = new StorageHandler(filenames);
        ah = new AuthenticationHandler(filenames);
        alphabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();
        rand = new Random(11517299);
        extensions = new String[]{"sys", "dll", "dmz", "jql", "ben"};
        filenames = new FileName(initialize());
    }

/*
    private AccessController(int numFiles) {
        alphabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();
        numberOfFiles = numFiles;
        rand = new Random(numFiles * 7 % 5);
        extensions = new String[]{"sys", "dll", "dmz", "jql", "ben"};
        filenames = new FileName(initialize());
    }
*/

    private String[] initialize() {
        String[] tempFiles = new String[numberOfFiles];
        String tempFileName;
        for (int i = 0; i < numberOfFiles; i++) {
            int randomLen = rand.nextInt(16) + 5;
            tempFileName = new String();
            //Selects a random string for a filename
            for (int j = 0; j < randomLen; j++) {
                int randomChar = rand.nextInt(26);
                tempFileName += alphabet[randomChar];
            }
            //Selects a random extension from the list
            int randomExt = rand.nextInt(extensions.length);
            tempFileName += "." + extensions[randomExt];
            tempFiles[i] = tempFileName;
        }
        return tempFiles;
    }

    public static void test() {
        AccessController ac = new AccessController();
        for (int i = 0; i < numberOfFiles; i++) {
            System.out.println(filenames.getNames(i));
        }
    }

    public static void authenticate(String username, byte[] pword) {
        if (ah.check(username, pword));
    }
}
