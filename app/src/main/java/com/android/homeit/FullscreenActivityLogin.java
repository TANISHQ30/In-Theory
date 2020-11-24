package com.android.homeit;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class FullscreenActivityLogin extends AppCompatActivity {

    private static final int AUTO_HIDE_DELAY_MILLIS = 1500;

    private Button register;
    private Button signin;
    private long backPressedTime;
    private Toast backToast;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fullscreen_login);
        getSupportActionBar().hide();
        handler = new Handler();

        //final UserSession userSession = new UserSession(this);

        register = (Button) findViewById(R.id.registerfullbutton);
        signin = (Button) findViewById(R.id.signinfullbutton);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FullscreenActivityLogin.this, ActivityRegister.class);
                startActivity(intent);
                finish();
            }
        });


        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FullscreenActivityLogin.this, ActivityLogin.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (backPressedTime + 2000 > System.currentTimeMillis()) {
            backToast.cancel();
            super.onBackPressed();
            return;
        } else {
            backToast = Toast.makeText(getBaseContext(), "Press back again to exit", Toast.LENGTH_SHORT);
            backToast.show();
        }
        backPressedTime = System.currentTimeMillis();
    }
}




