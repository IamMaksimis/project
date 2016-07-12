package com.iammaksimus.garret;

import android.graphics.Bitmap;

/**
 * Created by 111 on 23.01.2016.
 */
public class ElementTvShowFriends{
    String name, url = "http://www.serialdata.ru/serials.php?name=";
    Bitmap cover;

    ElementTvShowFriends(String name){
        this.name = name;
        //changeCover();


    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        int start = 0;
        start = url.indexOf("big/") + 4;
        String tmp = url.substring(start, url.length() - 4);
        this.url += tmp;
    }

    public String getName() {
        return name;
    }

    public Bitmap getCover() {
        return cover;
    }

    public void setCover(Bitmap cover) {
        this.cover = cover;
    }



}
