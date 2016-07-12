package com.iammaksimus.recipes;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * Created by 111 on 01.06.2016.
 */
public class MainModel {
    IPresenter iPresenter;
    Context context;
    ArrayList<Recipe> recipes;
    SQLiteDatabase db;
    DBHelper myDbHelper;
    MainModel(IPresenter iPresenter) {
        this.iPresenter = iPresenter;
        this.context = iPresenter.getContext();
        //myDbHelper = new DBHelper(context);
        try {
            createDataBase();
            createCalories();
        } catch (IOException e) {
            e.printStackTrace();
            Log.d("createDataBase()", e.toString());
        }
        db = openDataBase();
    }

  /*  ArrayList<Recipe> createMainList() {
        DBHelper myDbHelper = new DBHelper(context);

    }
   */

    public void createDataBase() throws IOException {
        //получаем путь к SD-карте.
        Log.e("createDataBase()", "получаем путь к SD-карте.");
        File DB_PATH = context.getExternalCacheDir();
        //создаём каталог для нашей базы данных
        DB_PATH.mkdirs();
        Log.e("createDataBase()", "создаём каталог для нашей базы данных.");
        //проверяем есть ли уже файл БД на карте
        File db = new File(DB_PATH, "recipe_db");
        Log.e("createDataBase()", "проверяем есть ли уже файл БД на карте");
        if (!db.exists()) {
            //если файла нет, то попытаемся его создать
            db.createNewFile();
            Log.e("createDataBase()", "если файла нет, то попытаемся его создать");
            try {
                copyFromZipFile();
            } catch (IOException e) {
                Log.d("Error", e.toString());
                Log.e("createDataBase()", e.toString());
                throw new Error("Error copying database", e);
            }
        }

    }


    private void copyFromZipFile() throws IOException{

        InputStream is = context.getResources().openRawResource(R.raw.recipe_db);
        Log.e("copyFromZipFile()", "1");
        File DB_PATH = context.getExternalCacheDir();
        Log.e("copyFromZipFile()", "2");
        File outFile = new File(DB_PATH , "recipe_db");
        Log.e("copyFromZipFile()", "3");
        OutputStream myOutput = new FileOutputStream(outFile.getAbsolutePath());
        Log.e("copyFromZipFile()", "4");
        ZipInputStream zis = new ZipInputStream(new BufferedInputStream(is));
        Log.e("copyFromZipFile()", "5");
        try {
            ZipEntry ze;
            while ((ze = zis.getNextEntry()) != null) {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                byte[] buffer = new byte[8024];
                int count;

                while ((count = zis.read(buffer)) != -1) {
                    myOutput.write(buffer, 0, count);
                   // baos.write(buffer, 0, count);
                }
               // baos.writeTo(myOutput);
            }
        } finally {
            Log.e("copyFromZipFile()", "6");
            zis.close();
            myOutput.flush();
            myOutput.close();
            is.close();
        }
    }

    public void createCalories() throws IOException {
        //получаем путь к SD-карте.
        Log.e("createDataBase()", "получаем путь к SD-карте.");
        File DB_PATH = context.getExternalCacheDir();
        //создаём каталог для нашей базы данных
        DB_PATH.mkdirs();
        Log.e("createDataBase()", "создаём каталог для нашей базы данных.");
        //проверяем есть ли уже файл БД на карте
        File db = new File(DB_PATH, "calories");
        Log.e("createDataBase()", "проверяем есть ли уже файл БД на карте");
        if (!db.exists()) {
            //если файла нет, то попытаемся его создать
            db.createNewFile();
            Log.e("createDataBase()", "если файла нет, то попытаемся его создать");
            try {
                copyFromZipCalories();
            } catch (IOException e) {
                Log.d("Error", e.toString());
                Log.e("createDataBase()", e.toString());
                throw new Error("Error copying database", e);
            }
        }

    }

    private void copyFromZipCalories() throws IOException{

        InputStream is = context.getResources().openRawResource(R.raw.calories);
        Log.e("copyFromZipFile()", "1");
        File DB_PATH = context.getExternalCacheDir();
        Log.e("copyFromZipFile()", "2");
        File outFile = new File(DB_PATH , "calories");
        Log.e("copyFromZipFile()", "3");
        OutputStream myOutput = new FileOutputStream(outFile.getAbsolutePath());
        Log.e("copyFromZipFile()", "4");
        ZipInputStream zis = new ZipInputStream(new BufferedInputStream(is));
        Log.e("copyFromZipFile()", "5");
        try {
            ZipEntry ze;
            while ((ze = zis.getNextEntry()) != null) {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                byte[] buffer = new byte[8024];
                int count;

                while ((count = zis.read(buffer)) != -1) {
                    myOutput.write(buffer, 0, count);
                    // baos.write(buffer, 0, count);
                }
                // baos.writeTo(myOutput);
            }
        } finally {
            Log.e("copyFromZipFile()", "6");
            zis.close();
            myOutput.flush();
            myOutput.close();
            is.close();
        }
    }



    public SQLiteDatabase openDataBase() throws SQLException {
        File DB_PATH = context.getExternalCacheDir();
        Log.e("openDataBase()", "1");
        File dbFile = new File (DB_PATH,"recipe_db");
        Log.e("openDataBase()", "2");
        SQLiteDatabase myDataBase = SQLiteDatabase.openDatabase(dbFile.getAbsolutePath(), null, SQLiteDatabase.OPEN_READWRITE);
        Log.e("openDataBase()", "3");
        return myDataBase;
    }
}
