package com.iammaksimus.recipes;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * Created by 111 on 02.06.2016.
 */
public abstract class Values {
    static String
            category,
            national,
            name,
            ingredients,
            minRecipe = "";
    static HashSet<String> where;
    static ArrayList<Step> step = new ArrayList<>();
    static Context context;
    static Bitmap cover;
    static ImageView im;
    static Recipe recipe;
    static boolean switchIngredient;
    static int count = 0;
    static RecipeDay recipeDay = new RecipeDay(null, null, null, null);
    static LinearLayout recipeDayL;
    static ImageView coverRecipeDay;
    static TextView nameRecipeDay;
    static boolean day = false;
    static ArrayList<String> map;
    static String min;
    static android.os.Handler h;
}
