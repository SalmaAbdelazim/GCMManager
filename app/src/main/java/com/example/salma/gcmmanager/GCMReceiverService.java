package com.example.salma.gcmmanager;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.android.gms.gcm.GcmListenerService;

import java.util.Date;
import java.util.Locale;

/**
 * Created by salma on 13/05/2017.
 */

public class GCMReceiverService extends GcmListenerService {

    public String acceptedEmployeeToken;

    @Override
    public void onMessageReceived(String from, Bundle data) {


        String message = data.getString("message");
        ///get the token of accepted employee
        acceptedEmployeeToken=data.getString("acceptedToken");

        String date=data.getString("time");


        ////////create notification that appear to manager
        sendNotification(message,date);
        ////////create notification that will send to Employees
        new NotificationCreation().sendNotification("job of "+date+" already accepted",acceptedEmployeeToken,date);
    }


    private void sendNotification(String message,String date) {


        Intent intent = new Intent(this, DetailsActivity.class);
        intent.putExtra("msg",message);
        intent.putExtra("date",date);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        int requestCode = 0;
        PendingIntent pendingIntent = PendingIntent.getActivity(this, requestCode, intent, PendingIntent.FLAG_ONE_SHOT);

        //Build notification
        NotificationCompat.Builder noBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentText("My GCM message :X:X")
                .setContentText(message+" job from Employee")
                .setAutoCancel(true)
                .setContentIntent(pendingIntent);

        SimpleDateFormat sdf = new SimpleDateFormat("EE MMM dd HH:mm:ss",
                Locale.ENGLISH);

        Date secondDate= null;
        try {
            secondDate = sdf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //////parse to int to set it as notification id
        int i = (int)secondDate.getTime();

        NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(i, noBuilder.build());
    }
}
