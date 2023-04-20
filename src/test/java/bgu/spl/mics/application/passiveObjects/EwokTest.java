package bgu.spl.mics.application.passiveObjects;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
//Tests that I want to add: Test double acquire, test double release.
class EwokTest {

    private Ewok ewok;

    @BeforeEach
    public void setUp(){
        ewok = new Ewok(5);
    }

    @Test
    /**
     * checks if Ewok starts as available, and checks if acquire makes the
     * Ewok unavailable and release makes it available once again.
     */
    public void testAvailable(){
        assertTrue(ewok.isAvailable());
        ewok.acquire();
        assertFalse(ewok.isAvailable());
        ewok.release();
        assertTrue(ewok.isAvailable());
    }

    @Test
    /**
     * Checks if the constructor builds the serial number correctly.
     */
    public void testSerialNum(){
        assertEquals(5, ewok.getSerialNumber());
    }

}