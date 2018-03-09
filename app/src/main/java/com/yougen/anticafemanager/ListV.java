package com.yougen.anticafemanager;

/**
 * Created by thymomenosgata on 04.11.17.
 */

public class ListV {
    private int table;
    private int cpeop;
    private boolean sHookah;
    private boolean sHard;
    private boolean sNormal;
    private boolean sLigth;
    private boolean sVIP;
    private boolean sNonLimited;

    public ListV(int table,int cpeop, boolean sHookah,boolean sHard,
                 boolean sNormal,boolean sLigth,boolean sVIP,boolean sNonLimited){
        this.table = table;
        this.cpeop = cpeop;
        this.sHookah = sHookah;
        this.sHard = sHard;
        this.sNormal = sNormal;
        this.sLigth = sLigth;
        this.sVIP = sVIP;
        this.sNonLimited = sNonLimited;
    }

    public boolean issNonLimited() {
        return sNonLimited;
    }

    public void setsNonLimited(boolean sNonLimited) {
        this.sNonLimited = sNonLimited;
    }

    public boolean issHookah() {
        return sHookah;
    }

    public void setsHookah(boolean sHookah) {
        this.sHookah = sHookah;
    }

    public boolean issHard() {
        return sHard;
    }

    public void setsHard(boolean sHard) {
        this.sHard = sHard;
    }

    public boolean issNormal() {
        return sNormal;
    }

    public void setsNormal(boolean sNormal) {
        this.sNormal = sNormal;
    }

    public boolean issLigth() {
        return sLigth;
    }

    public void setsLigth(boolean sLigth) {
        this.sLigth = sLigth;
    }

    public boolean issVIP() {
        return sVIP;
    }

    public void setsVIP(boolean sVIP) {
        this.sVIP = sVIP;
    }

    public void setTable(int table) {
        this.table = table;
    }

    public void setCpeop(int cpeop) {
        this.cpeop = cpeop;
    }

    public int getTable() {
        return table;
    }

    public int getCpeop() {
        return cpeop;
    }
}
