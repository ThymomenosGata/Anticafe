package com.yougen.anticafemanager;

/**
 * Created by thymomenosgata on 16.12.17.
 */

public class graphList {
    private int cash;
    private int dat;

    public graphList(int cash, int dat){
        this.cash = cash;
        this.dat = dat;
    }

    public int getCash() {
        return cash;
    }

    public void setCash(int cash) {
        this.cash = cash;
    }

    public int getDat() {
        return dat;
    }

    public void setDat(int dat) {
        this.dat = dat;
    }
}
