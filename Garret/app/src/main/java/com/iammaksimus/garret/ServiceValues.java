package com.iammaksimus.garret;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.table.MobileServiceTable;

import java.util.ArrayList;

/**
 * Created by 111 on 19.01.2016.
 */
public class ServiceValues extends Service{
    static ArrayList<ElementTvShow> elementTvShows = new ArrayList<>();
    static ArrayList<ElementTvShow> myelementTvShows = new ArrayList<>();
    static int month, day, year;
    int count = 0;
    static String nick = "", name = "", fname = "", url = "", tvshow = "", friends = "", password = "";
    static TvShowObject tv;
    static int id = 0;
    static String tvShow = "";
    public static user item = new user();
    public static MobileServiceClient mClient;
    public static MobileServiceTable<user> mToDoTable;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
