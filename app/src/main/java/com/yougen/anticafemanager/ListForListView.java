package com.yougen.anticafemanager;

/**
 * Created by thymomenosgata on 07.11.17.
 */

public class ListForListView {
    int numTable;
    int countPeopls;
    String vipOrNonLimited;
    String hookah;
    String onTime;

    ListForListView(int n, int c, String h, String v, String t){
        numTable = n;
        countPeopls = c;
        hookah = h;
        vipOrNonLimited = v;
        onTime = t;
    }

}
