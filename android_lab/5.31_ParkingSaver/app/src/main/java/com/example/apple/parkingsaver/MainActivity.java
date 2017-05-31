package com.example.apple.parkingsaver;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import retrofit.Call;
import retrofit.Retrofit;
import retrofit.GsonConverterFactory;
import webservice.Api;

import retrofit.Callback;
import retrofit.Response;
public class MainActivity extends AppCompatActivity {
    public static final String TAG = "ASYNC_TASK";

    EditText editName;
    TextView textDetails;
    Button btnGetData, btnInsertData;
    private ProgressDialog pDialog;

   // private  PostTask postTask;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textDetails = (TextView) findViewById(R.id.textDetails);
        btnGetData = (Button) findViewById(R.id.btnGetData);
        btnInsertData = (Button) findViewById(R.id.btnInsert);
        editName = (EditText) findViewById(R.id.editParkingSaver);

        //set progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);

        // set button click listener

        btnGetData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getAllParkingSaver();
            }
        });

        btnInsertData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                postIndividualParkingSaver();
            }
        });


    }


    void getAllParkingSaver() {
        showpDialog();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080/parkingSaver/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Api api = retrofit.create(Api.class);
        Call<List<ServerParkingSaver>> getAllParkingSaverCall = api.getAllParkingSavers();
        getAllParkingSaverCall.enqueue(new Callback<List<ServerParkingSaver>>() {

            @Override
            public void onResponse(Response<List<ServerParkingSaver>> response, Retrofit retrofit) {

                Log.d("onResponse", "" + response.code() +
                        "  response body " + response.body() +
                        " responseError " + response.errorBody() + " responseMessage " +
                        response.message());

                List<ServerParkingSaver> list_parkingSavers = response.body();
                String detail = "";
                for(int i = 0; i < list_parkingSavers.size(); i++) {
                    ServerParkingSaver parkingSaver  = list_parkingSavers.get(i);

                    String user = parkingSaver.getUser();
                    String coordination = parkingSaver.getCoordination();
                    int    parkingSaverID = parkingSaver.getParkingSaverID();
                    String startTime = parkingSaver.getStartTime();
                    String endTime = parkingSaver.getEndTime();
                    String ifReservation = parkingSaver.getReservation();

//                    details += "ID: " + id + "\n" +
//                            "Name: " + name + "\n\n";

                    detail += "ID: "+ parkingSaverID + "\n" + "Coordination: " + coordination + "\n"
                           + "User: " + user + "\n"  + "StartTime: " + startTime  + "\n" +
                            "EndTime: " + endTime + "\n" + "Reservation: " + ifReservation + "\n\n";
                    textDetails.setText(detail);
                    hidepDialog();

                }


            }

            @Override
            public void onFailure(Throwable t) {
                Toast.makeText(getApplicationContext(), "Error while fetching parkingSaver", Toast.LENGTH_SHORT).show();

            }
        });
    }

    void postIndividualParkingSaver() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080/parkingSaver/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Api api = retrofit.create(Api.class);

        // construct a new parkingSaver object
        ServerParkingSaver parkingSaver = new ServerParkingSaver();
        parkingSaver.setUser("Li");
        parkingSaver.setCoordination("29-30");
        parkingSaver.setReservation("false");
        parkingSaver.setPakingSaverID(12);
        parkingSaver.setStartTime("3:00");
        parkingSaver.setEndTime("5:00");

        Call<ServerParkingSaver> postIndividualParkingSaverCall = api.post(parkingSaver);

        postIndividualParkingSaverCall.enqueue(new Callback<ServerParkingSaver>() {
            @Override
            public void onResponse(Response<ServerParkingSaver> response, Retrofit retrofit) {
                Log.d("onResponse", "" + response.code() +
                        "  response body " + response.body() +
                        " responseError " + response.errorBody() + " responseMessage " +
                        response.message());

                ServerParkingSaver parkingSaver = response.body();
                String detail  = "";
                String user = parkingSaver.getUser();
                String coordination = parkingSaver.getCoordination();
                int    parkingSaverID = parkingSaver.getParkingSaverID();
                String startTime = parkingSaver.getStartTime();
                String endTime = parkingSaver.getEndTime();
                String ifReservation = parkingSaver.getReservation();


                detail += "ID: "+ parkingSaverID + "\n" + "Coordination: " + coordination + "\n"
                        + "User: " + user + "\n"  + "StartTime: " + startTime  + "\n" +
                        "EndTime: " + endTime + "\n" + "Reservation: " + ifReservation + "\n\n";
                textDetails.setText(detail);

//                user.setText("user: " + serverParkingSaver.getUser());
//                coordination.setText("coordination " + serverParkingSaver.getCoordination());
//                startTime.setText("startTime: " + serverParkingSaver.getStartTime());
//                endTime.setText("endTime " + serverParkingSaver.getEndTime());
//                ifReservation.setText("Reservation " + serverParkingSaver.getReservation());
//                parkingSaverID.setText("ID " + serverParkingSaver.getParkingSaverID());

            }

            @Override
            public void onFailure(Throwable t) {
                Toast.makeText(getApplicationContext(), "Error while fetching parkingSaver", Toast.LENGTH_SHORT).show();
            }

        });
    }
    private void showpDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }
    private void hidepDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }











}
