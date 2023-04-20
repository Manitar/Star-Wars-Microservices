package bgu.spl.mics.application;

import bgu.spl.mics.application.passiveObjects.Attack;

public class GsonAccess {
    private  Attack[] attacks;
    private final int R2D2;
    private final int Lando;
    private final int Ewoks;


    public GsonAccess(Attack[] _attacks, int _R2D2, int _Lando,  int _Ewoks){
        attacks = _attacks;
        R2D2 = _R2D2;
        Lando = _Lando;
        Ewoks = _Ewoks;
    }
    public Attack[] getAttacks(){
        return attacks;
    }

    public int getR2D2() {
        return R2D2;
    }

    public int getLando(){
        return Lando;
    }

    public int getEwoks(){
        return Ewoks;
    }
}
