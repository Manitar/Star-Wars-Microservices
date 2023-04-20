package bgu.spl.mics;

public class DummyBroadcast implements Broadcast{

    private int num;

    public DummyBroadcast(int num){
        this.num = num;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

}
