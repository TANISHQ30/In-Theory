
package com.android.homeit;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.android.homeit.common.UserSession;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;

import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Arrays;


/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class ActivityLogin extends AppCompatActivity {

    private Button forgotPassword;
   // private ProgressBar progressBar;

    private static final String TAG = "ERROR";
    private Button loginwithemail;
    private Button loginwithgoogle;
    private Button buttonFacebookLogin;
    private GoogleSignInClient mGoogleSignInClient;
    private int RC_SIGN_IN = 0;
    private UserSession userSession;
    private FirebaseAuth mAuth;
    private CallbackManager mCallbackManager;
    private EditText userEmail, userPassword;
    private String email, pass, socialmedia;
    private CheckBox checkBoxShowPassword;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

// ...--
// Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        FacebookSdk.sdkInitialize(ActivityLogin.this);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        setContentView(R.layout.activity_login);

        checkBoxShowPassword = (CheckBox) findViewById(R.id.checkBoxShowPassword);
        loginwithemail = (Button) findViewById(R.id.signinemail);
        loginwithgoogle = (Button) findViewById(R.id.logingoogle);
        userEmail = (EditText) findViewById(R.id.loginemail);
        userPassword = (EditText) findViewById(R.id.loginpassword);
        userSession = new UserSession(this);

        //signInButton= (Button) findViewById(R.id.signinemail);

        //signIn.setOnClickListener((View.OnClickListener) this);

        //editTextEmail= (EditText) findViewById(R.id.loginemail);
        //editTextPassword= (EditText) findViewById(R.id.loginpassword);

        forgotPassword=(Button) findViewById(R.id.forgotPassword);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(ActivityLogin.this.getResources().getString(R.string.server_client_id))
                .requestEmail().build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        loginwithgoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });

        loginwithemail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userLogin();
                /*Intent intent = new Intent(ActivityLogin.this, MainActivity.class);
                startActivity(intent);
                finish();*/
            }
        });

        checkBoxShowPassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (!isChecked) {
                    // show password
                    userEmail.setTransformationMethod(PasswordTransformationMethod.getInstance());
                } else {
                    // hide password
                    userPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
            }
        });

        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ActivityLogin.this, ForgotPassword.class));
                finish();
            }
        });

        //Initialize Email Login button


        // Initialize Facebook Login button
        mCallbackManager = CallbackManager.Factory.create();
        buttonFacebookLogin = findViewById(R.id.loginfacebook);

        buttonFacebookLogin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                LoginManager.getInstance().logInWithReadPermissions(ActivityLogin.this, Arrays.asList("email", "public_profile"));

                LoginManager.getInstance().registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        socialmedia = "facebook";
                        Log.d(TAG, "facebook:onSuccess:" + loginResult);
                        handleFacebookAccessToken(loginResult.getAccessToken());
                    }

                    @Override
                    public void onCancel() {
                        Log.d(TAG, "facebook:onCancel");
                        // ...
                    }

                    @Override
                    public void onError(FacebookException error) {
                        Log.d(TAG, "facebook:onError", error);
                        // ...
                    }
                });

            }
        });
    }

    private void handleFacebookAccessToken(AccessToken token) {
        Log.d(TAG, "handleFacebookAccessToken:" + token);

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "Sign In Successful");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "Sign In Failed", task.getException());
                            Toast.makeText(ActivityLogin.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }

                        // ...
                    }
                });
    }

    private void signIn() {
        Intent signIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signIntent,RC_SIGN_IN);
    }

    private void userLogin(){
        email = userEmail.getText().toString().trim();
        pass = userPassword.getText().toString().trim();

        if(email.isEmpty()){
            userEmail.setError("Email is required!");
            userEmail.requestFocus();
            return;
        }

        if(pass.isEmpty()){
            userPassword.setError("Password is required!");
            userPassword.requestFocus();
            return;

        }

//        progressBar.setVisibility(View.VISIBLE);
        mAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()){
                    //email verification
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                    if(user.isEmailVerified()){
                        //redirect to user profile
                        Toast.makeText(ActivityLogin.this, "Sign-in successful", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(ActivityLogin.this, MainActivity.class ));
                        finish();
                    }else{
                        Toast.makeText(ActivityLogin.this, "Check your email to verify your account", Toast.LENGTH_LONG).show();
                    }


                }else{
                    Toast.makeText(ActivityLogin.this, "Please check your credentials", Toast.LENGTH_LONG).show();
                }
            }
        });


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                final ProgressDialog progress;
                progress = new ProgressDialog(this);
                progress.setMessage("Please Wait");
                progress.setCanceledOnTouchOutside(false);
                progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progress.show();

                socialmedia = "googlelogin";

                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                Log.d(TAG, "firebaseAuthWithGoogle:" + account.getId());
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e);
                // ...
            }
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            startActivity(new Intent(ActivityLogin.this, MainActivity.class));
        }
        catch (ApiException e) {
            Log.w("Google Sign In Error","signInResult:failed code ="+e.getStatusCode());
            Toast.makeText(ActivityLogin.this,"Failed",Toast.LENGTH_LONG).show();
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            //Snackbar.make(mBinding.mainLayout, "Authentication Failed.", Snackbar.LENGTH_SHORT).show();
                            updateUI(null);
                        }

                        // ...
                    }
                });
    }

    //Change UI according to user data.
    public void updateUI(FirebaseUser account){

        if(account != null){
//            if(socialmedia.compareTo("googlelogin") == 0 || socialmedia.compareTo("facebook") == 0)
             //   startActivity(new Intent(this, ActivityFacebookDetails.class));
            //else
            startActivity(new Intent(this, MainActivity.class));
            Toast.makeText(this,"Signed-In successfully",Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this,MainActivity.class));
            finish();

        }/*else {
            Toast.makeText(this,"Please Sign-In to continue",Toast.LENGTH_SHORT).show();
        }*/

    }


    protected void onStart() {
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if(account != null){
            /*String name = account.getDisplayName();
            String personGivenName = account.getGivenName();
            String personFamilyName = account.getFamilyName();
            String personalEmail = account.getEmail();
            String personId = account.getId();
            Uri personPhoto = account.getPhotoUrl();
            userSession.createUserLoginSession(personId, name, personGivenName, personFamilyName, personalEmail, true);*/
            if(socialmedia.compareTo("googlelogin") == 0 || socialmedia.compareTo("facebook") == 0)
                startActivity(new Intent(this, ActivityFacebookDetails.class));
            else
                startActivity(new Intent(this, MainActivity.class));
            finish();
        }
        super.onStart();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if ((keyCode == KeyEvent.KEYCODE_BACK))
        {
            startActivity(new Intent(ActivityLogin.this, FullscreenActivityLogin.class));
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }
}
