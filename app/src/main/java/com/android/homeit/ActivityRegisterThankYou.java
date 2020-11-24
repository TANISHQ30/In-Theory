package com.android.homeit;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;

public class ActivityRegisterThankYou extends AppCompatActivity {

    private Button homebackbuttom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_thank_you);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        homebackbuttom = (Button) findViewById(R.id.thankyouregisterbutton);

        homebackbuttom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActivityRegisterThankYou.this,FullscreenActivityLogin.class);
                startActivity(intent);
                finish();
            }
        });

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if ((keyCode == KeyEvent.KEYCODE_BACK))
        {
            startActivity(new Intent(ActivityRegisterThankYou.this, FullscreenActivity.class));
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }
}