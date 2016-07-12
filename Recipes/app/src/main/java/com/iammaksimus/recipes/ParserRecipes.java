package com.iammaksimus.recipes;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ListView;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

/**
 * Created by 111 on 27.05.2016.
 */
public class ParserRecipes extends AsyncTask<String, Void, String> {
    private ArrayList<Step> step;
    Context context;
    ListView list;
    IPresenter iPresenter;
    String
            coverHeader,
            cover,
            name,
            count;

    int p = 0;
    @Override
    protected String doInBackground(String... params) {
        context = Values.context;
        Values.step = new ArrayList<>();
        Log.d("Parser url", params[0]);
        String tmp = params[0].replace("//retsepty", "/retsepty");
        parserPage(tmp);
        Intent intent = new Intent(context, ActivityRecipe.class);
        context.startActivity(intent);
        return null;
    }
    @Override
    protected void onPostExecute(String strJson) {
        super.onPostExecute(strJson);
    }

    private void parserPage(String page){
        String text = null;
        try {
            String webPage = page;
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
            String tmp = text;
            int start= 0, end = 0;
            try {
                start = text.indexOf("<a href=\"/photos/recipes/");
                text = text.substring(start);
                start = text.indexOf("itemprop=\"image\"");
                text = text.substring(start + 7);
                end = text.indexOf("title");
                start = text.indexOf("http");
                coverHeader = text.substring(start, end - 2);
                new  DownloadImageTask().execute(coverHeader);
                Log.d("Parser", coverHeader);
            }catch (Exception e){

            }

            text = tmp;
           // start = text.indexOf("data-utk-sku-unit");
           // text = text.substring(start);
            int i = 0;
            while (start > -1){
                start = text.indexOf("<div class=\"g-cleared rec-sposob-inst rec-sposob-inst__with_image\"");
                if(start == -1){
                    break;
                }
                text = text.substring(start + 50);
                start = text.indexOf("src");
                text = text.substring(start);
                end = text.indexOf("width");
                cover = text.substring(5, end - 2);
                text = text.substring(end);
                Log.d("Parser", cover);
                start = text.indexOf("<p>");
                text = text.substring(start);
                end = text.indexOf("</p>");
                name = text.substring(3, end);
                text = text.substring(end + 5);
                Values.step.add(new Step(cover, name));
                Log.d("Parser", name);
            }

        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        @Override
        protected Bitmap doInBackground(String... params) {
            publishProgress(new Void[]{}); //or null

            String url = "";
            if (params.length > 0) {
                url = params[0];
            }

            InputStream input = null;
            try {
                URL urlConn = new URL(url);
                input = urlConn.openStream();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
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
            Values.cover = null;
            //Values.cover = result;
            Values.im.setImageBitmap(result);
            //.list.invalidateViews();

        }
    }

}
