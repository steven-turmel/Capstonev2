package server;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class StorageHandlerTest {


    @Test
    void testTesting() {
        assertEquals(2, 2);
    }

    @Test
    void testRead() {
        StorageHandler sh = new StorageHandler("C:/temp/shadow.pwd");
        assertEquals("{username=password}", sh.readFromFile());
    }
}