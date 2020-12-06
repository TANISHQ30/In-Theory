package com.android.homeit.ui.submithouse;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.homeit.ActivityHouseThankYou;
import com.android.homeit.ActivityLogin;
import com.android.homeit.ActivityRegister;
import com.android.homeit.FullscreenActivityLogin;
import com.android.homeit.MainActivity;
import com.android.homeit.R;
import com.android.homeit.common.HouseSubmission;
import com.android.homeit.common.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class SubmitHouseFragment extends Fragment {

    private TextView ctext;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser currentFirebaseUser;
    private Button thankyoubutton;
    private DatePickerDialog ybPicker, yrPicker;
    private Spinner HouseWaterfront, HouseCondition, HouseView;
    private EditText noofbedrooms, noofbathrooms, livingarea, lotarea, floors, yearBuild, yearRenovated, areaabove, areabasement, zipcode, latitude, longitude, living2015, lot2015;
    private String nobedrooms, nobathrooms, la, lota, f, yb, yr, aa, ab, z, lat, lon, la2015, l2015, v, water, condn, picker;
    private String a,b,c,d,e,g,h,i,j,k,l,m,n,o,p,q,r,s,t,u,w,x,y;

    private DatabaseReference databaseReference;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_submit_house, container,false);

        noofbedrooms = (EditText) root.findViewById(R.id.house_noofbedrooms);
        noofbathrooms = (EditText) root.findViewById(R.id.house_noofbathrooms);
        livingarea = (EditText) root.findViewById(R.id.house_livingarea);
        lotarea = (EditText) root.findViewById(R.id.house_lotarea);
        floors = (EditText) root.findViewById(R.id.house_floors);
        yearBuild = (EditText) root.findViewById(R.id.house_yearbuild);
        yearRenovated = (EditText) root.findViewById(R.id.house_yearrenovtated);
        areaabove = (EditText) root.findViewById(R.id.house_areaabove);
        areabasement = (EditText) root.findViewById(R.id.house_areabasement);
        zipcode = (EditText) root.findViewById(R.id.house_zipcode);
        latitude = (EditText) root.findViewById(R.id.house_latitude);
        longitude = (EditText) root.findViewById(R.id.house_longitude);
        living2015 = (EditText) root.findViewById(R.id.house_livingarea2015);
        lot2015 = (EditText) root.findViewById(R.id.house_lotarea2015);

        HouseWaterfront = (Spinner) root.findViewById(R.id.house_spinnerwaterfront);
        HouseView = (Spinner) root.findViewById(R.id.house_spinnerview);
        HouseCondition = (Spinner) root.findViewById(R.id.house_spinnercondition);

        thankyoubutton = (Button) root.findViewById(R.id.house_buttoncontinue);
        HouseWaterfront = (Spinner) root.findViewById(R.id.house_spinnerwaterfront);

        super.onCreate(savedInstanceState);

        thankyoubutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkCredentials();
            }
        });

        System.out.println("House waterfront :"+HouseWaterfront.getSelectedItem().toString());

        picker="";

        yearBuild.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                picker = "yearbuild";
                new DatePickerDialog(getContext(), date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        yearRenovated.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                picker = "yearrenovated";
                new DatePickerDialog(getContext(), date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        /*ctext = root.findViewById(R.id.text_submit_house);
        ctext.setText("Submit House Fragment");*/

        return root;
    }

    final Calendar myCalendar = Calendar.getInstance();

    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel();
        }

    };

    private void updateLabel() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.UK);

        if(picker.compareTo("yearbuild") == 0)
            yearBuild.setText(sdf.format(myCalendar.getTime()));
        else if(picker.compareTo("yearrenovated") == 0)
            yearRenovated.setText(sdf.format(myCalendar.getTime()));
    }

    private void checkCredentials() {
        nobedrooms = noofbedrooms.getText().toString().trim();
        nobathrooms = noofbathrooms.getText().toString().trim();
        la = livingarea.getText().toString().trim();
        lota = lotarea.getText().toString().trim();
        f = floors.getText().toString().trim();
        yb = yearBuild.getText().toString().trim();
        yr = yearRenovated.getText().toString().trim();
        water = HouseWaterfront.getSelectedItem().toString().trim();
        v = HouseView.getSelectedItem().toString().trim();
        condn = HouseCondition.getSelectedItem().toString().trim();
        aa = areaabove.getText().toString().trim();
        ab = areabasement.getText().toString().trim();
        z = zipcode.getText().toString().trim();
        lat = latitude.getText().toString().trim();
        lon = longitude.getText().toString().trim();
        la2015 = livingarea.getText().toString().trim();
        l2015 = lotarea.getText().toString().trim();

        if (nobedrooms.isEmpty()) {
            noofbedrooms.setError("Please enter 'No of Bedrooms' !");
            noofbedrooms.requestFocus();
            return;
        }

        if (nobathrooms.isEmpty()) {
            noofbathrooms.setError("Please enter No of Bathrooms !");
            noofbathrooms.requestFocus();
            return;
        }

        if (la.isEmpty()) {
            livingarea.setError("Please enter Living Area");
            livingarea.requestFocus();
            return;
        }

        if (lota.isEmpty()) {
            lotarea.setError("Please enter Lot Area");
            lotarea.requestFocus();
            return;
        }

        if (f.isEmpty()) {
            floors.setError("Please enter No of Floors");
            floors.requestFocus();
            return;
        }

        if(water.isEmpty()) {
            Toast.makeText(getContext(),"Please select an option for Waterfront",Toast.LENGTH_SHORT).show();
        }
        else{
            if(water.compareTo("Yes") == 0){
                water = "1";
            }
            else
                water = "0";

        }

        if(v.isEmpty()) {
            Toast.makeText(getContext(),"Please select an option for Waterfront",Toast.LENGTH_SHORT).show();
        }
        else{
            if(v.compareTo("Yes") == 0)
                v = "1";
            else
                v = "0";
        }

        if(condn.isEmpty()) {
            Toast.makeText(getContext(),"Please select an option for Waterfront",Toast.LENGTH_SHORT).show();
        }
        else{
            if(condn.compareTo("Great(Good as new)") == 0)
                condn = "5";
            else if(condn.compareTo("Good") == 0)
                condn = "4";
            else if(condn.compareTo("Pleasant") == 0)
                condn = "3";
            else if(condn.compareTo("Moderate") == 0)
                condn = "2";
            else
                condn = "1";
        }

        if (yb.isEmpty()) {
            yearBuild.setError("Please enter Year Build");
            yearBuild.requestFocus();
            return;
        }

        if (yr.isEmpty()) {
            yearRenovated.setError("Please enter Year Renovated");
            yearRenovated.requestFocus();
            return;
        }

        if (aa.isEmpty()) {
            areaabove.setError("Please enter Area Above");
            areaabove.requestFocus();
            return;
        }

        if (ab.isEmpty()) {
            areabasement.setError("Please enter Area Basement");
            areabasement.requestFocus();
            return;
        }

        if (z.isEmpty()) {
            zipcode.setError("Please enter Zipcode");
            zipcode.requestFocus();
            return;
        }

        if (lat.isEmpty()) {
            latitude.setError("Please enter Latitude");
            latitude.requestFocus();
            return;
        }

        if (lon.isEmpty()) {
            longitude.setError("Please enter Longitude");
            longitude.requestFocus();
            return;
        }

        if (la2015.isEmpty()) {
            living2015.setError("Please enter Living Area 2015");
            living2015.requestFocus();
            return;
        }

        if (l2015.isEmpty()) {
            lot2015.setError("Please enter Lot Area 2015");
            lot2015.requestFocus();
            return;
        }

        //if (!nobedrooms.isEmpty() &&!nobathrooms.isEmpty() && !la.isEmpty() && !lota.isEmpty() && !f.isEmpty() && !yb.isEmpty() && !yr.isEmpty() && !aa.isEmpty() && !ab.isEmpty() && !z.isEmpty() &&)

        HouseSubmission hs = new HouseSubmission(nobedrooms, nobathrooms, la, lota, f, yb, yr, aa, ab, z, lat, lon, la2015, l2015, v, water, condn);

        DatabaseReference ref ;

        String key = FirebaseAuth.getInstance().getUid();

        ref = FirebaseDatabase.getInstance().getReference("User");
        /*FirebaseDatabase.getInstance().getReference("User")
                //.child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .setValue(FirebaseAuth.getInstance().getCurrentUser().getUid());*/

        ref.child(key).child("House Submission").setValue(hs).addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {

                if(task.isSuccessful()){
                        startActivity(new Intent(getContext(), ActivityHouseThankYou.class ));
                        getActivity().finish();
                }
                else {
                    Toast.makeText(getContext(), "Please try again", Toast.LENGTH_LONG).show();
                }
            }
        });

        /*DatabaseReference ref=FirebaseDatabase.getInstance().getReference().child(key);
        Map<String, Object> updates = new HashMap<String,Object>();
        updates.put("House Submission", hs);
        ref.updateChildren(updates);*/

        /*DatabaseReference dref = FirebaseDatabase.getInstance().getReference("Users/"+key);
        dref
                .child("House Submission")
                .setValue(hs);

        /*databaseReference = FirebaseDatabase.getInstance().getReference("HouseSubmission");
        currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();



        String id = currentFirebaseUser.getUid();

        databaseReference.child(id).setValue(hs);*/

        Log.d(TAG,"House submission successfull!!");

    }

    /*public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if ((keyCode == KeyEvent.KEYCODE_BACK))
        {
            startActivity(new Intent(getContext(), FullscreenActivityLogin.class));
            root.finish();
        }
        return super.onKeyDown(keyCode, event);
    }*/
}
