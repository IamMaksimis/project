package com.iammaksimus.garret;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import java.util.ArrayList;

/**
 * Created by 111 on 16.01.2016.
 */
public class BroadCast extends BroadcastReceiver {

    static ArrayList<ElementTvShow> element = new ArrayList<ElementTvShow>();
    static int month, day, year;
    static String name = "", fname = "", nick = "";
    public void onReceive(Context context, Intent intent) {
        Intent time = new Intent(context, ServiceNotification.class);
        context.startService(time);

    }




}
