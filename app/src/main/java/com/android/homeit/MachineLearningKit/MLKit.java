package com.android.homeit.MachineLearningKit;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.ml.common.modeldownload.FirebaseModelDownloadConditions;
import com.google.firebase.ml.common.modeldownload.FirebaseModelManager;
import com.google.firebase.ml.custom.FirebaseCustomRemoteModel;

import org.tensorflow.lite.Interpreter;

import java.io.File;

public class MLKit {
    Boolean res = false;
    private float out;
    float[][] output;
    boolean status;


    public FirebaseCustomRemoteModel configureHostedModelSource() {
        FirebaseCustomRemoteModel remoteModel = new FirebaseCustomRemoteModel.Builder("Loan-Grant-Predictor-Test").build();
        return remoteModel;
    }

    public float startModelDownloadTask(FirebaseCustomRemoteModel remoteModel) {
        status = false;
        final float input[] = {1,1,1,1,1,1,1};
        final float output = 0;

        FirebaseModelDownloadConditions conditions = new FirebaseModelDownloadConditions.Builder()
                .requireWifi()
                .build();
        FirebaseModelManager.getInstance().getLatestModelFile(remoteModel)
                .addOnCompleteListener(new OnCompleteListener<File>() {
                    @Override
                    public void onComplete(@NonNull Task<File> task) {
                        Interpreter interpreter;
                        File modelImport = task.getResult();
                        if (modelImport != null) {
                            status = true;
                            interpreter = new Interpreter(modelImport);
                            interpreter.run(input, output);



                        /*else{
                            try {
                                InputStream inputStream = getAssets().open("loan.tflite");
                                byte[] model = new byte[inputStream.available()];
                                inputStream.read(model);
                                ByteBuffer buffer = ByteBuffer.allocateDirect(model.length)
                                        .order(ByteOrder.nativeOrder());
                                buffer.put(model);
                                Interpreter interpreter = new Interpreter(buffer);
                            } catch (IOException e) {
                                // File not found?
                            }
                        }*/
                        }
                    }
                });
        return output;
    }



    /*private FirebaseCustomLocalModel configureLocalModelSource() {
        FirebaseCustomLocalModel localModel = new FirebaseCustomLocalModel.Builder()
                .setAssetFilePath("loan.tflite")
                .build();
        return localModel;
    }

    private FirebaseModelInterpreter createInterpreter(FirebaseCustomLocalModel localModel) throws FirebaseMLException {
        FirebaseModelInterpreter interpreter = null;
        try {
            FirebaseModelInterpreterOptions options =
                    new FirebaseModelInterpreterOptions.Builder(localModel).build();
            interpreter = FirebaseModelInterpreter.getInstance(options);
        } catch (FirebaseMLException e) {
            // ...
        }
        return interpreter;
    }
    private void checkModelDownloadStatus(
            final FirebaseCustomRemoteModel remoteModel,
            final FirebaseCustomLocalModel localModel) {
        // [START mlkit_check_download_status]
        FirebaseModelInterpreter interpreter;
        FirebaseModelManager.getInstance().isModelDownloaded(remoteModel)
                .addOnSuccessListener(new OnSuccessListener<Boolean>() {
                    @Override
                    public void onSuccess(Boolean isDownloaded) {
                        FirebaseModelInterpreterOptions options;
                        if (isDownloaded) {
                            options = new FirebaseModelInterpreterOptions.Builder(remoteModel).build();
                            System.out.print("Remote model");
                        } else {
                            options = new FirebaseModelInterpreterOptions.Builder(localModel).build();
                            System.out.print("Local model");
                        }
                        try {
                            FirebaseModelInterpreter interpreter = FirebaseModelInterpreter.getInstance(options);
                            // ...
                        } catch (FirebaseMLException e) {

                        }
                    }
                });
        // [END mlkit_check_download_status]
    }

    private void addDownloadListener(
            FirebaseCustomRemoteModel remoteModel,
            FirebaseModelDownloadConditions conditions) {
        // [START mlkit_remote_model_download_listener]
        FirebaseModelManager.getInstance().download(remoteModel, conditions)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void v) {
                        // Download complete. Depending on your app, you could enable
                        // the ML feature, or switch from the local model to the remote
                        // model, etc.
                    }
                });
        // [END mlkit_remote_model_download_listener]
    }

    /*private FirebaseModelInputOutputOptions createInputOutputOptions() throws FirebaseMLException {
        // [START mlkit_create_io_options]
        FirebaseModelInputOutputOptions inputOutputOptions =
                new FirebaseModelInputOutputOptions.Builder()
                        .setInputFormat(0, FirebaseModelDataType.FLOAT32, new int[]{1, 8})
                        .setOutputFormat(0, FirebaseModelDataType.FLOAT32, new int[]{1, 1})
                        .build();
        // [END mlkit_create_io_options]

        return inputOutputOptions;
    }

    public float getOutStart() {
        return out;
    }

//    public void setOut(float[] out) {
//        this.out = out;
//    }


    public void runInference(final TextView T, float[][] input, final DatabaseReference DR) throws FirebaseMLException {
        FirebaseCustomLocalModel localModel = configureLocalModelSource();
        FirebaseModelInterpreter firebaseInterpreter = createInterpreter(localModel);
        //FirebaseModelInputOutputOptions inputOutputOptions = createInputOutputOptions();

        // [START mlkit_run_inference]
        FirebaseModelInputs inputs = new FirebaseModelInputs.Builder()
                .add(input)  // add() as many input arrays as your model requires
                .build();
        firebaseInterpreter.run(inputs, inputOutputOptions)
                .addOnSuccessListener(
                        new OnSuccessListener<FirebaseModelOutputs>() {
                            @Override
                            public void onSuccess(FirebaseModelOutputs result) {
                                // [START_EXCLUDE]
                                // [START mlkit_read_result]

                                output = result.getOutput(0);
                                DR.child("DiabetesOutPut").setValue(output[0][0]);
                                if(output[0][0] >= 0.5){
//                                    res = true;
                                    T.setText("You have a " + output[0][0]*100 + "% chance of having diabetes.");
                                }
                                else{
                                    T.setText("You do not have Diabetes.");
                                }

                            }
                        })
                .addOnFailureListener(
                        new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // Task failed with an exception
                                // ...
                            }
                        });
        // [END mlkit_run_inference]
    }*/

//    public Boolean Run(){
//        try {
//            runInference();
//        } catch (FirebaseMLException e) {
//            e.printStackTrace();
//        }
//        return res;
//
//    }
}
