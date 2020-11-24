package com.android.homeit;

import android.annotation.SuppressLint;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

public class FullscreenActivity extends AppCompatActivity {

    private static final int AUTO_HIDE_DELAY_MILLIS = 1500;

    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fullscreen);
        getSupportActionBar().hide();
        handler = new Handler();

        //final UserSession userSession = new UserSession(this);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //onStart();
                GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(getApplicationContext());
                if(account != null){
                    Intent intent = new Intent(FullscreenActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                else {
                    Intent intent = new Intent(FullscreenActivity.this, FullscreenActivityLogin.class);
                    startActivity(intent);
                    finish();
                }
            }
        }, AUTO_HIDE_DELAY_MILLIS);
    }
}
