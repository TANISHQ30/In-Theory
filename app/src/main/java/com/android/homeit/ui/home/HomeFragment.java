package com.android.homeit.ui.home;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.ui.AppBarConfiguration;

import com.android.homeit.ActivityLogin;
import com.android.homeit.ActivityShowHouseSubmission;
import com.android.homeit.ActivityShowLoanQuotes;
import com.android.homeit.FullscreenActivity;
import com.android.homeit.FullscreenActivityLogin;
import com.android.homeit.MainActivity;
import com.android.homeit.R;
import com.android.homeit.common.User;
import com.android.homeit.common.UserSession;
import com.facebook.CallbackManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static android.content.ContentValues.TAG;

public class HomeFragment extends Fragment {

    private static final int REQUEST_CALL = 1;
    private HomeViewModel homeViewModel;
    private RelativeLayout rel1;
    private RelativeLayout rel2;
    private RelativeLayout rel3;
    private TextView textViewName;
    private UserSession userSession;
    private String name;
    private String personGivenName;
    private FirebaseUser user;
    private DatabaseReference ref;
    private String userID;
    private String personFamilyName;
    private String personalEmail;
    private String personId;
    private  Uri personPhoto;
    private AppBarConfiguration mAppBarConfiguration;
    private GoogleSignInClient mGoogleSignInClient;
    private DatabaseReference mDatabase;
    private FirebaseAuth firebaseAuth;
    private CallbackManager mCallbackManager;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);

        rel1 = (RelativeLayout) root.findViewById(R.id.relLayouthousesubmission);
        rel2 = (RelativeLayout) root.findViewById(R.id.relLayoutloanquote);
        rel3 = (RelativeLayout) root.findViewById(R.id.relLayoutphone);
        textViewName = (TextView) root.findViewById(R.id.textViewName);

        // [START initialize_database_ref]
        mDatabase = FirebaseDatabase.getInstance().getReference();
        // [END initialize_database_ref]

        firebaseAuth = FirebaseAuth.getInstance();
        final FirebaseUser mUser = firebaseAuth.getCurrentUser();

        user = FirebaseAuth.getInstance().getCurrentUser();
        ref = FirebaseDatabase.getInstance().getReference("User");
        userID = user.getUid();
        System.out.println(userID);

        //logoutButton = (Button) root.findViewById(R.id.buttonLogout);

        ref.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userProfile = snapshot.getValue(User.class);
                System.out.println(userProfile);
                if(userProfile != null){
                    System.out.println(userProfile.first);
                    personGivenName = userProfile.first;
                    textViewName.setText(personGivenName);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(),"Something went wrong!",Toast.LENGTH_SHORT).show();
            }
        });


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(HomeFragment.this.getResources().getString(R.string.server_client_id))
                .requestEmail().build();

            GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(getContext());
            if(account != null){
                name = account.getDisplayName();
                personGivenName = account.getGivenName();
                personFamilyName = account.getFamilyName();
                personalEmail = account.getEmail();
                personId = account.getId();
                personPhoto = account.getPhotoUrl();
//                userSession.createUserLoginSession(personId, name, personGivenName, personFamilyName, personalEmail, true);
            }

            if(mUser != null){
                 personGivenName = mUser.getDisplayName();
                 personalEmail = mUser.getEmail();
                 personPhoto = mUser.getPhotoUrl();
            }

        textViewName.setText(personGivenName);

        rel1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ActivityShowHouseSubmission.class);
                view.getContext().startActivity(intent);
                getActivity().finish();
            }
        });

        rel2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ActivityShowLoanQuotes.class);
                view.getContext().startActivity(intent);
                getActivity().finish();
            }
        });

        rel3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CALL_PHONE},REQUEST_CALL);
                    Toast.makeText(getContext(),"PERMISSION GRANTED",Toast.LENGTH_SHORT).show();                }
                else
                {
                    Intent callIntent = new Intent(Intent.ACTION_DIAL);
                    callIntent.setData(Uri.parse("tel:+353899788076"));
                    startActivity(callIntent);
                }

            }
        });

        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        //final TextView textView = root.findViewById(R.id.text_home);
        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                //textView.setText(s);
            }
        });
        return root;
    }

    public void basicReadWrite() {
        // [START write_message]
        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("message");

        myRef.setValue("Hello, World!");
        // [END write_message]

        // [START read_message]
        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = dataSnapshot.getValue(String.class);
                Log.d(TAG, "Value is: " + value);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
        // [END read_message]
    }
}