package com.iammaksimus.recipes;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by 111 on 30.05.2016.
 */
class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context) {
        // конструктор суперкласса
        super(context, context.getExternalFilesDir(null).getAbsolutePath() + "/" + "my_recipe", null, 540);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // создаем таблицу с полями
        db.execSQL("create table my_recipes ("
                + "name text primary key,"
                + "category text,"
                + "ingredients text,"
                + "national text,"
                + "cover text,"
                + "url text" + ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}

