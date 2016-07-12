package com.iammaksimus.recipes;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ListView;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by 111 on 11.06.2016.
 */
public class Step {
    private boolean load = true;
    private String
                cover,
                name;
    Bitmap image;

    Step(String cover, String name){
        this.cover = cover;
        this.name = name.replaceAll("< /br>", "");
    }

    public String getName() {
        return name;
    }

    public Bitmap getImage() {
        return image;
    }

    public String getCover() {
        return cover;
    }
    public void loadCover(ListView list){
        if(load){
            new  DownloadImageTask().execute(cover);
            load = false;
        }else{
            list.invalidateViews();
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
            image = null;
            image = result;
            //.list.invalidateViews();

        }
    }
}
