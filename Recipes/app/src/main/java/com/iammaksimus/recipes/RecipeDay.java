package com.iammaksimus.recipes;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by 111 on 28.06.2016.
 */
public class RecipeDay {
    private String
            name,
            url,
            cover,
            ingredients;
    Bitmap image;
    LinearLayout recipeDay;
    ImageView coverRecipeDay;
    TextView nameRecipeDay;
    RecipeDay(String name, String url, String cover, String ingredients){
        if(url != null) {
            this.name = name.replaceAll("&quot;", "");
            this.url = url;
            this.cover = cover;
            this.ingredients = ingredients;
            this.coverRecipeDay = Values.coverRecipeDay;
            this.recipeDay = Values.recipeDayL;
            this.nameRecipeDay = Values.nameRecipeDay;
            nameRecipeDay.setText(name);
            new DownloadImageTask().execute(cover);
        }
    }

    public String getIngredients() {
        return ingredients;
    }

    public String getCover() {
        return cover;
    }

    public Bitmap getImage() {
        return image;
    }

    public String getName() {
        return name;
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
            image = null;
            image = result;
            coverRecipeDay.setImageBitmap(image);
            recipeDay.setVisibility(View.VISIBLE);
            //.list.invalidateViews();

        }
    }
}
