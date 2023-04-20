package bgu.spl.mics.application.passiveObjects;


import bgu.spl.mics.MicroService;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Passive data-object representing a Diary - in which the flow of the battle is recorded.
 * We are going to compare your recordings with the expected recordings, and make sure that your output makes sense.
 * <p>
 * Do not add to this class nothing but a single constructor, getters and setters.
 */
public class Diary {

    private static Diary diary = null;
    private final AtomicInteger totalAttacks;

    private final ConcurrentHashMap<String, Long> terminateMap = new ConcurrentHashMap<String, Long>();

    private Diary() {
        totalAttacks = new AtomicInteger(0);
    }

    public static Diary getInstance() {
        if (diary == null) {
            diary = new Diary();
        }
        return diary;
    }

    public  void setTerminate(MicroService m, long time){
        String name =  m.getName() +"Terminate";
        terminateMap.putIfAbsent(name, time);

    }
    public  void setFinish(MicroService m, long time){

        String name = m.getName() + "Finish";
        if(terminateMap.containsKey(name)) {
            terminateMap.replace(name, time);
        }
        else {
            terminateMap.put(name, time);
        }
        totalAttacks.incrementAndGet();
    }

    public void setDeactivate(MicroService m, long time){
        String name = m.getName()+ "Deactivate";
        terminateMap.putIfAbsent(name, time);
    }

    public int getTotalAttacks() {
        return totalAttacks.get();
    }

    public AtomicInteger getNumberOfAttacks() {
        return totalAttacks;
    }

    public long getC3POFinish(){
        return terminateMap.get("C3POFinish");
    }

    public long getHanSoloFinish(){
        return terminateMap.get("HanSoloFinish");
    }

    public long getR2D2Deactivate(){
        return terminateMap.get("R2D2Deactivate");
    }

    public long getHanSoloTerminate(){
        return terminateMap.get("HanSoloTerminate");
    }

    public long getR2D2Terminate(){
        return terminateMap.get("R2D2Terminate");
    }
    public long getLandoTerminate(){
        return terminateMap.get("LandoTerminate");
    }
    public long getC3POTerminate(){
        return terminateMap.get("C3POTerminate");
    }

    public long getLeiaTerminate(){
        return terminateMap.get("LeiaTerminate");
    }

    public void resetNumberAttacks() {
        this.totalAttacks.set(0);
    }
}