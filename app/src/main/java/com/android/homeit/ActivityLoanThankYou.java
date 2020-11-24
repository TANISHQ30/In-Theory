package com.android.homeit;

import android.annotation.SuppressLint;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;


public class ActivityLoanThankYou extends AppCompatActivity {

    private Button homebackbuttom;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_loan_thank_you);
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        homebackbuttom = (Button) findViewById(R.id.thankyouloanbutton);

        homebackbuttom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActivityLoanThankYou.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

}