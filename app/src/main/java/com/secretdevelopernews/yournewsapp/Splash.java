package com.secretdevelopernews.yournewsapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Timer t = new Timer();
        boolean checkConnection=new ApplicationUtility().checkConnection(this);
        if (checkConnection) {
            t.schedule(new splash(), 3000);
        } else {
            Toast.makeText(Splash.this,
                    "connection not found...plz check connection", Toast.LENGTH_SHORT).show();
            return;
        }
    }

    class splash extends TimerTask {

        @Override
        public void run() {
            Intent i = new Intent(Splash.this,MainActivity.class);
            finish();
            startActivity(i);
        }

    }



}
