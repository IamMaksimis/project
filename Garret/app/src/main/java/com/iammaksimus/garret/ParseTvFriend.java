package com.iammaksimus.garret;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimeZone;

/**
 * Created by 111 on 23.01.2016.
 */
public class ParseTvFriend extends AsyncTask<String, Void, String> {
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
    Bitmap bmp;
    String index = "";


    @Override
    protected String doInBackground(String... urls) {
        try {
            webPage = "http://www.serialdata.ru/search.php?Search=" + URLEncoder.encode(urls[0], "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        index = urls[1];
        parse();
       //hdh();


        return "";
    }


    @Override
    protected void onPostExecute(String strJson) {
        super.onPostExecute(strJson);

       // TvShow.startCreateList = true;
    }


    public void parse() {
        InputStream sin;
        StringBuffer buffer = new StringBuffer();
        String text = "", urlStr = "http://www.serialdata.ru/";
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
            startIndex = text.indexOf("serials.php?name");
            text = text.substring(startIndex);
            endIndex = text.indexOf("\"");
            urlStr += text.substring(0, endIndex);
            //endIndex = urlStr.indexOf("\"");
            //urlStr = urlStr.substring(0, endIndex);


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

       new DownloadImageTask().execute(createCover(urlStr), index);
    }

    public String createCover(String el) {
        String urlCover = "";
        int startIndex = 0;
        int endIndex = 0;
        StringBuffer buffer = new StringBuffer();
        String text = "", urlStr = "";
        BufferedReader in;


        try {
            URL url = new URL(el);
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

            startIndex = text.indexOf("/images/Posters/big/");
            endIndex = text.indexOf("jpg");
            urlCover = text.substring(startIndex, endIndex) + "jpg";
            try {
                FriendClass.tv.get(Integer.valueOf(index)).setUrl(urlCover);
            }catch (Exception e){

            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "http://www.serialdata.ru" + urlCover;
    }


        class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
            int index = 0;
        @Override
        protected Bitmap doInBackground(String... params) {
            publishProgress(new Void[]{}); //or null

            String url = "";
            if (params.length > 0) {
                url = params[0];
            }
            index = Integer.valueOf(params[1]);

            InputStream input = null;
            try {
                URL urlConn = new URL(url);
                input = urlConn.openStream();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
                if(Values.setCover) {
                    FriendClass.tv.get(index).setCover(BitmapFactory.decodeStream(input));

                }

            return BitmapFactory.decodeStream(input);
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);

        }

        @Override
        protected void onPostExecute(Bitmap result) {
            super.onPostExecute(result);
            //cover = result;
            //GarretHomeActivity.startCreateList2 = true;

        }
    }

}