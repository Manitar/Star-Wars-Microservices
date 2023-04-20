package bgu.spl.mics.application.services;


import bgu.spl.mics.MicroService;
import bgu.spl.mics.application.messages.AttackEvent;
import bgu.spl.mics.application.passiveObjects.Diary;
import bgu.spl.mics.application.passiveObjects.Ewoks;

/**
 * HanSoloMicroservices is in charge of the handling {@link AttackEvent}.
 * This class may not hold references for objects which it is not responsible for:
 * {@link AttackEvent}.
 *
 * You can add private fields and public methods to this class.
 * You MAY change constructor signatures and even add new public constructors.
 */
public class HanSoloMicroservice extends MicroService {

    private Diary diary = Diary.getInstance();
    //private Ewoks ewoks = Ewoks.getInstance();

    public HanSoloMicroservice() {
        super("HanSolo");
    }


    @Override
    protected void initialize() {
        this.subscribeEvent(AttackEvent.class, (AttackEvent e)->{
            Ewoks.lockEwoks(e.getSerials());
            try{
                Thread.sleep(e.getDuration());
            }
            catch(InterruptedException ex){ex.printStackTrace();}
            Ewoks.releaseAll(e.getSerials());
            complete(e, true);
            diary.setFinish(this, System.currentTimeMillis());
        });
    }
}