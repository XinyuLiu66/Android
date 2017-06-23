package com.example.apple.liu_androidauto_parkingsaverapp;

import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import com.example.apple.liu_androidauto_parkingsaverapp.Auth_Basic.Auth_Basic;
import com.example.apple.liu_androidauto_parkingsaverapp.model.Reservationsstatus;
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
 * This class denotes REST-API
 */

public class RestAPI {
    public static List<Server_ParkingSaver> listAllParkingSavers = new ArrayList<>();
    //public static Map<String,Integer> MapUserReservedParkingSavers = new HashMap<>();
    public static List<Reservationsstatus> ListUserReservedParkingSavers = new ArrayList<Reservationsstatus>();

    public static Map<Integer,double[]> mapCoordinationParkingSavers = new HashMap<>();
    //-------------------------------------------------------------------------------------
    //TODO You can set this base url as your wish!(Your server's url)
    private static String BASE_URL = "http://10.0.2.2:8080/Parking_saver/";
    //TODO You can change the authentication information as you want!
    // Here i set the username "liu" and "xinyu" as default!
    Auth_Basic auth_basic = new Auth_Basic("liu","xinyu");
    //-------------------------------------------------------------------------------------

    /**
     * Constructor
     */
    public RestAPI(){

    }

    //=======================ParkingSaver: PUT=====================================================
    /**
     * Update the status of a Parking Saver in web-server. "Blocked" -> "Unblocked"      "Unblocked" -> "Blocked"
     *
     * @param parkingSaverID; the ID of reserved parking saver
     * @param status: the required status
     */
    public void updateParkingSaverState(int parkingSaverID,  String status) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api_parkingSaver api = retrofit.create(Api_parkingSaver.class);
        //=====================TODO Auth basic=====
/*        String userName = "liu";
        String password = "xinyu";
        String base = userName + ":" + password;*/
        String base = auth_basic.getUsername() + ":" + auth_basic.getPassword();
        String authHeader = "Basic " + Base64.encodeToString(base.getBytes(),Base64.NO_WRAP);
        //============================================

        Server_ParkingSaver psWhichTochangeStatus = new Server_ParkingSaver();
        for(int i=0;i<listAllParkingSavers.size();i++) {
            Server_ParkingSaver ps = listAllParkingSavers.get(i);
            if(ps.getId() == parkingSaverID) {
                psWhichTochangeStatus = ps;
                break;
            }
        }
        psWhichTochangeStatus.setStatus(status);
        Call<Server_ParkingSaver> putIndividualParkingSaverCall = api.updateParkingSaverStatus(authHeader,
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
                //  Toast.makeText(getApplicationContext(), "Error while fetching parkingSaver", Toast.LENGTH_SHORT).show();
                System.out.println("*********Error:In RestAPI class -> updateParkingSaverState onFailure!**********");
            }

        });
    }

    //============================ParkingSaver: GET=======================================================
    /**
     * Get information of all parking savers, but in order to get rid of asynchronous problem, call update method in this method.
     * And the information of all parking savers would be saved in global list "listAllParkingSavers"".
     */
    public void getAllParkingSaver() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Api_parkingSaver api = retrofit.create(Api_parkingSaver.class);
        //=====================TODO Auth basic=====
/*        String userName = "liu";
        String password = "xinyu";
        String base = userName + ":" + password;*/
        String base = auth_basic.getUsername() + ":" + auth_basic.getPassword();
        String authHeader = "Basic " + Base64.encodeToString(base.getBytes(),Base64.NO_WRAP);
        //============================================
        Call<List<Server_ParkingSaver>> getAllParkingSaverCall = api.getAllParkingSavers(authHeader);

        getAllParkingSaverCall.enqueue(new Callback<List<Server_ParkingSaver>>() {
            @Override
            public void onResponse(Response<List<Server_ParkingSaver>> response, Retrofit retrofit) {
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
                System.out.println("*********Error:In RestAPI class -> getAllParkingSaver onFailure!**********");
            }
        });
    }


    //========================================== User: GET==========================================================================
    /**
     * Get reserved parking saver of this default user. And save the information in global map "MapUserReservedParkingSavers"
     */
    //TODO We use one default username to test out REST-API, maybe you need to set this username for different users later.
    public void getUserReservedParkingSaver(/*String username*/) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Api_user userApi = retrofit.create(Api_user.class);
        //=====================TODO Auth basic=====
/*        String userName = "liu";
        String password = "xinyu";
        String base = userName + ":" + password;*/
        String base = auth_basic.getUsername() + ":" + auth_basic.getPassword();

        String authHeader = "Basic " + Base64.encodeToString(base.getBytes(),Base64.NO_WRAP);
        //============================================
        //TODO You can change this userName! Pick a userName from your server!
        String userEmail = "huyue@gmail.com";
       // Call<Map<String,Integer>> getUserReservedParkingSaverCall = userApi.getUser(authHeader,userEmail);  List<Reservationsstatus>
        Call<List<Reservationsstatus>> getUserReservedParkingSaverCall = userApi.getUser(authHeader,userEmail);
        getUserReservedParkingSaverCall.enqueue(new Callback<List<Reservationsstatus>>() {

            @Override
            public void onResponse(Response<List<Reservationsstatus>> response, Retrofit retrofit) {
                Log.d("onResponse", "" + response.code() +
                        "  response body " + response.body() +
                        " responseError " + response.errorBody() + " responseMessage " +
                        response.message());

            //    Map<String,Integer> map_UserReservedParkingSavers = response.body();
                List<Reservationsstatus> listUserReservedParkingSavers = response.body();
                System.out.println("!!!!!!how many parking savers are been reseved by this user? " +listUserReservedParkingSavers.size());
               // MapUserReservedParkingSavers = map_UserReservedParkingSavers;
                ListUserReservedParkingSavers= listUserReservedParkingSavers;
            }

            @Override
            public void onFailure(Throwable t) {
                //  Toast.makeText(getApplicationContext(), "Error while fetching parkingSaver", Toast.LENGTH_SHORT).show();
                System.out.println("*********Error:In RestAPI class -> getUserReservedParkingSaver onFailure!**********");

            }
        });
    }

}
