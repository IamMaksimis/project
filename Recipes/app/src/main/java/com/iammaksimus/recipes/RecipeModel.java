package com.iammaksimus.recipes;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.io.File;

/**
 * Created by 111 on 11.06.2016.
 */
public class RecipeModel {
    Context context;
    IRecipe iRecipe;
    SQLiteDatabase db;
    RecipeModel(IRecipe iRecipe){
        this.iRecipe = iRecipe;
        this.context = iRecipe.getContext();
        this.db = openDataBase();
    }

    public SQLiteDatabase openDataBase() throws SQLException {
        File DB_PATH = context.getExternalCacheDir();
        Log.e("openDataBase()", "1");
        File dbFile = new File (DB_PATH,"calories");
        Log.e("openDataBase()", "2");
        SQLiteDatabase myDataBase = SQLiteDatabase.openDatabase(dbFile.getAbsolutePath(), null, SQLiteDatabase.OPEN_READWRITE);
        Log.e("openDataBase()", "3");
        return myDataBase;
    }
}
