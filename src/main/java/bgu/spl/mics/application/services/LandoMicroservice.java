package bgu.spl.mics.application.services;

import bgu.spl.mics.MicroService;
import bgu.spl.mics.application.messages.BombDestroyerEvent;
import bgu.spl.mics.application.messages.TerminationBroadcast;

/**
 * LandoMicroservice
 * You can add private fields and public methods to this class.
 * You MAY change constructor signatures and even add new public constructors.
 */
public class LandoMicroservice  extends MicroService {
    private long timeSleep;

    private TerminationBroadcast terminateAll = new TerminationBroadcast();

    public LandoMicroservice(long duration) {
        super("Lando");
        timeSleep = duration;
    }

    @Override
    protected void initialize() {
        this.subscribeEvent(BombDestroyerEvent.class, (BombDestroyerEvent e)->{
            try{
                Thread.sleep(timeSleep);
            }
            catch(InterruptedException ex){ex.printStackTrace();}
            complete(e, true);
            this.sendBroadcast(terminateAll);

        });
    }
}