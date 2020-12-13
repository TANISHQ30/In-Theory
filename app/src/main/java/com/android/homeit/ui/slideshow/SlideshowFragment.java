package com.android.homeit.ui.slideshow;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.android.homeit.MachineLearningKit.MLKit;
import com.android.homeit.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.ml.common.modeldownload.FirebaseModelDownloadConditions;
import com.google.firebase.ml.common.modeldownload.FirebaseModelManager;
import com.google.firebase.ml.custom.FirebaseCustomRemoteModel;

import org.tensorflow.lite.Interpreter;

import java.io.File;

public class SlideshowFragment extends Fragment {

    private SlideshowViewModel slideshowViewModel;
    private Button loansubmit;
    private Spinner maritalStatus, dependents, education, selfEmployed, loanTerm, creditHistory;
    private String inc, coinc, loanamt, marstatus, dep, edu, selfemp, loant, credith;
    private EditText income, coIncome, loanAmount;
    private MLKit mlKit;
    String TAG = "not null";

    public View onCreateView(@NonNull final LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        mlKit = new MLKit();
        FirebaseCustomRemoteModel remote = mlKit.configureHostedModelSource();
        if(remote != null) {
            float a = mlKit.startModelDownloadTask(remote);
        }


        slideshowViewModel =
                new ViewModelProvider(this).get(SlideshowViewModel.class);
        View root = inflater.inflate(R.layout.fragment_slideshow, container, false);
        //final TextView textView = root.findViewById(R.id.text_slideshow);
        slideshowViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
               // textView.setText(s);
            }
        });

        loansubmit = (Button) root.findViewById(R.id.loan_buttoncontinue);
        maritalStatus = (Spinner) root.findViewById(R.id.loan_maritalstatus);
        dependents = (Spinner) root.findViewById(R.id.loan_dependents);
        education = (Spinner) root.findViewById(R.id.loan_education);
        selfEmployed = (Spinner) root.findViewById(R.id.loan_selfemployed);
        loanTerm = (Spinner) root.findViewById(R.id.loan_term);
        creditHistory = (Spinner) root.findViewById(R.id.loan_credithistory);
        income = (EditText) root.findViewById(R.id.loan_income);
        coIncome =(EditText) root.findViewById(R.id.loan_incomeofcoapplicant);
        loanAmount = (EditText) root.findViewById(R.id.loan_amount);

        super.onCreate(savedInstanceState);

        loansubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FirebaseCustomRemoteModel remote = mlKit.configureHostedModelSource();
                if(remote != null){
                    float a = mlKit.startModelDownloadTask(remote);
                    if(a > 0.5)
                        Toast.makeText(getContext(),"Model downloaded successfully !"+a,Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(getContext(),"Cant download !"+a,Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(getContext(),"Remote model is NULL !!",Toast.LENGTH_SHORT).show();

                //float probability = input

            }

                //mlKit.loanQuote();

                /*
                ----------------------------------------

                FirebaseCustomRemoteModel remoteModel =


                        new FirebaseCustomRemoteModel.Builder("test").build();
                FirebaseModelDownloadConditions conditions = new FirebaseModelDownloadConditions.Builder()
                        .requireWifi()
                        .build();
                FirebaseModelManager.getInstance().getLatestModelFile(remoteModel)
                        .addOnCompleteListener(new OnCompleteListener<File>() {
                            @Override
                            public void onComplete(@NonNull Task<File> task) {
                                File prediction = task.getResult();
                                if(prediction != null) {
                                    System.out.print(prediction);
                                    Toast.makeText(getContext(), "Success download complete :"+prediction, Toast.LENGTH_SHORT).show();
                                }
                                else
                                    Toast.makeText(getContext(),"Cant download :"+prediction,Toast.LENGTH_SHORT).show();
                            }

                        });
                    }
                    ----------------------
                 */
                            /*try
                            {
                            InputStream inputStream = getContext().getAssets().open("model.tflite");
                            byte[] model = new byte[inputStream.available()];
                            inputStream.read(model);
                            ByteBuffer buffer = ByteBuffer.allocateDirect(model.length)
                                    .order(ByteOrder.nativeOrder());
                            buffer.put(model);
                            interpreter = new Interpreter(buffer);
                        } catch (IOException e) {
                                System.out.println(e);
                                Toast.makeText(getContext(),"error block",Toast.LENGTH_SHORT).show();
                        }
                        }
                    }*/
                /*checkCredentials();
                FirebaseCustomRemoteModel firebaseCustomRemoteModel;
                FirebaseModelManager firebaseModelManager = null;
                firebaseCustomRemoteModel = new FirebaseCustomRemoteModel.Builder("model").build();
                FirebaseModelManager.getInstance().getLatestModelFile(firebaseCustomRemoteModel).addOnCompleteListener(new OnCompleteListener<File>() {
                    @Override
                    public void onComplete(@NonNull Task<File> task) {
                        File prediction = task.getResult();
                        if(prediction != null)
                            System.out.print("Model downloaded successfully");
                        else
                            System.out.print("Bhenchod");
                    }

                });*/

                /*Intent intent = new Intent(view.getContext(), ActivityLoanThankYou.class);
                view.getContext().startActivity(intent);
                getActivity().finish();*/

                });

        return root;
    }


    private void checkCredentials() {
        inc = income.getText().toString().trim();
        coinc = coIncome.getText().toString().trim();
        loanamt = loanAmount.getText().toString().trim();
        marstatus = maritalStatus.getSelectedItem().toString().trim();
        dep = dependents.getSelectedItem().toString().trim();
        edu = education.getSelectedItem().toString().trim();
        selfemp = selfEmployed.getSelectedItem().toString().trim();
        loant = loanTerm.getSelectedItem().toString().trim();
        credith = creditHistory.getSelectedItem().toString().trim();

        if (inc.isEmpty()) {
            income.setError("Please enter 'Applicant income' !");
            income.requestFocus();
            return;
        }

        if (coinc.isEmpty()) {
            coinc = "0";
            return;
        }

        if (loanamt.isEmpty()) {
            loanAmount.setError("Please enter Living Area");
            loanAmount.requestFocus();
            return;
        }

        if (marstatus.isEmpty()) {
            Toast.makeText(getContext(), "Please select an option for Marital Status", Toast.LENGTH_SHORT).show();
        } else {
            if (marstatus.compareTo("Yes") == 0) {
                marstatus = "1";
            } else
                marstatus = "0";

        }

        if (dep.isEmpty()) {
            Toast.makeText(getContext(), "Please select an option for Dependents", Toast.LENGTH_SHORT).show();
        } else {
            if (dep.compareTo("Yes") == 0)
                dep = "1";
            else
                dep = "0";
        }

        if (edu.isEmpty()) {
            Toast.makeText(getContext(), "Please select an option for Education", Toast.LENGTH_SHORT).show();
        } else {
            if (edu.compareTo("Graduate") == 0)
                edu = "1";
            else if (edu.compareTo("Not Graduate") == 0)
                edu = "0";
        }

        if (selfemp.isEmpty()) {
            Toast.makeText(getContext(), "Please select an option for Self-Employed", Toast.LENGTH_SHORT).show();
        } else {
            if (edu.compareTo("Yes") == 0)
                selfemp = "1";
            else
                selfemp = "0";
        }

        if (loant.isEmpty()) {
            Toast.makeText(getContext(), "Please select an option for Loan-Term", Toast.LENGTH_SHORT).show();
        } else {
            if (loant.compareTo("1 year") == 0)
                loant = "12";
            else if (loant.compareTo("3 years") == 0)
                loant = "36";
            else if (loant.compareTo("5 years") == 0)
                loant = "60";
            else if (loant.compareTo("7 years") == 0)
                loant = "84";
            else if (loant.compareTo("10 years") == 0)
                loant = "120";
            else if (loant.compareTo("15 years") == 0)
                loant = "180";
            else if (loant.compareTo("20 years") == 0)
                loant = "240";
            else if (loant.compareTo("25 years") == 0)
                loant = "300";
            else if (loant.compareTo("30 years") == 0)
                loant = "360";
            else if (loant.compareTo("40 years") == 0)
                loant = "480";
        }

        if (credith.isEmpty()) {
            Toast.makeText(getContext(), "Please select an option for Credit History", Toast.LENGTH_SHORT).show();
        } else {
            if (credith.compareTo("Yes") == 0)
                credith = "1";
            else if (loant.compareTo("No") == 0)
                credith = "0";
        }
    }
}