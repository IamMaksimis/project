package com.iammaksimus.garret;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Environment;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimeZone;

/**
 * Created by 111 on 17.01.2016.
 */


public class ServiceNotification extends IntentService {

    static ArrayList<ElementTvShow> elementTvShows = new ArrayList<>();
    static ArrayList<ElementTvShow> myelementTvShows = new ArrayList<>();
    ArrayList<String> listTvShow = new ArrayList<>();
    ArrayList<String> myTv = new ArrayList<>();
    String[] data, name;
    static int month, day, year;
    int count = 0;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        //myelementTvShows.clear();
     /*   if(!readFileSD().equals("")){
            count = Integer.valueOf(readFileSD());
            name = new String[count];
            data = new String[count];
        }
        for(int i = 0; i < count; i++){
            readCache(i);
        }
        String tvShow = "";
        boolean not = false;
        Calendar now = Calendar.getInstance(TimeZone.getDefault());
        //Toast.makeText(this, myelementTvShows.size() + "", Toast.LENGTH_LONG).show();
        for(int i = 0; i < count; i++){
            if(data[i].contains(String.valueOf(now.get(Calendar.DATE)))){
                if(tvShow.equals("")){
                    tvShow += name[i];
                }else{
                    tvShow +=", " + name[i];
                }

                not = true;
            }
        }
        if(not) {
            notification(this, tvShow);
        }
        */
        boolean start = false;
        String not = "";
        check();
        String tv = readFileSDTV();
        String[] tmp;
        tmp = tv.split("///");
        if(tmp.length > 3) {
            String[] tmp2 = tmp[4].split("@@@");
            for (int i = 0; i < tmp2.length; i++) {
                for (int j = 0; j < myTv.size(); j++) {
                    if (tmp2[i].equals(myTv.get(j))) {
                        if (not.equals("")) {
                            not += tmp2[i];
                        } else {
                            not += ", " + tmp2[i];
                        }
                        start = true;
                    }
                }
            }
            if (start) {
                notification(this, not);
            }
        }

    }

    public ServiceNotification() {
        super("garret");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //Toast.makeText(this, "startS", Toast.LENGTH_LONG).show();
    }

    public void notification(Context context, String message) {
        // Set Notification Title
        String strtitle = context.getString(R.string.notificationtitle);
        // Open NotificationView Class on Notification Click
        Intent intent = new Intent(context, GarretHomeActivity.class);
        // Send data to NotificationView Class
        intent.putExtra("title", strtitle);
        intent.putExtra("text", message);
        // Open NotificationView.java Activity
        PendingIntent pIntent = PendingIntent.getActivity(context, 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        Uri ringURI =
                RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        // Create Notification using NotificationCompat.Builder
        NotificationCompat.Builder builder = new NotificationCompat.Builder(
                context)
                // Set Icon
                .setSmallIcon(R.drawable.garret)
                        // Set Ticker Message
                .setTicker(message)
                        // Set Title
                .setContentTitle(context.getString(R.string.notificationtitle))
                        // Set Text
                .setContentText(message)
                        // Set PendingIntent into Notification
                .setContentIntent(pIntent)
                        // Dismiss Notification
                .setAutoCancel(true)
                .setSound(ringURI)
                .setLights(Color.MAGENTA, 0, 1);

        // Create Notification Manager
        NotificationManager notificationmanager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);
        // Build Notification with Notification Manager
        notificationmanager.notify(0, builder.build());


    }

    public String readFileSD() {
        // проверяем доступность SD
        if (!Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            return "";
        }
        // получаем путь к SD
        File sdPath = Environment.getExternalStorageDirectory();
        // добавляем свой каталог к пути
        sdPath = new File(sdPath.getAbsolutePath() + "/" + "garret");
        // формируем объект File, который содержит путь к файлу
        File sdFile = new File(sdPath, "cache.dat");
        String str = "";
        try {
            // открываем поток для чтения
            BufferedReader br = new BufferedReader(new FileReader(sdFile));
            str = br.readLine();
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        return str;
    }


    public void readCache(int i) {
        // проверяем доступность SD
        if (!Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            return;
        }
        // получаем путь к SD
        File sdPath = Environment.getExternalStorageDirectory();
        // добавляем свой каталог к пути
        sdPath = new File(sdPath.getAbsolutePath() + "/" + "garret/" + "garretTvShowCache" + i);
        // формируем объект File, который содержит путь к файлу
        File sdFile = new File(sdPath, "garret" + i + ".dat");
        String[] str = new String[8];
        try {
            // открываем поток для чтения
            BufferedReader br = new BufferedReader(new FileReader(sdFile));
            str[0] = br.readLine();
            str[1] = br.readLine();
            str[2] = br.readLine();
            str[3] = br.readLine();
            str[4] = br.readLine();
            str[5] = br.readLine();
            str[6] = br.readLine();
            str[7] = br.readLine();
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        name[i] = str[0];
        data[i] = str[2];
       // myelementTvShows.add(new ElementTvShow(str[0], str[1], str[4], str[3], str[5], str[2], str[7], str[6]));
    }




    public String readFileSDTV() {
        // проверяем доступность SD
        if (!Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            return "";
        }
        // получаем путь к SD
        File sdPath = Environment.getExternalStorageDirectory();
        // добавляем свой каталог к пути
        sdPath = new File(sdPath.getAbsolutePath() + "/" + "garret");
        // формируем объект File, который содержит путь к файлу
        File sdFile = new File(sdPath, "us.dat");
        String str = "";
        try {
            // открываем поток для чтения
            BufferedReader br = new BufferedReader(new FileReader(sdFile));
            str = br.readLine();
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        return str;
    }


    public void parse(){
        InputStream sin;
        StringBuffer buffer = new StringBuffer();
        String text ="";
        BufferedReader in;
        int startIndex = 0, endIndex = 0;
        Calendar now = Calendar.getInstance( TimeZone.getDefault() );
        int m = now.get(Calendar.MONTH) + 1;
        int y = now.get(Calendar.YEAR);
        int d = now.get(Calendar.DATE);

        try {
            String webPage = "http://www.serialdata.ru/calendar.php?m=" + m + "&y=" + y + "&date=" + d + "." + m + "." + y;
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



            while(text.indexOf("<p class=\"search_list_img\">") >= 0){
                startIndex = text.indexOf("<p class=\"search_list_img\">");
                endIndex = text.indexOf("<div class=\"search_list_info\"></div>");
                String tmp = "";
                tmp = text.substring(startIndex, endIndex+36);
                text = text.substring(endIndex+36);
                listTvShow.add(tmp);
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public String createElment(String el){


        String rusName = "";
        int startIndex;
        int endIndex;


        startIndex = el.indexOf("fir_name\">") + 11;
        endIndex = el.indexOf("</span") - 2;
        rusName = el.substring(startIndex, endIndex);


        return rusName;
    }

    public void check(){
        parse();
        for(int i = 0; i < listTvShow.size(); i++){
            myTv.add(createElment(listTvShow.get(i)).replaceAll("\t", ""));
        }
    }
}
