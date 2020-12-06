package com.android.homeit.ui.gallery;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.android.homeit.ActivityShowHouseSubmission;
import com.android.homeit.ActivityShowLoanQuotes;
import com.android.homeit.R;
import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class GalleryFragment extends Fragment {

    private GalleryViewModel galleryViewModel;
    private RelativeLayout rel1;
    private Button house;
    private Button loan;
    private String name;
    private String personGivenName;
    private String personFamilyName;
    private String personalEmail;
    private String personId;
    private Uri personPhoto;
    private ImageView profilePhoto;
    private FirebaseAuth firebaseAuth;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        galleryViewModel =
                new ViewModelProvider(this).get(GalleryViewModel.class);
        View root = inflater.inflate(R.layout.fragment_gallery, container, false);



       house = (Button) root.findViewById(R.id.housesubmissionprofilebutton);
       loan = (Button) root.findViewById(R.id.loansubmissionprofilebutton);
       profilePhoto = (ImageView) root.findViewById(R.id.imageViewProfilePhoto);

        firebaseAuth = FirebaseAuth.getInstance();
        final FirebaseUser mUser = firebaseAuth.getCurrentUser();

       TextView textView = root.findViewById(R.id.textViewpersonname);
        galleryViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
              //  textView.setText(s);
            }
        });

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
        else if(mUser != null){
            name = mUser.getDisplayName();
            personalEmail = mUser.getEmail();
            personPhoto = mUser.getPhotoUrl();
            //mUser.get
//                userSession.createUserLoginSession(personId, name, personGivenName, personFamilyName, personalEmail, true);
        }

        textView.setText(name);
        Glide.with(this).load(personPhoto).into(profilePhoto);

        house.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ActivityShowHouseSubmission.class);
                view.getContext().startActivity(intent);
                getActivity().finish();
            }
        });

        loan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ActivityShowLoanQuotes.class);
                view.getContext().startActivity(intent);
                getActivity().finish();
            }
        });

        return root;
    }
}