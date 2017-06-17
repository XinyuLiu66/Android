package com.example.apple.liu_androidauto_parkingsaverapp;

import android.util.Log;

import com.example.apple.liu_androidauto_parkingsaverapp.model.Server_ParkingSaver;
import com.example.apple.liu_androidauto_parkingsaverapp.webService.Api_parkingSaver;
import com.example.apple.liu_androidauto_parkingsaverapp.webService.Api_user;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by dell on 15.06.2017.
 */

public class RestAPI {
    public static List<Server_ParkingSaver> listAllParkingSavers = new ArrayList<>();
    public static Map<String,Integer> MapUserReservedParkingSavers = new HashMap<>();
    public static Map<Integer,double[]> mapCoordinationParkingSavers = new HashMap<>();
   // public static List<double[]> record_coordination = new ArrayList<>();
  //  public static double[] record_coordination = new double[2];

    //Constructor
    public RestAPI(){}

    //===================================PUT=============================================
    void updateParkingSaverState(int parkingSaverID,  String status) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080/Parking_saver/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Api_parkingSaver api = retrofit.create(Api_parkingSaver.class);

        Server_ParkingSaver psWhichTochangeStatus = new Server_ParkingSaver();
        for(int i=0;i<listAllParkingSavers.size();i++) {
            Server_ParkingSaver ps = listAllParkingSavers.get(i);
            if(ps.getId() == parkingSaverID) {
                psWhichTochangeStatus = ps;
                break;
            }
        }
        psWhichTochangeStatus.setStatus(status);
        Call<Server_ParkingSaver> putIndividualParkingSaverCall = api.updateParkingSaverStatus(
                psWhichTochangeStatus.getId(),psWhichTochangeStatus);


        putIndividualParkingSaverCall.enqueue(new Callback<Server_ParkingSaver>() {
            @Override
            public void onResponse(Response<Server_ParkingSaver> response, Retrofit retrofit) {

                Log.d("onResponse", "" + response.code() +
                        "  response body " + response.body() +
                        " responseError " + response.errorBody() + " responseMessage " +
                        response.message());
            }

            @Override
            public void onFailure(Throwable t) {
                //    Toast.makeText(getApplicationContext(), "Error while fetching parkingSaver", Toast.LENGTH_SHORT).show();
            }

        });
    }

    //=================GET All the parking savers, but in order to get rid of asynchronous problem, call update method in this method ================
    void getAllParkingSaver() {
        //  showpDialog();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080/Parking_saver/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Api_parkingSaver api = retrofit.create(Api_parkingSaver.class);
        Call<List<Server_ParkingSaver>> getAllParkingSaverCall = api.getAllParkingSavers();

        System.out.println("=====Call get all has been implemented");
        getAllParkingSaverCall.enqueue(new Callback<List<Server_ParkingSaver>>() {

            @Override
            public void onResponse(Response<List<Server_ParkingSaver>> response, Retrofit retrofit) {
                System.out.println("!!!!!!Call get all has been implemented");

                Log.d("onResponse", "" + response.code() +
                        "  response body " + response.body() +
                        " responseError " + response.errorBody() + " responseMessage " +
                        response.message());

                List<Server_ParkingSaver> list_parkingSavers = response.body();
                System.out.println("!!!!!!how many parking savers are there? " +list_parkingSavers.size());
                listAllParkingSavers = list_parkingSavers;

                //================Store all parking saver coordination info in a Map--><ID,Coordination>
                for(Server_ParkingSaver ps : list_parkingSavers) {
                    int ID = ps.getId();
                    double[] coordination = ps.getCoordination();
                    mapCoordinationParkingSavers.put(ID,coordination);
                }

            }

            @Override
            public void onFailure(Throwable t) {
                //  Toast.makeText(getApplicationContext(), "Error while fetching parkingSaver", Toast.LENGTH_SHORT).show();
                System.out.println("======get all failure");

            }
        });
    }

    //=================GET reserved parking saver of this user================
    //TODO username
    void getUserReservedParkingSaver(/*String username*/) {
        //  showpDialog();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080/Parking_saver/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Api_user userApi = retrofit.create(Api_user.class);
        Call<Map<String,Integer>> getUserReservedParkingSaverCall = userApi.getUser("huyue@gmail.com");

        System.out.println("=====Call get all has been implemented");
        getUserReservedParkingSaverCall.enqueue(new Callback<Map<String,Integer>>() {

            @Override
            public void onResponse(Response<Map<String,Integer>> response, Retrofit retrofit) {
                System.out.println("!!!!!!Call get user resered parking saver has been implemented");

                Log.d("onResponse", "" + response.code() +
                        "  response body " + response.body() +
                        " responseError " + response.errorBody() + " responseMessage " +
                        response.message());

                Map<String,Integer> map_UserReservedParkingSavers = response.body();
                System.out.println("!!!!!!how many parking savers are been reseved by this user? " +map_UserReservedParkingSavers.size());
                MapUserReservedParkingSavers = map_UserReservedParkingSavers;

                //!!!!!!!!======in order to get rid of asychrounous=======
                //  updateParkingSaverState(1,status);
//                String detail ="";
//                for(int id : map_UserReservedParkingSavers.values()) {
//                    detail += id;
//                    detail +="    ";
//                }
//                for(int psID : map_UserReservedParkingSavers.values()) {
//                    //================get coordination==================================//
//                    getParkingSaverCoordinate(psID);
////                    mapCoordinationParkingSavers.put(psID,record_coordination);
////                    System.out.println("========== psID " + psID);
//                }

            }

            @Override
            public void onFailure(Throwable t) {
                //  Toast.makeText(getApplicationContext(), "Error while fetching parkingSaver", Toast.LENGTH_SHORT).show();
                System.out.println("======get all failure");

            }
        });
    }




//    //=================GET coordate of parking saver================
//    //
//    void getParkingSaverCoordinate(final int ps_ID) {
//        //  showpDialog();
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl("http://10.0.2.2:8080/Parking_saver/")
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//        Api_parkingSaver api_parkingSaver = retrofit.create(Api_parkingSaver.class);
//        Call<double[]> getCoordinationCall = api_parkingSaver.getCoordinationInfo(ps_ID);
//
//     //   System.out.println("=====Call get coordination has been implemented");
//        getCoordinationCall.enqueue(new Callback<double[]>() {
//
//            @Override
//            public void onResponse(Response<double[]> response, Retrofit retrofit) {
//                System.out.println("!!!!!!Call get coordination has been implemented");
//
//                Log.d("onResponse", "" + response.code() +
//                        "  response body " + response.body() +
//                        " responseError " + response.errorBody() + " responseMessage " +
//                        response.message());
//                double[] result ;
//                result = response.body();
//                mapCoordinationParkingSavers.put(ps_ID,result);
//
//                System.out.println("============= cocordinate =  " + result[0] + ", " + result[1]);
//
//            }
//
//            @Override
//            public void onFailure(Throwable t) {
//                //  Toast.makeText(getApplicationContext(), "Error while fetching parkingSaver", Toast.LENGTH_SHORT).show();
//                System.out.println("======get coordination failure");
//
//            }
//        });
//     //   return result[0];
//    }

}
