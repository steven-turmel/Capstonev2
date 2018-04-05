package server;

public class AuthenticationHandler {
    private String prefix = "C://temp//";
    private FileName filename;

    public AuthenticationHandler(FileName filename) {
        this.filename = filename;
    }

    protected boolean check(String username, byte[] hashpass) {

        return true;
    }
}
