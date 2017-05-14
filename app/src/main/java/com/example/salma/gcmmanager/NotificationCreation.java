package com.example.salma.gcmmanager;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by salma on 13/05/2017.
 */

public class NotificationCreation {


    String API_KEY = "AAAAGn0gL0w:APA91bEsOlP_isCSPY-wkt6GZ-4GTPyv434jGBfZjWTSbO_TX96gGTAXXZLShYmrjAb5zlLpagYC8nr6XFqx8xOoivwW8jRz7yrjJWTl4zQCdT2iQJtGoHa4lyKjNWhEdeQ15f_VT0XJ";

    String to ="/topics/emp";


    public void sendNotification(final String message, final String token, final String date) {


        Thread th = new Thread(new Runnable() {
            @Override
            public void run() {


                try {
                    // Prepare JSON containing the GCM message content. What to send and where to send.

                    JSONObject jGcmData = new JSONObject();
                    JSONObject jData = new JSONObject();
                    jData.put("message", message);
                    jData.put("time",date);
                    jData.put("acceptedToken",token);


                    jGcmData.put("to", to);


                    jGcmData.put("data", jData);

                    // Create connection to send GCM Message request.
                    URL url = new URL("https://android.googleapis.com/gcm/send");
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestProperty("Authorization", "key=" + API_KEY);
                    conn.setRequestProperty("Content-Type", "application/json");
                    conn.setRequestMethod("POST");
                    conn.setDoOutput(true);

                    // GCM message content.
                    OutputStream outputStream = conn.getOutputStream();
                    outputStream.write(jGcmData.toString().getBytes());

                    // GCM response.
                    InputStream inputStream = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                    Log.i("result", "4");
                    StringBuilder str = new StringBuilder();
                    String line = null;


                    while ((line = reader.readLine()) != null) {
                        str.append(line);

                    }

                    String resultFromWs = str.toString();
                    Log.i("result", resultFromWs);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }


        });
        th.start();
    }
}
