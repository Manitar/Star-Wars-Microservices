package bgu.spl.mics.application.services;

import bgu.spl.mics.MicroService;
import bgu.spl.mics.application.messages.DeactivationEvent;
import bgu.spl.mics.application.passiveObjects.Diary;

/**
 * R2D2Microservices is in charge of the handling {@link DeactivationEvent}.
 * This class may not hold references for objects which it is not responsible for:
 * {@link DeactivationEvent}.
 *
 * You can add private fields and public methods to this class.
 * You MAY change constructor signatures and even add new public constructors.
 */
public class R2D2Microservice extends MicroService {
    private long sleepTime;
    private Diary diary = Diary.getInstance();
    public R2D2Microservice(long duration) {
        super("R2D2");
        sleepTime = duration;
    }

    @Override
    protected void initialize() {
        this.subscribeEvent(DeactivationEvent.class, (DeactivationEvent e)->{
            try {
                //here R2D2 awaits the needed time anyway.
                //so why do i need the message to hold 'duration' as well?
                Thread.sleep(sleepTime);
            }
            catch(InterruptedException ex){ex.printStackTrace();}
            complete(e, true);
            diary.setDeactivate(this, System.currentTimeMillis());

        });
    }
}
