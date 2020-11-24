package com.android.homeit.ui.submithouse;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.homeit.ActivityHouseThankYou;
import com.android.homeit.MainActivity;
import com.android.homeit.R;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class SubmitHouseFragment extends Fragment {

    private TextView ctext;
    private Button thankyoubutton;
    private Spinner HouseWaterfront;
    private TextView yearBuild, yearRenovated;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_submit_house, container,false);

        thankyoubutton = (Button) root.findViewById(R.id.house_buttoncontinue);
        HouseWaterfront = (Spinner) root.findViewById(R.id.house_spinnerwaterfront);
        yearBuild = (TextView) root.findViewById(R.id.house_yearbuild);
        yearRenovated = (TextView) root.findViewById(R.id.house_yearrenovtated);
        super.onCreate(savedInstanceState);

        thankyoubutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ActivityHouseThankYou.class);
                view.getContext().startActivity(intent);
                getActivity().finish();
            }
        });

        System.out.println("House waterfront :"+HouseWaterfront.getSelectedItem().toString());

        yearBuild.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(getContext(), date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        yearRenovated.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
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

        yearBuild.setText(sdf.format(myCalendar.getTime()));
    }
}
