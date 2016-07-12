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
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimeZone;

/**
 * Created by 111 on 13.01.2016.
 */
public class SearchTv extends AsyncTask<String, Void, String> {
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


    @Override
    protected String doInBackground(String... urls) {
        parse();


        return "";
    }


    @Override
    protected void onPostExecute(String strJson){
        super.onPostExecute(strJson);
        if(!data.equals("")){
            if(GarretHomeActivity.searchEl.size() > 0){
                GarretHomeActivity.searchEl.clear();
            }
            for(int i = 0; i < listTvShow.size(); i++) {
                GarretHomeActivity.searchEl.add(createElment(listTvShow.get(i)));
                index++;
            }
            GarretHomeActivity.startCreateList = true;
        }


    }


    public void parse(){
        InputStream sin;
        StringBuffer buffer = new StringBuffer();
        String text ="";
        BufferedReader in;
        int startIndex = 0, endIndex = 0;


        try {
            String webPage = "http://www.serialdata.ru/search.php?Search=" + URLEncoder.encode(GarretHomeActivity.serchText.getText().toString(), "utf-8");
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

            if(text.contains("По Вашему запросу ничего не найдено.")){
                GarretHomeActivity.startCreateList = true;
                GarretHomeActivity.sorry = true;

            }else{
                startIndex = text.indexOf("<p class=\"search_list_img\">");
                endIndex = text.lastIndexOf("<div class=\"search_list_info\"></div>");
                text = text.substring(startIndex, endIndex);
                text += "<div class=\"search_list_info\"></div>";
                data = text;
                while(text.indexOf("<p class=\"search_list_img\">") >= 0){
                    startIndex = text.indexOf("<p class=\"search_list_img\">");
                    endIndex = text.indexOf("<div class=\"search_list_info\"></div>");
                    String tmp = "";
                    tmp = text.substring(startIndex, endIndex+36);
                    text = text.substring(endIndex+36);
                    listTvShow.add(tmp);

                }
            }




        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
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


        if(tmp.contains("Снимается")){
            sezon = "Снимается";
        }else{
            sezon = "Завершен";
        }


        startIndex = tmp.indexOf("bycountry");
        tmp = tmp.substring(startIndex);
        startIndex = tmp.indexOf(">") + 1;
        endIndex = tmp.indexOf("</a>");
        episod = tmp.substring(startIndex, endIndex);



        rusName = rusName.replaceAll("\t", "");
        engName = engName.replaceAll("\t", "");
        rusName = rusName.replaceAll("  ", "");
        engName = engName.replaceAll("  ", "");
        episodName = episodName.replaceAll("\t", "");
        episodName = episodName.replaceAll("  ", "");
        sezon = sezon.replaceAll("\t", "");
        sezon = sezon.replaceAll("  ", "");
        episod = episod.replaceAll("\t", "");
        episod = episod.replaceAll("  ", "");

//        GarretHomeActivity.test.setText(GarretHomeActivity.test.getText() + episodName);
       // GarretHomeActivity.test.setText(rusName + " " + engName + " " + episodName + " " + sezon + " " + episod + " " + urlCover);
        return new ElementTvShow(rusName, engName, episodName, "Статус: " + sezon, "Страна: " + episod, "", urlCover, url);
    }
}
