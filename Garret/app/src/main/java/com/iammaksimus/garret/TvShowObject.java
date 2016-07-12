package com.iammaksimus.garret;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by 111 on 14.01.2016.
 */
public class TvShowObject {
    private String name = "", country = "", acters = "", status = "", info = "", urlCover = "";
    private Bitmap cover;

    TvShowObject(String name, String country, String acters, String status, String info, String urlCover) {
        this.name = name.replaceAll("\n", "");
        this.country = country;
        this.acters = acters;
        this.status = status;
        this.info = info.replaceAll("<br>", "");
        this.urlCover = urlCover;
        new DownloadImageTask().execute(urlCover);
    }


    public Bitmap getCover() {
        return cover;
    }

    public String getActers() {
        return acters;
    }

    public String getCountry() {
        return country;
    }

    public String getInfo() {
        return info;
    }

    public String getName() {
        return name;
    }

    public String getStatus() {
        return status;
    }

    public String getUrlCover() {
        return urlCover;
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
            cover = result;
            GarretHomeActivity.startCreateList2 = true;

        }
    }




}
