package com.android.homeit;

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.util.Patterns;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.homeit.common.User;
import com.facebook.login.LoginManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;


public class ActivityRegister extends AppCompatActivity {

    private Button homebackbutton;
    private Button register;
    private FirebaseAuth mAuth;
    private EditText regEmail, regPassword, firstName, lastName, gender, dateOfBirth , contactNumber;
    private String email, password, fname, lname, gen, dob, contact_no;
    private static String TAG = "ok";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_register);
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        regEmail = (EditText) findViewById(R.id.registeremail);
        regPassword = (EditText) findViewById(R.id.registerpassword);
        firstName = (EditText) findViewById(R.id.registerfirstname);
        lastName = (EditText) findViewById(R.id.registerlastname);
        //gender = (EditText) findViewById(R.id.registerfirstname);
        dateOfBirth = (EditText) findViewById(R.id.registerdob);
        contactNumber = (EditText) findViewById(R.id.registerphoneno);
        gender = (EditText) findViewById(R.id.registergender);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        //homebackbuttom = (Button) findViewById(R.id.registerbutton);
        register = (Button) findViewById(R.id.registerbutton);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkCredentials();
            }
        });

        /*register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }*/
    }

    /*@Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
       // updateUI(currentUser);
    }
*/
    private void checkCredentials() {
        email = regEmail.getText().toString().trim();
        password = regPassword.getText().toString().trim();
        fname = firstName.getText().toString().trim();
        lname = lastName.getText().toString().trim();
        dob = dateOfBirth.getText().toString().trim();
        contact_no = contactNumber.getText().toString().trim();
        gen = gender.getText().toString().trim();

        if(fname.isEmpty()){
            firstName.setError("Please enter first name");
            firstName.requestFocus();
            return;
        }

        if(lname.isEmpty()){
            lastName.setError("Please enter last name");
            lastName.requestFocus();
            return;
        }

        if(dob.isEmpty()){
            dateOfBirth.setError("Please enter date of birth");
            dateOfBirth.requestFocus();
            return;
        }

        if(email.isEmpty()){
            regEmail.setError("Please provide valid email");
            regEmail.requestFocus();
            return;
        }

        if(password.isEmpty()){
            regPassword.setError("Please enter password");
            regPassword.requestFocus();
            return;
        }


        if(password.length() < 8){
            regPassword.setError("Password should be more than 8 letters");
            regPassword.requestFocus();
            return;
        }

        if(gen.isEmpty()){
            gender.setError("Please enter your gender");
            gender.requestFocus();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        final ProgressDialog progress;
                        progress = new ProgressDialog(ActivityRegister.this);
                        progress.setMessage("Please Wait");
                        progress.setCanceledOnTouchOutside(false);
                        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                        progress.show();
                        if (task.isSuccessful()) {
                            System.out.println("Success");
                            User u = new User(fname, lname, email, password, dob, contact_no, gen);

                            String key = FirebaseAuth.getInstance().getCurrentUser().getUid();
                            FirebaseDatabase.getInstance().getReference("User/"+key+"/Details")
                                    .setValue(u)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    //.setValue(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    if(task.isSuccessful()){
                                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                        user.sendEmailVerification();
                                        // Toast.makeText(ActivityRegister.this,"User registered");
                                        Log.d(TAG,"Registration successfull");
                                        startActivity(new Intent(ActivityRegister.this, ActivityRegisterThankYou.class));
                                        finish();
                                        System.out.println("User registered");
                                        FirebaseAuth.getInstance().signOut();
                                    }
                                    else{
                                        Log.d(TAG,"Failed, please try again");
                                        System.out.println("User not registered");
                                    }

                                }

                            });
                        }
                        else
                        {
                            System.out.println("User not registered");
                        }

                    }
                });
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if ((keyCode == KeyEvent.KEYCODE_BACK))
        {
            startActivity(new Intent(ActivityRegister.this, FullscreenActivityLogin.class));
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }

}