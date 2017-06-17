/*
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.apple.liu_androidauto_parkingsaverapp;

import android.Manifest;
import android.app.Fragment;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.icu.text.SimpleDateFormat;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.apple.liu_androidauto_parkingsaverapp.model.Server_ParkingSaver;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The main fragment that shows the buttons and the text view containing the log.
 */

public class MessagingFragment extends Fragment implements View.OnClickListener,GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {

    private static final String TAG = MessagingFragment.class.getSimpleName();

    private Button bt_block;
    private Button bt_unblock;

    private TextView mDataPortView;
    private Button mClearLogButton;

    private Messenger mService;
    private boolean mBound;
    private  String detail = "*************************";
  //  private boolean ifBlock ;

    // important, important, important
    private int flag_indicate_block;   //0 indicate want to block, 1 indicate want to unblock, 2 indicate get nearby of the reserved parking space
    private boolean flag_IfHaveSendMessageWhenNearby = false;

    public boolean isFlag_IfHaveSendMessageWhenNearby() {
        return flag_IfHaveSendMessageWhenNearby;
    }

    public void setFlag_IfHaveSendMessageWhenNearby(boolean flag_IfHaveSendMessageWhenNearby) {
        this.flag_IfHaveSendMessageWhenNearby = flag_IfHaveSendMessageWhenNearby;
    }

    // Location
    private final String LOG_TAG = "=====HUYUETestApp";
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;

   // ===============REST API================
    RestAPI restAPI = new RestAPI();
    private static int ps_ID;

    public static int getPs_ID() {
        return ps_ID;
    }

    public static void setPs_ID(int ps_ID) {
        MessagingFragment.ps_ID = ps_ID;
    }
    /*    public int getFlag_indicate_block() {
        return flag_indicate_block;
    }

    public void setFlag_indicate_block(int flag_indicate_block) {
        this.flag_indicate_block = flag_indicate_block;
    }*/

    /*
      Constructor
     */
    public MessagingFragment() {
    }

    private final ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder service) {
            mService = new Messenger(service);
            mBound = true;
            setButtonsState(true);
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            mService = null;
            mBound = false;
            setButtonsState(false);
        }
    };

    private final SharedPreferences.OnSharedPreferenceChangeListener listener =
            new SharedPreferences.OnSharedPreferenceChangeListener() {
        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
            if (MessageLogger.LOG_KEY.equals(key)) {
                mDataPortView.setText(MessageLogger.getAllMessages(getActivity()));
            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_message_me, container, false);

        bt_block = (Button) rootView.findViewById(R.id.bt_block);
        bt_block.setOnClickListener(this);

        bt_unblock = (Button) rootView.findViewById(R.id.bt_unblock);
        bt_unblock.setOnClickListener(this);


        mDataPortView = (TextView) rootView.findViewById(R.id.data_port);
        mDataPortView.setMovementMethod(new ScrollingMovementMethod());

        mClearLogButton = (Button) rootView.findViewById(R.id.clear);
        mClearLogButton.setOnClickListener(this);

        setButtonsState(false);

        return rootView;
    }

    @Override
    public void onClick(View view) {
        if (view == bt_block) {
          //  setIfBlock(true);
          //  System.out.println("==== at the moment block status is : " + this.isIfBlock());
            sendMsg(1, 0);
        } else if (view == bt_unblock) {
         //   setIfBlock(false);
          //  System.out.println("==== at the moment block status is : " + this.isIfBlock());
            sendMsg(1, 1);
        }else if (view == mClearLogButton) {
            MessageLogger.clear(getActivity());
            mDataPortView.setText(MessageLogger.getAllMessages(getActivity()));
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        //===================REST Api==================

        restAPI.getAllParkingSaver();
        restAPI.getUserReservedParkingSaver();

     //   restAPI.getAllParkingSaver();
        //======================For Message===================================
        getActivity().bindService(new Intent(getActivity(), MessagingService.class), mConnection,
                Context.BIND_AUTO_CREATE);

        // ======================For Location=================================
        mGoogleApiClient = new GoogleApiClient.Builder(this.getActivity())
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
        mGoogleApiClient.connect();
    }

    @Override
    public void onPause() {
        super.onPause();
        MessageLogger.getPrefs(getActivity()).unregisterOnSharedPreferenceChangeListener(listener);
    }

    @Override
    public void onResume() {
        super.onResume();
        mDataPortView.setText(MessageLogger.getAllMessages(getActivity()));
        MessageLogger.getPrefs(getActivity()).registerOnSharedPreferenceChangeListener(listener);
    }

    @Override
    public void onStop() {
        super.onStop();
        //======================For Message===================================
        if (mBound) {
            getActivity().unbindService(mConnection);
            mBound = false;
        }
        // ======================For Location=================================
        mGoogleApiClient.disconnect();
    }


private void sendMsg(int howManyConversations, int flag_indicate_block) {
    if (mBound) {
        Message msg = Message.obtain(null, MessagingService.MSG_SEND_NOTIFICATION,
                howManyConversations, flag_indicate_block );
        try {
            mService.send(msg);
        } catch (RemoteException e) {
            Log.e(TAG, "Error sending a message", e);
            MessageLogger.logMessage(getActivity(), "Error occurred while sending a message.");
        }
    }
}

    private void setButtonsState(boolean enable) {
        bt_block.setEnabled(enable);
        bt_unblock.setEnabled(enable);

    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onLocationChanged(Location location) {
        //============================test for if mapCoordinationParkingSavers have actual values when I want to use it ===========
//        for(int id : RestAPI.mapCoordinationParkingSavers.keySet()) {
//            detail += id;
//            detail += ":";
//            detail += RestAPI.mapCoordinationParkingSavers.get(id)[0];
//            detail += ",";
//            detail += RestAPI.mapCoordinationParkingSavers.get(id)[1];
//            detail += "   ";
//        }
//        System.out.println("!!!!!!!!!!len of mapCoordinationParkingSavers :"+ RestAPI.mapCoordinationParkingSavers.size());
//        System.out.println("!!!!!!!!!!detail:  " +detail);
        //System.out.println("Latitude: "+Double.toString(location.getLatitude())+" "+"Longitude: "+ Double.toString(location.getLongitude()));
        Log.i(LOG_TAG,location.toString());
        double userLatitude = location.getLatitude();
        double userLongitude = location.getLongitude();

        //  Take care ! which one is psLatitude and which one is psLongitude!!!
        if(isInReservationTime()){
            System.out.println("===========????????????   :" + getPs_ID());
            double psLatitude = RestAPI.mapCoordinationParkingSavers.get(getPs_ID())[0];
            double psLongitude = RestAPI.mapCoordinationParkingSavers.get(getPs_ID())[1];

            if (isFlag_IfHaveSendMessageWhenNearby() == false && distance(userLatitude, userLongitude, psLatitude, psLongitude, "K") < 0.3) {
                System.out.println("===========????????????");
                sendMsg(1, 2);
                setFlag_IfHaveSendMessageWhenNearby(true);
            }
        }
    }

    //===================Compare Time======================
     @RequiresApi(api = Build.VERSION_CODES.N)
     public boolean isInReservationTime(){
       //  Map<String,Integer> reserInfos = RestAPI.MapUserReservedParkingSavers;

         int psID=0;
         Set<String> allTimes = RestAPI.MapUserReservedParkingSavers.keySet();
         SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy,HH:mm");
         Date dbDateBegin = null;
         Date dbDateEnd = null;

         for(String time: allTimes){
             System.out.println("=======time section  :" + time);
             String[] result = time.split("-");  //   12/06/2017,09:00-10:00
             String newFormatTimeBegin = result[0]; //  12/06/2017,09:00
             System.out.println("=======time BEGIN  :" + newFormatTimeBegin );
             // get end-time
             String[] endTime = newFormatTimeBegin.split(","); // 12/06/2017,09:00
             String tempEnd = endTime[0]; //  12/06/2017
             String newFormatTimeEnd = tempEnd +","+ result[1]; //   12/06/2017,10:00
             System.out.println("=======time END  :" + newFormatTimeEnd);
             try {
                 dbDateBegin = sdf.parse(newFormatTimeBegin);
                 dbDateEnd = sdf.parse(newFormatTimeEnd);
             } catch (ParseException e) {
                 e.printStackTrace();
             }

             System.out.println("======dbDateEnd :" + dbDateBegin.toString());
             int compareToNowBegin = new Date().compareTo(dbDateBegin);
    //         System.out.println("current time============ " + sdf.format(new Date()));
    //         System.out.println("compareToNow============ " + compareToNowBegin);

             System.out.println("======dbDateEnd :" + dbDateEnd.toString());
             int compareToNowEnd = new Date().compareTo(dbDateEnd);
             System.out.println("current time============ " + sdf.format(new Date()));
             System.out.println("compareToNow============ " + compareToNowEnd);
             // if current time > reser BeginTime  && current time < reser EndTime
             if(compareToNowBegin == 1 && compareToNowEnd == -1){
                 //  user can block or unblock
                 this.setPs_ID(RestAPI.MapUserReservedParkingSavers.get(time));
                 return true;
             }
             else {
                 // TODO: for test  exception handeln
                 this.setPs_ID(10);
                 return true;
             }
         }

         return false;
     }
    //==================================For Location=======================================



    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Log.i(LOG_TAG,"GoogleApiClient connetion has been successful!");
        mLocationRequest = LocationRequest.create();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(1000); // 1s
        // ==========request the permission=============================
        boolean permissionGranted = ActivityCompat.checkSelfPermission(this.getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
        if(permissionGranted) {
            System.out.println("permissionGranted=== ture==========");
        } else {
            System.out.println("permissionGranted=== false==========");
            ActivityCompat.requestPermissions(this.getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 200);
        }
        //===============================================================
        if (ActivityCompat.checkSelfPermission(this.getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this.getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            //
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
//        Location mLastLocation = LocationServices.FusedLocationApi
//                .getLastLocation(mGoogleApiClient);
        //  System.out.println("mLastLocation.getLatitude "+ mLastLocation.getLatitude() );android.location.LocationListener
        //   System.out.println("mLastLocation.getLongitude "+ mLastLocation.getLongitude() );
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.i(LOG_TAG,"GoogleApiClient connetion has been suspended");
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.i(LOG_TAG,"GoogleApiClient connetion has failed");
    }

    //Start : =================calculate the distance of two location according to latitude and longitude===============
         /*   This routine calculates the distance between two points (given the     :*/
        /*::  latitude/longitude of those points). It is being used to calculate     :*/
        /*::  the distance between two locations using GeoDataSource (TM) prodducts  :*/
        /*::                                                                         :*/
        /*::  Definitions:                                                           :*/
        /*::    South latitudes are negative, east longitudes are positive           :*/
        /*::                                                                         :*/
        /*::  Passed to function:                                                    :*/
        /*::    lat1, lon1 = Latitude and Longitude of point 1 (in decimal degrees)  :*/
        /*::    lat2, lon2 = Latitude and Longitude of point 2 (in decimal degrees)  :*/
        /*::    unit = the unit you desire for results                               :*/
        /*::           where: 'M' is statute miles (default)                         :*/
        /*::                  'K' is kilometers                                      :*/
        /*::                  'N' is nautical miles                                  :*/
    private static double distance(double lat1, double lon1, double lat2, double lon2, String unit) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        if (unit == "K") {
            dist = dist * 1.609344;
        } else if (unit == "N") {
            dist = dist * 0.8684;
        }

        return (dist);
    }

    /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
	/*::	This function converts decimal degrees to radians						 :*/
	/*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
    private static double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
	/*::	This function converts radians to decimal degrees						 :*/
	/*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
    private static double rad2deg(double rad) {
        return (rad * 180 / Math.PI);
    }
// end : =================calculate the distance of two location according to latitude and longitude===============
}
