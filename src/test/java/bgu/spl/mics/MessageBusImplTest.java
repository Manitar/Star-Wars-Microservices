package bgu.spl.mics;

import bgu.spl.mics.application.services.DummyMicroservice;
import bgu.spl.mics.MessageBusImpl;
import bgu.spl.mics.MessageBus;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions. * ;

class MessageBusImplTest {
    private MessageBusImpl mBus = MessageBusImpl.getInstance();
    private DummyMicroservice dummy1 = new DummyMicroservice("Dummy1");
    private DummyMicroservice dummy2 = new DummyMicroservice("Dummy2");
    private DummyMicroservice dummy3 = new DummyMicroservice("Dummy3");
    private DummyMicroservice dummy4 = new DummyMicroservice("Dummy4");
    private DummyMicroservice dummy5 = new DummyMicroservice("Dummy5");
    private DummyMicroservice dummy6 = new DummyMicroservice("Dummy6");
    private DummyMicroservice dummy7 = new DummyMicroservice("Dummy7");
    private DummyMicroservice dummy8 = new DummyMicroservice("Dummy8");
    private DummyCallback cback = new DummyCallback();

    @Test
        //This test checks if completing actually completes the Future task gotten from sending the event.
    /**I have made changes to this test, to make it work with the code I wrote in the assignment
     * Log: Future<Integer> --> Future<Boolean>, and changed every Integer to Boolean.
     * Removed line that made things happen twice - like dummy1.complete + mBus.complete can change into dummy1.complete
     * Also added: mBus.register(dummy1) to make him register to the message bus
     */
    void complete() {
        mBus.register(dummy2);
        DummyEvent event1 = new DummyEvent(1);
        dummy2.subscribeEvent(DummyEvent.class, cback);
        Future<Boolean> result = dummy1.sendEvent(event1);
        try {
            mBus.awaitMessage(dummy2);
            Boolean boolResult = true;
            dummy1.complete(event1, boolResult);
            assertTrue(result.isDone());
            assertEquals(boolResult, result.get());
        }
        catch(InterruptedException e){}
        mBus.unregister(dummy2);
    }




    @Test
        //Also checks subscribe broadcast
        //In the test below, I create a broadcast, subscribe 2nd MicroService to the specific broadcast type, then
        //send the broadcast through dummy 1. Then, I "Await message" with 2nd MicroService, and check if
        //the broadcast the 2nd MicroService got from the await message is equal to the one sent.
    /**
     * I changed the following test to make it work, I added:
     * mBus.register(dummy2)
     * mBus.unregister(dummy4)
     * And a few more lines to make the function work.
     */
    void sendBroadcast() {
        mBus.register(dummy4);
        DummyBroadcast broadcast1 = new DummyBroadcast(3);
        dummy4.subscribeBroadcast(DummyBroadcast.class, cback);
        dummy3.sendBroadcast(broadcast1);
        try {
            Message broadcast2 = mBus.awaitMessage(dummy4);
            assertEquals(broadcast2, broadcast1);
        } catch(InterruptedException e) {}
        mBus.unregister(dummy4);
    }


    @Test
    //Also checks subscribe event
    //In the test below, I create an event, and subscribe the 2nd MicroService to this specific event type.
    //Then, I send the event from the 1st MicroService, and await the message with the 2nd. Then I compare if the result
    //is equals to the event sent.

    /**
     * I changed the following test to make it work, I added:
     * mBus.register(dummy6)
     * mBus.unregister(dummy6)
     * and a few more lines to make the test work
     */
    void sendEvent() {
        mBus.register(dummy6);
        DummyEvent event1 = new DummyEvent(8);
        dummy6.subscribeEvent(DummyEvent.class, cback);
        dummy5.sendEvent(event1);

        try {
            Message event2 = mBus.awaitMessage(dummy6);
            assertEquals(event2, event1);
        } catch(InterruptedException e) {}
        mBus.unregister(dummy6);
    }


    /**
     * Also changed this test like the others so I can make the test work according to my implementation.
     */

    @Test
        //Checks if awaitMessage actually returns the message that was sent into the queue (assuming queue is empty)
    void awaitMessage() {
        mBus.register(dummy8);
        DummyEvent event1 = new DummyEvent(8);
        dummy8.subscribeEvent(DummyEvent.class, cback);
        dummy7.sendEvent(event1);
        try {
            DummyEvent event2 = (DummyEvent) mBus.awaitMessage(dummy8);
            assertEquals(event2, event1);
        } catch(InterruptedException e) {}
        mBus.unregister(dummy8);
    }
}