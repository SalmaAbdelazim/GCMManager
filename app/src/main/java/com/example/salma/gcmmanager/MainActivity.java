package com.example.salma.gcmmanager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;

import java.util.Date;

public class MainActivity extends AppCompatActivity {


        Button sendNotification;
        private BroadcastReceiver mRegistrationBroadcastReceiver;
        @Override
        protected void onCreate (Bundle savedInstanceState){
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            sendNotification = (Button) findViewById(R.id.button);

            mRegistrationBroadcastReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    //Check type of intent filter
                    if (intent.getAction().equals(GCMRegistration.REGISTRATION_SUCCESS)) {
                        //Registration success
                        String token = intent.getStringExtra("token");
                     //   Toast.makeText(getApplicationContext(), "GCM token:" + token, Toast.LENGTH_LONG).show();
                    } else if (intent.getAction().equals(GCMRegistration.REGISTRATION_ERROR)) {
                        //Registration error
                        Toast.makeText(getApplicationContext(), "GCM registration error!!!", Toast.LENGTH_LONG).show();
                    }
                }
            };

            //Check status of Google play service in device
            int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getApplicationContext());
            if (ConnectionResult.SUCCESS != resultCode) {
                //Check type of error
                if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                    Toast.makeText(getApplicationContext(), "Google Play Service is not install/enabled in this device!", Toast.LENGTH_LONG).show();
                    //So notification
                    GooglePlayServicesUtil.showErrorNotification(resultCode, getApplicationContext());
                } else {
                    Toast.makeText(getApplicationContext(), "This device does not support for Google Play Service!", Toast.LENGTH_LONG).show();
                }
            } else {
                //Start service
                Intent itent = new Intent(this, GCMRegistration.class);
                startService(itent);
            }

            sendNotification.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    String newDate = new Date().toString();
                    new NotificationCreation().sendNotification("new job offer!!", "", newDate);


                }
            });
        }



        @Override
        protected void onResume () {
            super.onResume();
            LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                    new IntentFilter(GCMRegistration.REGISTRATION_SUCCESS));
            LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                    new IntentFilter(GCMRegistration.REGISTRATION_ERROR));
        }

        @Override
        protected void onPause () {
            super.onPause();
            LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        }


    }