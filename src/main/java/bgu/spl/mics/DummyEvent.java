package bgu.spl.mics;

public class DummyEvent implements Event<Boolean> {

    private int num;

    public DummyEvent(int num){
        this.num = num;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

}
