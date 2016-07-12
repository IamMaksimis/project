package com.iammaksimus.garret;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.NotificationCompat;

/**
 * Created by 111 on 18.01.2016.
 */
public class TimeNotif extends AsyncTask<Context, Void, String> {

    @Override
    protected String doInBackground(Context... params) {
        changeTime(params[0]);
        return null;
    }

    @Override
    protected void onPostExecute(String strJson){
        super.onPostExecute(strJson);


    }


    public void changeTime(Context params){
        // Calendar now = Calendar.getInstance(TimeZone.getDefault());
        int time = 0;
        if( time == 59){
            time++;
            notification(params, "");
        }else{
            changeTime(params);
        }
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
                        // Add an Action Button below Notification
                .addAction(R.drawable.friends, "Action Button", pIntent)
                        // Set PendingIntent into Notification
                .setContentIntent(pIntent)
                        // Dismiss Notification
                .setAutoCancel(true);

        // Create Notification Manager
        NotificationManager notificationmanager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);
        // Build Notification with Notification Manager
        notificationmanager.notify(0, builder.build());

    }
}

