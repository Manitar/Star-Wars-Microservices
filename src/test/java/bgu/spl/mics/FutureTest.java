package bgu.spl.mics;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.AfterAll;

import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;


import static org.junit.jupiter.api.Assertions.*;


public class FutureTest {

    private Future<String> future;

    @BeforeEach
    public void setUp(){
        future = new Future<>();
    }

    @Test
    //Tests if testResolve returns true.
    public void testResolve(){
        String str = "someResult";
        future.resolve(str);
        assertTrue(future.isDone());
        assertTrue(str.equals(future.get()));
    }

    @Test
    public void testisDone(){
        assertFalse(future.isDone());
        String str = "someResult";
        future.resolve(str);
        assertTrue(future.isDone());
    }

    @Test
    public void testGet(){
        assertFalse(future.isDone());
        future.resolve("hi");
        future.get();
        assertTrue(future.isDone());
    }

    @Test
    public synchronized void testGetTimeout(){
        assertFalse(future.isDone());
        assertNull(future.get(50, TimeUnit.NANOSECONDS));
        assertFalse(future.isDone());
        future.resolve("example");
        assertEquals(future.get(50, TimeUnit.NANOSECONDS), "example");
    }
}
