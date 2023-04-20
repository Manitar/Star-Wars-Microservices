package bgu.spl.mics.application.messages;
import bgu.spl.mics.Event;

import java.util.List;

public class AttackEvent implements Event<Boolean> {
	private int duration;
	private List<Integer> serials;

	public AttackEvent(int _duration, List<Integer> _serials){
	    duration = _duration;
	    serials = _serials;
    }

    public int getDuration(){
	    return duration;
    }

    public int getByIndex(int i){
	    return serials.get(i);
    }

    public List<Integer> getSerials(){
	    return serials;
    }
}
