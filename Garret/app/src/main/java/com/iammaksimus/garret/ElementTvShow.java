package com.iammaksimus.garret;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by 111 on 11.01.2016.
 */
public class ElementTvShow {
    private String rusName, engName, nameEpisod, numSezon, numEpisod, dataTv, urlCover, url;
    private Bitmap cover;
    boolean load = true;
    ElementTvShow(String rusName, String engName, String nameEpisod, String numSezon, String numEpisod, String dataTv, String urlCover, String url){
        this.rusName = rusName;
        this.engName = engName;
        this.nameEpisod = nameEpisod;
        this.numSezon = numSezon;
        this.numEpisod = numEpisod;
        this.dataTv = dataTv;
        this.urlCover = urlCover;
        this.url = url;

    }

    ElementTvShow(String rusName, String engName, String nameEpisod, String numSezon, String numEpisod, String dataTv){
        this.rusName = rusName;
        this.engName = engName;
        this.nameEpisod = nameEpisod;
        this.numSezon = numSezon;
        this.numEpisod = numEpisod;
        this.dataTv = dataTv;
        this.urlCover = urlCover;
        this.url = url;
        this.cover = cover;
        //new  DownloadImageTask().execute(urlCover);
    }

    public void loadCover(){
        if(load){
            new  DownloadImageTask().execute(urlCover);
            load = false;
        }
    }

    public String getNumEpisod() {
        return numEpisod;
    }

    public String getNumSezon() {
        return numSezon;
    }

    public String getEngName() {
        return engName;
    }

    public String getNameEpisod() {
        return nameEpisod;
    }

    public String getRusName() {
        return rusName;
    }

    public Bitmap getCover() {
        return cover;
    }

    public String getDataTv() {
        return dataTv;
    }

    public String getUrlCover() {
        return urlCover;
    }

    public String getUrl() {
        return url;
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
            GarretHomeActivity.list.invalidateViews();
            GarretHomeActivity.myList.invalidateViews();
            //GarretHomeActivity.list.invalidate();
           /* GarretHomeActivity.countImage++;
            if(GarretHomeActivity.element.size() > 0){
                if(GarretHomeActivity.countImage >= GarretHomeActivity.element.size()){
                    GarretHomeActivity.list.invalidateViews();
                    GarretHomeActivity.myList.invalidateViews();
                    GarretHomeActivity.circl_my.setVisibility(View.INVISIBLE);
                    GarretHomeActivity.countImage = 0;
                }
            }
            if(GarretHomeActivity.searchEl.size() > 0){
                if(GarretHomeActivity.countImage >= GarretHomeActivity.searchEl.size()){
                    GarretHomeActivity.list.invalidateViews();
                    GarretHomeActivity.countImage = 0;
                }
            }
            */
        }
    }
}
