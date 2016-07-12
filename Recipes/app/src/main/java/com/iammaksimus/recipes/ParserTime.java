package com.iammaksimus.recipes;

import android.os.AsyncTask;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by 111 on 05.07.2016.
 */
public class ParserTime extends AsyncTask<Recipe, Void, Void> {
    String web;
    int min;
    @Override
    protected Void doInBackground(Recipe... params) {
        web = params[0].getUrl();
        //web = "http://www.edimdoma.ru/retsepty/80930-chernichnyy-konfityur-s-myatoy";
        int
                start,
                end;
        String text = null;
        URL url = null;
        try {
            url = new URL(web);

        URLConnection urlConnection = url.openConnection();
        InputStream is = urlConnection.getInputStream();
        InputStreamReader isr = new InputStreamReader(is);

        int numCharsRead;
        char[] charArray = new char[1024];
        StringBuffer sb = new StringBuffer();
        while ((numCharsRead = isr.read(charArray)) > 0) {
            sb.append(charArray, 0, numCharsRead);
        }
        text = sb.toString();
            String time;
            start = text.indexOf("<strong itemprop=\"totalTime\">");
            if(start > 0) {
                time = text.substring(start + 29, start + 45);
                end = time.indexOf("<");
                time = time.substring(0, end);
                end = time.indexOf("ч");
                String tmp = time;
                if (end > 0) {
                    try {
                        min += Integer.parseInt(time.substring(0, end - 1)) * 60;
                        if (time.length() > end + 3) {
                            tmp = time.substring(end + 3);
                        }
                    }catch (Exception e){

                    }
                }
                end = tmp.indexOf("м");
                if (end > 0) {
                    try {
                        min += Integer.parseInt(tmp.substring(0, end - 1));
                        tmp = time.substring(end + 1);
                    }catch (Exception e){

                    }
                }
            }else{
                min = 0;
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        setMin(min);
        Values.h.sendEmptyMessage(min);
        //params[0].setMin(min);
        return null;
    }

    public void setMin(int min) {
        int
                c,
                m;
        String t = "";
        if(min > 0) {
            if(min > 59){
                c = min / 60;
                m = min - c * 60;
                t = "Время приготовления: " + c + " ч.";
                if(m > 0){
                    t += m + " мин.";
                }
            }else{
                t = "Время приготовления: " + min + "мин.";
            }

            Values.min = t;
        }
    }
}
