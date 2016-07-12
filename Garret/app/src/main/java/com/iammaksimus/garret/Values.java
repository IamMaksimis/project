package com.iammaksimus.garret;

import android.graphics.Bitmap;

import java.util.ArrayList;

/**
 * Created by 111 on 13.01.2016.
 */
abstract class Values {

   static String nick = "", name = "", fname = "", url = "", tvshow = "", friends = "", password = "", friendNick = "", friendName = "", friendFname = "", friendsTvShow = "";
   static TvShowObject tv;
   static int id = 0;
   static String tvShow = "";
   static ArrayList<ElementTvShow> elementTvShows = new ArrayList<>();
   static ArrayList<ElementTvShow> myelementTvShows = new ArrayList<>();
   static int month, day, year;
   int count = 0;
   static boolean setCover = true;
   static boolean open = false;
   static Bitmap coverFriendTv = null;
}
