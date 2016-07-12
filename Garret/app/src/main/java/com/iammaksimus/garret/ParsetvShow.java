package com.iammaksimus.garret;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimeZone;

/**
 * Created by 111 on 14.01.2016.
 */
public class ParsetvShow extends AsyncTask<String, Void, String> {
    boolean flag = false;
    HttpURLConnection urlConnection = null;
    String webPage = Values.url;
    BufferedReader reader = null;
    String resultJson = "";
    URL url = null;
    InputStream stream;
    ArrayList<String> listTvShow = new ArrayList<>();
    ArrayList<ElementTvShow> elementList = new ArrayList<ElementTvShow>();
    ArrayList<Data> dataList = new ArrayList<Data>();
    Calendar now = Calendar.getInstance(TimeZone.getDefault());
    String data = "";


    @Override
    protected String doInBackground(String... urls) {
        parse();


        return "";
    }


    @Override
    protected void onPostExecute(String strJson) {
        super.onPostExecute(strJson);
        Values.tv = createElment(data);
        TvShow.startCreateList = true;
        Values.open = true;
    }


    public void parse() {
        InputStream sin;
        StringBuffer buffer = new StringBuffer();
        String text = "";
        BufferedReader in;
        int startIndex = 0, endIndex = 0;


        try {
            URL url = new URL(webPage);
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
            data = text;


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }


    public TvShowObject createElment(String el) {


        String name = "", country = "", acters = "", status = "", reiting = "", info = "", urlCover = "";
        int startIndex = 0;
        int endIndex = 0;
        String tmp = "";
        startIndex = el.indexOf("<span itemprop=\"name\">") + 22;
        tmp = el.substring(startIndex);
        endIndex = tmp.indexOf("</span") - 1;
        name = tmp.substring(0, endIndex);

        startIndex = el.indexOf("/images/Posters/big/");
        endIndex = el.indexOf("jpg");
        urlCover = el.substring(startIndex, endIndex) + "jpg";


        if (el.contains("Снимается")) {
            status = "Снимается";
        } else {
            status = "Завершен";
        }




        startIndex = el.indexOf("bycountry");
        tmp = el.substring(startIndex);
        startIndex = tmp.indexOf(">") + 1;
        endIndex = tmp.indexOf("</a>");
        country = tmp.substring(startIndex, endIndex);

        startIndex = tmp.indexOf("В главных ролях");
        tmp = tmp.substring(startIndex);
        startIndex = tmp.indexOf("</span>") + 7;
        endIndex = tmp.indexOf("</p>");
        acters = tmp.substring(startIndex, endIndex);

        startIndex = tmp.indexOf("Описание");
        tmp = tmp.substring(startIndex);
        startIndex = tmp.indexOf("</span>") + 7;
        endIndex = tmp.indexOf("</p>");
        info = tmp.substring(startIndex, endIndex);

        name = name.replaceAll("\t", "");
        acters = acters.replaceAll("\t", "");
        reiting = reiting.replaceAll("  ", "");
        country = country.replaceAll("  ", "");
        info = info.replaceAll("\t", "");
//        GarretHomeActivity.test.setText("http://www.serialdata.ru" + urlCover);

        return new TvShowObject(name, country, acters, status, info, "http://www.serialdata.ru" + urlCover);
    }
}