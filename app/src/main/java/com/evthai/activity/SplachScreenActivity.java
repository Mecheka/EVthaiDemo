package com.evthai.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.evthai.R;

public class SplachScreenActivity extends AppCompatActivity {

    private final int SPLASH_DISPLAY_LENGTH = 2000;
    private final String SPF_NAME = "tokenperferences";
    private final String TOKEN = "token";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splach_screen);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                SharedPreferences tokenPreferrences = getSharedPreferences(SPF_NAME, Context.MODE_PRIVATE);
                String token = tokenPreferrences.getString(TOKEN, null);
                if (token == null){
                    Intent intentMain = new Intent(SplachScreenActivity.this, LoginAndRegisterActivity.class);
                    SplachScreenActivity.this.startActivity(intentMain);
                    SplachScreenActivity.this.finish();
                }else {
                    Intent intentMain = new Intent(SplachScreenActivity.this, MainActivity.class);
                    SplachScreenActivity.this.startActivity(intentMain);
                    SplachScreenActivity.this.finish();
                }
            }
        }, SPLASH_DISPLAY_LENGTH);

    }
}
