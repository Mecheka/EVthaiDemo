package com.evthai.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.evthai.R;
import com.evthai.fragment.LoginFragment;


public class LoginAndRegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_register);

        if (savedInstanceState == null){
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.contentContainer, new LoginFragment())
                    .commit();

        }
    }
}
