package com.example.apple.parkingsaver.ui;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import android.widget.Toast;

import com.example.apple.parkingsaver.R;
import com.example.apple.parkingsaver.ServerParkingSaver;
import com.example.apple.parkingsaver.adapter_ParkingSaver.AdapterParkingSaver;
import com.example.apple.parkingsaver.model.Data_ParkingSaver;
import com.example.apple.parkingsaver.model.Item;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import retrofit.Call;
import retrofit.Retrofit;
import retrofit.GsonConverterFactory;
import webservice.Api;

import retrofit.Callback;
import retrofit.Response;
public class MainActivity extends AppCompatActivity implements AdapterParkingSaver.ItemClickCallback{
    public static final String TAG = "ASYNC_TASK";

    EditText editName;
 //   TextView textDetails;
    Button btnGetData, btnInsertData;
    private ProgressDialog pDialog;

    // for recycler view
    private RecyclerView recView;
    private AdapterParkingSaver adapter;
    Context c = this;

    AdapterParkingSaver.ItemClickCallback itemClickCallbackInMainActivity = this;
    public static List<ServerParkingSaver> list_totalParkingSavers = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // ====set Recyclew view===

        recView = (RecyclerView)findViewById(R.id.rec_list);
//        //Check out GridLayoutManager and StaggeredGridLayoutManager for more options
        recView.setLayoutManager(new LinearLayoutManager(this));

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
            //    putIndividualParkingSaver();

            }
        });

        btnInsertData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
       //         postIndividualParkingSaver();
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
                list_totalParkingSavers = list_parkingSavers;
                String detail = "";
                for(int i = 0; i < list_parkingSavers.size(); i++) {
                    ServerParkingSaver parkingSaver  = list_parkingSavers.get(i);

                    String user = parkingSaver.getUser();
                    String coordination = parkingSaver.getCoordination();
                    int    parkingSaverID = parkingSaver.getParkingSaverID();
                    Map<String,String> map_reservation_status = parkingSaver.getReservation_status();
                    Set set=map_reservation_status.entrySet();
                    String reservation_status = ""+set;

                    detail += "ID: "+ parkingSaverID + "\n" + "Coordination: " + coordination + "\n"
                           + "User: " + user + "\n"  + reservation_status +"\n\n";
           //         textDetails.setText(detail);
                    System.out.println(detail);
                    hidepDialog();

                }
                hidepDialog();
                adapter = new AdapterParkingSaver(Data_ParkingSaver.getListData(),c);
                recView.setAdapter(adapter);

                // set ItemCallback
                adapter.setItemClickCallback(itemClickCallbackInMainActivity );


            }

            @Override
            public void onFailure(Throwable t) {
                Toast.makeText(getApplicationContext(), "Error while fetching parkingSaver", Toast.LENGTH_SHORT).show();

            }
        });
    }

 /*   void postIndividualParkingSaver() {
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
              //  textDetails.setText(detail);

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
    }*/
    private void showpDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }
    private void hidepDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }


//    @Override
//    public void onItemClick(int p) {
//        Item item = (Item) Data_ParkingSaver.getListData().get(p);
//        if (Boolean.valueOf(item.getTimeSectionFrom0().isIfReservation())){
//            item.getTimeSectionFrom0().setIfReservation("false");
//            Toast.makeText(getApplicationContext(),"Reservarion ended",Toast.LENGTH_SHORT).show();
//        } else {
//            item.setReservation(true);
//            Toast.makeText(getApplicationContext(),"Reservarion success",Toast.LENGTH_SHORT).show();
//
//
//
//        }
//
//    }

    @Override
    public void onItemClick1(int p) {

        Item item = (Item) Data_ParkingSaver.getListData().get(p);
        String str_psID = item.getPs_ID();
        int psID = Integer.parseInt(String.valueOf(str_psID.charAt(str_psID.length()-1)));
        System.out.println("ID of this parking saver's time section = " + item.getTimeSectionFrom0().getTimeSection());
        System.out.println("=======status now======="+ item.getTimeSectionFrom0().isIfReservation() );
        if (Boolean.valueOf(item.getTimeSectionFrom0().isIfReservation())){
            item.getTimeSectionFrom0().setIfReservation("false");
            putIndividualParkingSaver(psID, 0,"false");
      //      adapter = new AdapterParkingSaver(Data_ParkingSaver.getListData(),c);
       //     recView.setAdapter(adapter);

        }  else {
            item.getTimeSectionFrom0().setIfReservation("true");
            putIndividualParkingSaver(psID, 0,"true");

        }

    }

    @Override
    public void onItemClick2(int p) {

        Item item = (Item) Data_ParkingSaver.getListData().get(p);
        String str_psID = item.getPs_ID();
        int psID = Integer.parseInt(String.valueOf(str_psID.charAt(str_psID.length()-1)));
        if (Boolean.valueOf(item.getTimeSectionFrom6().isIfReservation())){
            item.getTimeSectionFrom6().setIfReservation("false");
            putIndividualParkingSaver(psID, 1,"false");

        }  else {
            item.getTimeSectionFrom6().setIfReservation("true");
            putIndividualParkingSaver(psID, 1,"true");

        }
    }

    @Override
    public void onItemClick3(int p) {
        Item item = (Item) Data_ParkingSaver.getListData().get(p);
        String str_psID = item.getPs_ID();
        int psID = Integer.parseInt(String.valueOf(str_psID.charAt(str_psID.length()-1)));
        if (Boolean.valueOf(item.getTimeSectionFrom8().isIfReservation())){
            item.getTimeSectionFrom8().setIfReservation("false");
            putIndividualParkingSaver(psID, 2,"false");


        }  else {
            item.getTimeSectionFrom8().setIfReservation("true");
            putIndividualParkingSaver(psID, 2,"true");


        }

    }

    @Override
    public void onItemClick4(int p) {
        Item item = (Item) Data_ParkingSaver.getListData().get(p);
        String str_psID = item.getPs_ID();
        int psID = Integer.parseInt(String.valueOf(str_psID.charAt(str_psID.length()-1)));
        if (Boolean.valueOf(item.getTimeSectionFrom10().isIfReservation())){
            item.getTimeSectionFrom10().setIfReservation("false");
            putIndividualParkingSaver(psID, 3,"false");


        }  else {
            item.getTimeSectionFrom10().setIfReservation("true");
            putIndividualParkingSaver(psID, 3,"true");


        }

    }

    @Override
    public void onItemClick5(int p) {
        Item item = (Item) Data_ParkingSaver.getListData().get(p);
        String str_psID = item.getPs_ID();
        int psID = Integer.parseInt(String.valueOf(str_psID.charAt(str_psID.length()-1)));
        if (Boolean.valueOf(item.getTimeSectionFrom12().isIfReservation())){
            item.getTimeSectionFrom12().setIfReservation("false");
            putIndividualParkingSaver(psID, 4,"false");


        }  else {
            item.getTimeSectionFrom12().setIfReservation("true");
            putIndividualParkingSaver(psID, 4,"true");


        }

    }

    @Override
    public void onItemClick6(int p) {
        Item item = (Item) Data_ParkingSaver.getListData().get(p);
        String str_psID = item.getPs_ID();
        int psID = Integer.parseInt(String.valueOf(str_psID.charAt(str_psID.length()-1)));
        if (Boolean.valueOf(item.getTimeSectionFrom14().isIfReservation())){
            item.getTimeSectionFrom14().setIfReservation("false");
            putIndividualParkingSaver(psID, 5,"false");


        }  else {
            item.getTimeSectionFrom14().setIfReservation("true");
            putIndividualParkingSaver(psID, 5,"true");


        }

    }

    @Override
    public void onItemClick7(int p) {
        Item item = (Item) Data_ParkingSaver.getListData().get(p);
        String str_psID = item.getPs_ID();
        int psID = Integer.parseInt(String.valueOf(str_psID.charAt(str_psID.length()-1)));
        if (Boolean.valueOf(item.getTimeSectionFrom16().isIfReservation())){
            item.getTimeSectionFrom16().setIfReservation("false");
            putIndividualParkingSaver(psID, 6,"false");


        }  else {
            item.getTimeSectionFrom16().setIfReservation("true");
            putIndividualParkingSaver(psID, 6,"true");


        }

    }

    @Override
    public void onItemClick8(int p) {
        Item item = (Item) Data_ParkingSaver.getListData().get(p);
        String str_psID = item.getPs_ID();
        int psID = Integer.parseInt(String.valueOf(str_psID.charAt(str_psID.length()-1)));
        if (Boolean.valueOf(item.getTimeSectionFrom18().isIfReservation())){
            item.getTimeSectionFrom18().setIfReservation("false");
            putIndividualParkingSaver(psID, 7,"false");


        }  else {
            item.getTimeSectionFrom18().setIfReservation("true");
            putIndividualParkingSaver(psID, 7,"true");


        }

    }

    @Override
    public void onItemClick9(int p) {
        Item item = (Item) Data_ParkingSaver.getListData().get(p);
        String str_psID = item.getPs_ID();
        int psID = Integer.parseInt(String.valueOf(str_psID.charAt(str_psID.length()-1)));
        if (Boolean.valueOf(item.getTimeSectionFrom21().isIfReservation())){
            item.getTimeSectionFrom21().setIfReservation("false");
            putIndividualParkingSaver(psID, 8,"false");


        }  else {
            item.getTimeSectionFrom21().setIfReservation("true");
            putIndividualParkingSaver(psID, 8,"true");


        }

    }

     //TUDO :============Tips: after click, first: put parkingsaver information, then

    //=============http put method=================

    void putIndividualParkingSaver(int parkingSaverID, int timeSectionTOchangeID, final String status) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080/parkingSaver/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Api api = retrofit.create(Api.class);

        String[] startTimeSection = new String[]{
            "timeSectionFrom0","timeSectionFrom6", "timeSectionFrom8",
            "timeSectionFrom10","timeSectionFrom12","timeSectionFrom14",
            "timeSectionFrom16","timeSectionFrom18","timeSectionFrom21"};

        ServerParkingSaver psTochangeStatus = new ServerParkingSaver();
        for(int i=0;i<list_totalParkingSavers.size();i++) {
            ServerParkingSaver ps = list_totalParkingSavers.get(i);

            if(ps.getParkingSaverID() == parkingSaverID) {
                psTochangeStatus = ps;
            }
        }
        Map<String ,String> reservationStatusOfthisPS = psTochangeStatus.getReservation_status();
        reservationStatusOfthisPS.put(startTimeSection[timeSectionTOchangeID],status);
        Call<ServerParkingSaver> putIndividualParkingSaverCall = api.put(parkingSaverID,psTochangeStatus);


        //======= construct a new parkingSaver object  for post!!!!!!!!!!!!!!!!=========//
//        ServerParkingSaver parkingSaver = new ServerParkingSaver();
//        parkingSaver.setUser("Li");
//        parkingSaver.setCoordination("29-30");
//        parkingSaver.setParkingSaverID(5);
//
//        Map<String,String> reservation_status = new HashMap<>();
//        String[] startTimeSection = new String[]{
//                "timeSectionFrom0","timeSectionFrom6", "timeSectionFrom8",
//                "timeSectionFrom10","timeSectionFrom12","timeSectionFrom14",
//                "timeSectionFrom1","timeSectionFrom18","timeSectionFrom21"};
//
//        reservation_status.put(startTimeSection[0], "true");
//        reservation_status.put(startTimeSection[1], "false");
//        reservation_status.put(startTimeSection[2], "false");
//        reservation_status.put(startTimeSection[3], "false");
//        reservation_status.put(startTimeSection[4], "false");
//        reservation_status.put(startTimeSection[5], "false");
//        reservation_status.put(startTimeSection[6], "false");
//        reservation_status.put(startTimeSection[7], "false");
//        reservation_status.put(startTimeSection[8], "true");
//        parkingSaver.setReservation_status(reservation_status);
//        Call<ServerParkingSaver> putIndividualParkingSaverCall = api.put(5,parkingSaver);

        //======= construct a new parkingSaver object  for post!!!!!!!!!!!!!!!!=======//

        putIndividualParkingSaverCall.enqueue(new Callback<ServerParkingSaver>() {
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
                Map<String,String> map_reservation_status = parkingSaver.getReservation_status();
                Set set=map_reservation_status.entrySet();
                String reservation_status = ""+set;

                detail += "ID: "+ parkingSaverID + "\n" + "Coordination: " + coordination + "\n"
                        + "User: " + user + "\n"  + reservation_status +"\n\n";

               System.out.println(detail);
                if(status == "true") {
                    Toast.makeText(getApplicationContext(), "Reservarion success", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getApplicationContext(), "Reservarion ended", Toast.LENGTH_SHORT).show();
                }


        }

        @Override
        public void onFailure(Throwable t) {
            Toast.makeText(getApplicationContext(), "Error while fetching parkingSaver", Toast.LENGTH_SHORT).show();
        }

    });
    }
}




