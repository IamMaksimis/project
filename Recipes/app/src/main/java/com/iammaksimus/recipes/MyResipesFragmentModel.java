package com.iammaksimus.recipes;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import java.util.ArrayList;

/**
 * Created by 111 on 13.06.2016.
 */
public class MyResipesFragmentModel {
    Context context;
    ArrayList<Recipe> recipes;
    SQLiteDatabase db;
    DBHelper myDbHelper;
    MyResipesFragmentModel(Context context) {
        this.context = context;
        myDbHelper = new DBHelper(context);
        try {
            db = myDbHelper.getWritableDatabase();
        }
        catch (SQLiteException ex){
            db = myDbHelper.getReadableDatabase();
        }

    }
}
