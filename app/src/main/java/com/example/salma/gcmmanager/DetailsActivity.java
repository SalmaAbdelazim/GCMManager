package com.example.salma.gcmmanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class DetailsActivity extends AppCompatActivity {

    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        textView =(TextView) findViewById(R.id.textView);
        Intent intent=getIntent();
        String msg = intent.getStringExtra("msg");
        String date = intent.getStringExtra("date");
        textView.setText("The job that sent at "+date+" is accepted");

    }
}
