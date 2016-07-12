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
 * Created by 111 on 12.01.2016.
 */
public class ParseTask extends AsyncTask<String, Void, String>{
    boolean flag = false;
    HttpURLConnection urlConnection = null;
    BufferedReader reader = null;
    String resultJson = "";
    URL url = null;
    InputStream stream;
    ArrayList<String> listTvShow = new ArrayList<>();
    ArrayList<ElementTvShow> elementList = new ArrayList<ElementTvShow>();
    ArrayList<Data> dataList = new ArrayList<Data>();
    Calendar now = Calendar.getInstance( TimeZone.getDefault() );
    int month = now.get(Calendar.MONTH) + 1;
    int year = now.get(Calendar.YEAR);
    int day = now.get(Calendar.DATE);
    int count = 0, index = 0;
    int[] array;
    String data = "";
    int[] monthSize = {31, 29, 31, 30, 31, 30, 31, 31, 30, 30, 30, 31};
    public void dataPlus(){
        day++;
        if(day > monthSize[month - 1]){
            day = 1;
            month++;
            if(month > 12){
                month = 1;
                year++;
            }
        }
    }

    @Override
    protected String doInBackground(String... urls) {
        parse();


        return "";
    }


    @Override
    protected void onPostExecute(String strJson){
        super.onPostExecute(strJson);
        //
        array = new int[listTvShow.size()];
        for(int i = 0; i < listTvShow.size(); i++) {
            GarretHomeActivity.element.add(createElment(listTvShow.get(i)));
            index++;
        }

        GarretHomeActivity.startCreateList = true;

    }


    public void parse(){
        InputStream sin;
        StringBuffer buffer = new StringBuffer();
        String text ="";
        BufferedReader in;
        int startIndex = 0, endIndex = 0;

        String d = "", m = "", y = "";
        if(day > 9){
            d = "" + day;
        }else{
            d = "0" + day;
        }
        if(month > 9){
            m = "" + month;
        }else{
            m = "0" + month;
        }
        y = "" + year;

        try {
            String webPage = "http://www.serialdata.ru/calendar.php?m=" + m + "&y=" + y + "&date=" + d + "." + m + "." + y;
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


            startIndex = text.indexOf("Сериалы за ") + 9;
            String tt = text.substring(startIndex);
            endIndex = tt.indexOf("20");
            data = tt.substring(0, endIndex);

            startIndex = text.indexOf("<p class=\"search_list_img\">");
            endIndex = text.lastIndexOf("<div class=\"search_list_info\"></div>");
            text = text.substring(startIndex, endIndex);
            text += "<div class=\"search_list_info\"></div>";


            while(text.indexOf("<p class=\"search_list_img\">") >= 0){
                startIndex = text.indexOf("<p class=\"search_list_img\">");
                endIndex = text.indexOf("<div class=\"search_list_info\"></div>");
                String tmp = "";
                tmp = text.substring(startIndex, endIndex+36);
                text = text.substring(endIndex+36);
                listTvShow.add(tmp);
                dataList.add(new Data(day, month));
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        count++;
        if(count < 7){
            dataPlus();
            parse();
        }
    }


    public ElementTvShow createElment(String el){


        String rusName = "", engName = "", sezon = "", episod = "", episodName = "", urlCover = "http://www.serialdata.ru", url = "http://www.serialdata.ru/";
        int startIndex = el.indexOf("/images/");
        int endIndex = el.indexOf("jpg");
        urlCover += el.substring(startIndex, endIndex) + "jpg";

        startIndex = el.indexOf("serials");
        endIndex = el.indexOf("<img");
        url += el.substring(startIndex, endIndex);
        endIndex = url.indexOf("\"");
        url = url.substring(0, endIndex);

        startIndex = el.indexOf("fir_name\">") + 11;
        endIndex = el.indexOf("</span") - 2;
        rusName = el.substring(startIndex, endIndex);

        startIndex = el.indexOf("sec_name\">") + 10;
        String tmp = el.substring(startIndex);
        endIndex = tmp.indexOf("</span");
        engName = tmp.substring(10, endIndex);

        startIndex = tmp.indexOf("Сезон");
        tmp = tmp.substring(startIndex);
        startIndex = tmp.indexOf("span>") + 5;
        endIndex = tmp.indexOf("<br>");
        sezon = tmp.substring(startIndex, endIndex);

        startIndex = tmp.indexOf("Эпизод");
        tmp = tmp.substring(startIndex);
        startIndex = tmp.indexOf("span>") + 5;
        endIndex = tmp.indexOf("<br>");
        episod = tmp.substring(startIndex, endIndex);

        startIndex = tmp.indexOf("Название");
        tmp = tmp.substring(startIndex);
        startIndex = tmp.indexOf("span>") + 5;
        endIndex = tmp.indexOf("</p>");
        episodName = tmp.substring(startIndex, endIndex);

        rusName = rusName.replaceAll("\t", "");
        engName = engName.replaceAll("\t", "");
        rusName = rusName.replaceAll("  ", "");
        engName = engName.replaceAll("  ", "");

        episodName = episodName.replaceAll("\t", "");
        episodName = episodName.replaceAll("  ", "");

//        GarretHomeActivity.test.setText(GarretHomeActivity.test.getText() + episodName);

        return new ElementTvShow(rusName, engName, "Название: " + episodName, "Сезон: " +sezon, "Эпизод: " +episod, dataList.get(index).getData(), urlCover, url);
    }
}
