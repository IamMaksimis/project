package com.iammaksimus.recipes;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by 111 on 28.06.2016.
 */
public class RecipeDayParser extends AsyncTask<Void, Void, Void> {
    Context context;
    String
            coverHeader,
            cover,
            name;
    @Override
    protected Void doInBackground(Void... params) {
        parserPage("http://www.edimdoma.ru/retsepty?page=1");
        return null;
    }

    private void parserPage(String page) {
        String text = null;
        String
                name,
                urlRecipe,
                cover,
                ingredients;
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
            int start = 0, end = 0;
            try {
                start = text.indexOf("<div class=\"b-page_block__content__picture\">");
                text = text.substring(start);
                start = text.indexOf("<a href=\"");
                text = text.substring(start + 9);
                end = text.indexOf(">");
                urlRecipe = text.substring(0, end - 1);
                Log.d("Parser", urlRecipe);
                text = text.substring(end + 2);
               // start = text.indexOf("img alt=\"");
                end = text.indexOf("src");
                name = text.substring(9, end - 2);
                Log.d("Parser", name);
                text = text.substring(end + 2);
                end = text.indexOf("/>");
                cover = text.substring(3, end - 2);
                Log.d("Parser", cover);
                text = tmp;
                start = text.indexOf("<div class=\"b-recipe_ingredients\">");
                text = text.substring(start + 52);
                end = text.indexOf("</div");
                ingredients = text.substring(0, end);
                start = text.indexOf("<span class=\"truncate_more\" style=\"display: none;\">");
                RecipeDay r = new RecipeDay(name, urlRecipe, cover, ingredients);
                Values.recipeDay = r;
            } catch (Exception e) {

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}