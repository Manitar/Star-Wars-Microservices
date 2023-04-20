package bgu.spl.mics.application.passiveObjects;
import java.util.List;

/**
 * Passive object representing the resource manager.
 * <p>
 * This class must be implemented as a thread-safe singleton.
 * You must not alter any of the given public methods of this class.
 * <p>
 * You can add ONLY private methods and fields to this class.
 */
public class Ewoks {

    //Fields
    private static Ewoks ewoks = null;
    private  static Ewok[] array;

    //Private constructor
    private Ewoks(int _numOfEwoks) {
        array = new Ewok[_numOfEwoks];
        for (int i = 0; i <_numOfEwoks; i++) {
            array[i] = new Ewok(i+1);
        }
    }

    //Singleton
    public static Ewoks getInstance(int _numOfEwoks) {
        if (ewoks == null) {
            ewoks = new Ewoks(_numOfEwoks);
        }
        else if(array.length!=_numOfEwoks)
                ewoks = new Ewoks(_numOfEwoks);
        return ewoks;
    }

    public static Ewoks getInstance(){
        while(ewoks==null){
        }
        return ewoks;
    }

    //We assume List is in order
    public static boolean lockEwoks(List<Integer> serials) {
        for (Integer num : serials) {
            {
                array[num-1].acquire();
            }
        }
        return true;
    }

    public static boolean releaseAll(List<Integer> serials) {
        for (Integer num : serials) {
            array[num-1].release();
        }
        return true;
    }
}
//as we can't tell the order of the ewoks required for the attack, we will have to LOCK the 'available' method
//example: attack1 - [1,2,3...100]
//attack2 - [100,1]
//in that case, a deadlock can occur, because by the time the first attack will get to 100 the second one might take 100 already.
//Solution: Sort array in ascending order to prevent deadlocks, then we don't need to synchronize. IMPORTANT!!!