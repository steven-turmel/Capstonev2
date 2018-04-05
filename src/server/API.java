package server;

public class API {

    public void authenticate(String tempString, byte[] md) {
        AccessController.authenticate(tempString, md);
    }

    public void serverTest() {
        AccessController.test();
    }
}
