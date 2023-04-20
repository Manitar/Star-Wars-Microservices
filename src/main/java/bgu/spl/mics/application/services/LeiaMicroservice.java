package bgu.spl.mics.application.services;

import bgu.spl.mics.Future;
import bgu.spl.mics.MicroService;
import bgu.spl.mics.application.messages.AttackEvent;
import bgu.spl.mics.application.messages.BombDestroyerEvent;
import bgu.spl.mics.application.messages.DeactivationEvent;
import bgu.spl.mics.application.passiveObjects.Attack;

import java.util.LinkedList;

/**
 * LeiaMicroservices Initialized with Attack objects, and sends them as  {@link AttackEvent}.
 * This class may not hold references for objects which it is not responsible for:
 * {@link AttackEvent}.
 *
 * You can add private fields and public methods to this class.
 * You MAY change constructor signatures and even add new public constructors.
 */
public class LeiaMicroservice extends MicroService {
    private Attack[] attacks;
    private LinkedList<Future> futures = new LinkedList <Future>();




    public LeiaMicroservice(Attack[] attacks) {
        super("Leia");
        this.attacks = attacks;
    }

    @Override
    protected void initialize() {

        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace(); }

        for(Attack a: attacks) {
            AttackEvent toSend = new AttackEvent(a.getDuration(), a.getSerials());

            Future result;
            do {
                result = sendEvent(toSend);
            }
            while (result == null);
            futures.add(result);
        }

        for(Future f: futures){
            while(!f.isDone()){

            }
        }
        DeactivationEvent deactivationEvent = new DeactivationEvent();
        Future deactivationFuture = this.sendEvent(deactivationEvent);

        while(!deactivationFuture.isDone()){

        }

        BombDestroyerEvent bombDestroyerEvent = new BombDestroyerEvent();
        Future bombFuture = this.sendEvent(bombDestroyerEvent);

    }

}