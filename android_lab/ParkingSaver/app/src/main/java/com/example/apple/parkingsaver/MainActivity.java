package com.example.apple.parkingsaver;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import webservice.Api;

import retrofit2.Callback;
import retrofit2.Response;
public class MainActivity extends AppCompatActivity {

    TextView user;
    TextView coordination;
    TextView ifReservation;
    TextView parkingSaverID;
    TextView startTime;
    TextView endTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        user =(TextView)findViewById(R.id.user);
        coordination =(TextView)findViewById(R.id.coordination);
        ifReservation = (TextView)findViewById(R.id.ifReservition);
        parkingSaverID = (TextView) findViewById(R.id.parkingSaverID);
        startTime = (TextView)findViewById(R.id.startTime);
        endTime = (TextView)findViewById(R.id.endTime);
        fetchTime();

    }


    void fetchTime() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080/parkingSaver/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Api api = retrofit.create(Api.class);
        Call<ServerParkingSaver> serverGetIndividualParkingSaverCall = api.getIndividualParkingSavers(1);
        serverGetIndividualParkingSaverCall.enqueue(new Callback<ServerParkingSaver>() {
            @Override
            public void onResponse(Call<ServerParkingSaver> call, Response<ServerParkingSaver> response) {
                ServerParkingSaver serverParkingSaver = response.body();
                user.setText("user: " + serverParkingSaver.getUser());
                coordination.setText("coordination " + serverParkingSaver.getCoordination());
                startTime.setText("startTime: " + serverParkingSaver.getStartTime());
                endTime.setText("endTime " + serverParkingSaver.getEndTime());
                ifReservation.setText("Reservation " + serverParkingSaver.isReservation());
                parkingSaverID.setText("ID " + serverParkingSaver.getParkingSaverID());
            }

            @Override
            public void onFailure(Call<ServerParkingSaver> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Error while fetching parkingSaver", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
