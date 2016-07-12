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
 * Created by 111 on 27.05.2016.
 */
public class Recipe implements Comparable{
    private boolean load = true;
    private String
            name,
            category,
            ingredients,
            national,
            cover,
            url,
            time;
    private String min;
    Bitmap image;

    private int wieght = 0;

    Recipe(String name, String category, String ingredients, String national, String cover, String url){
        this.category = category;
        this.cover = cover;
        this.name = name.replaceAll("&quot;", "");
        this.ingredients = ingredients.replaceAll("&quot;", "");
        this.national = national;
        this.url = url;
        this.min = "";
        //getTime();

    }
    Recipe(String name, String category, String ingredients, String national, String cover, String url, int wieght){
        this.category = category;
        this.cover = cover;
        this.name = name.replaceAll("&quot;", "");
        this.ingredients = ingredients.replaceAll("&quot;", "");
        this.national = national;
        this.url = url;
        this.wieght = wieght;
        this.min = "";
        //getTime();
    }

    public void loadCover(ListView list){
        if(load){
            if(cover != "Нет информации") {
                new DownloadImageTask().execute(cover);
            }
            load = false;
        }else{
            list.invalidateViews();
        }
    }

    public void getTime() {
        new ParserTime().execute(this);
    }



    public String getMin() {
        return min;
    }

    public String getCategory() {
        return category;
    }

    public String getCover() {
        return cover;
    }

    public String getIngredients() {
        return ingredients;
    }

    public String getName() {
        return name;
    }

    public String getNational() {
        return national;
    }

    public String getUrl() {
        return url;
    }

    public Bitmap getImage() {
        return image;
    }

    public int getWeght() {
        return wieght;
    }

    public void setWeght(int weght) {
        this.wieght = weght;
    }

    @Override
    public int compareTo(Object obj)
    {
        Recipe tmp = (Recipe)obj;
        if(this.getWeght() < tmp.getWeght())
        {
      /* текущее меньше полученного */
            return -1;
        }
        else if(this.getWeght() > tmp.getWeght())
        {
      /* текущее больше полученного */
            return 1;
        }
    /* текущее равно полученному */
        return 0;
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
