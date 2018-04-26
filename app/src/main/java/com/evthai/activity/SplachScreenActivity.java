package com.evthai.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.evthai.R;

public class SplachScreenActivity extends AppCompatActivity {

    private final int SPLASH_DISPLAY_LENGTH = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splach_screen);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intentMain = new Intent(SplachScreenActivity.this, MainActivity.class);
                SplachScreenActivity.this.startActivity(intentMain);
                SplachScreenActivity.this.finish();
            }
        }, SPLASH_DISPLAY_LENGTH);

    }
}
