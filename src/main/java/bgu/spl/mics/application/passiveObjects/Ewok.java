package bgu.spl.mics.application.passiveObjects;

/**
 * Passive data-object representing a forest creature summoned when HanSolo and C3PO receive AttackEvents.
 * You must not alter any of the given public methods of this class.
 * <p>
 * You may add fields and methods to this class as you see fit (including public methods).
 */
public class Ewok {
    int serialNumber;
    boolean available;
    //Must check if we need to turn Ewok into Singleton
    /**
     * constructor of Ewok
     * @param serialNum
     */
    public Ewok(int serialNum) {
        this.serialNumber = serialNum;
        this.available = true;
    }

    /**
     * Acquires an Ewok
     * This method is locked in order to prevent 2 Attack Microservices from acquiring the same Ewok, or a thread trying to acquire and release at the same time.
     *
     * Also, I want to check if I need to throw exceptions in case I double acquire.
     */
    public synchronized void acquire() {
        if (available) {
            available = false;
        }
    }

    /**
     * release an Ewok
     * This method is locked in order to prevent 2 Attack Microservices from releasing the same Ewok, or a thread trying to acquire and release at the same time.
     *
     * Also, I want to check if I need to throw exceptions in case I double release.
     */
    public synchronized void release() {
        if (!available) {
            available = true;
        }
    }

    /**
     * gets serial number of the Ewok
     */
    public int getSerialNumber() {
        return serialNumber;
    }

    /**
     * checks availability of the Ewok
     * is synchronized because we can return the wrong value if one thread is in the middle of setting and the other one is getting.
     */
    public synchronized boolean isAvailable() {
        return available;
    }

}
