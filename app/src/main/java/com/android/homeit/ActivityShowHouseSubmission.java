package com.android.homeit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ActivityShowHouseSubmission extends AppCompatActivity {

    private Button homebackbuttom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_show_house_submission);

        homebackbuttom = (Button) findViewById(R.id.homescreen);

        homebackbuttom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActivityShowHouseSubmission.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}