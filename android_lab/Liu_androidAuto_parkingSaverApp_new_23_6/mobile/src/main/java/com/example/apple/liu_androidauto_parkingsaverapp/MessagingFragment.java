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
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.apple.liu_androidauto_parkingsaverapp.model.Reservationsstatus;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The main fragment that shows the "block" and "unblock" buttons. The client can also control the parking saver through these two buttons.
 *
 * @author Xinyu Liu, Yue Hu
 */
public class MessagingFragment extends Fragment implements View.OnClickListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {

    private static final String TAG = MessagingFragment.class.getSimpleName();

    private Button bt_block;
    private Button bt_unblock;
    private TextView mDataPortView;
    private Button mClearLogButton;
    private Messenger mService;
    private boolean mBound;
    // important, important, important
    private boolean flag_IfHaveSendMessageWhenNearby = false;

    public boolean isFlag_IfHaveSendMessageWhenNearby() {
        return flag_IfHaveSendMessageWhenNearby;
    }

    public void setFlag_IfHaveSendMessageWhenNearby(boolean flag_IfHaveSendMessageWhenNearby) {
        this.flag_IfHaveSendMessageWhenNearby = flag_IfHaveSendMessageWhenNearby;
    }

    // ================Location=================
    private final String LOG_TAG = "HUYUETestApp";
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private static boolean isPS_Activated = false;

    public static boolean get_isPS_Activated() {
        return isPS_Activated;
    }

    // ===============REST API================
    RestAPI restAPI = new RestAPI();
    private static int ps_ID;

    public static int getPs_ID() {
        return ps_ID;
    }

    public static void setPs_ID(int ps_ID) {
        MessagingFragment.ps_ID = ps_ID;
    }

    /*
      Constructor
     */
    public MessagingFragment() {
    }

    /**
     * Class for interacting with the main interface of the service
     * More details; https://developer.android.com/guide/components/bound-services.html
     */
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

    /**
     * Ignore this one!!! Just for Test
     */
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
            // sendMsg(1, 0);
            // click button -> block
            if (isPS_Activated) {
                restAPI.updateParkingSaverState(MessagingFragment.getPs_ID(), "blocked");
            } else {
                Toast toast = Toast.makeText(this.getActivity(), "You cant cotroll the button now!", Toast.LENGTH_SHORT);
                toast.show();
            }

        } else if (view == bt_unblock) {
            //  sendMsg(1, 1);
            // click button -> unblock
            if (isPS_Activated) {
                restAPI.updateParkingSaverState(MessagingFragment.getPs_ID(), "unblocked");
            } else {
                Toast toast = Toast.makeText(this.getActivity(), "You cant cotroll the button now!", Toast.LENGTH_SHORT);
                toast.show();
            }

        } else if (view == mClearLogButton) {
            MessageLogger.clear(getActivity());
            mDataPortView.setText(MessageLogger.getAllMessages(getActivity()));
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        //===================REST Api========================================
        restAPI.getAllParkingSaver();
        restAPI.getUserReservedParkingSaver();
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

    /**
     * Send message as we want, all message are saved in "Conversations" class
     * @param howManyConversations the number of conversations that we want to send, we set this as 1
     * @param flag_indicate_block which message we should send, see "MESSAGES" in "Conversations" class
     */
    private void sendMsg(int howManyConversations, int flag_indicate_block) {
        if (mBound) {
            Message msg = Message.obtain(null, MessagingService.MSG_SEND_NOTIFICATION,
                    howManyConversations, flag_indicate_block);
            try {
                mService.send(msg);
            } catch (RemoteException e) {
                Log.e(TAG, "Error sending a message", e);
                MessageLogger.logMessage(getActivity(), "Error occurred while sending a message.");
            }
        }
    }

    /**
     * Set button state
     * @param enable the state of button
     */
    private void setButtonsState(boolean enable) {
        bt_block.setEnabled(enable);
        bt_unblock.setEnabled(enable);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onLocationChanged(Location location) {
        Log.i(LOG_TAG, location.toString());
        double userLatitude = location.getLatitude();
        double userLongitude = location.getLongitude();

        //  Take care ! which one is psLatitude and which one is psLongitude!!!
        if (isInReservationTime()) {
          //  System.out.println("===========????????????   :" + getPs_ID());
            double psLatitude = RestAPI.mapCoordinationParkingSavers.get(getPs_ID())[0];
            double psLongitude = RestAPI.mapCoordinationParkingSavers.get(getPs_ID())[1];

            if (isFlag_IfHaveSendMessageWhenNearby() == false && distance(userLatitude, userLongitude, psLatitude, psLongitude, "K") < 0.3) {
           //     System.out.println("===========????????????");
                sendMsg(1, 2); // send message:  "Do you want to block your parking saver?"
                setFlag_IfHaveSendMessageWhenNearby(true);
            }
            // check, whether user can block or unblock the PS
            if (distance(userLatitude, userLongitude, psLatitude, psLongitude, "K") < 0.3) {
                isPS_Activated = true;
            } else {
                isPS_Activated = false;
            }
        } else {
            isPS_Activated = false;
        }
    }

    //TODO
    public List<Pair> getUsers_TimePair(List<Reservationsstatus> rs){
        List<Pair> result = new ArrayList<Pair>();
        for(int i = 0;i< rs.size();i++){
            Pair tem = new Pair(rs.get(i).getBeginTime(),rs.get(i).getEndTime());
            result.add(tem);
        }
        return result;
    }
    //TODO
    public List<Integer> getReserved_psID(List<Reservationsstatus> rs){
        List<Integer> result = new ArrayList<Integer>();
        for(int i = 0;i< rs.size();i++){
            int tem = rs.get(i).getPs_ID();
            result.add(tem);
        }
        return result;
    }

    /**
     * Compare the reservation-time and current-time, check that whether one user can control the PS at this moment
     * @return whether current time is in user's reserved time
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    public boolean isInReservationTime() {
       // Set<String> allTimes = RestAPI.MapUserReservedParkingSavers.keySet();

        List<Reservationsstatus> allTimes2 = RestAPI.ListUserReservedParkingSavers;
        // Test
        System.out.println("==========size allTimes2  " + allTimes2.size());
        System.out.println("==========getBeginTime  " + allTimes2.get(0).getBeginTime());
        System.out.println("==========getEndTime  " + allTimes2.get(0).getEndTime());
        System.out.println("==========ReverID  " + allTimes2.get(0).getRever_ID());
        System.out.println("==========PSID  " + allTimes2.get(0).getPs_ID());
        //TODO
        List<Pair> allTimePair =  getUsers_TimePair(allTimes2);
        //Test
        System.out.println("==========size allTimePair  " + allTimePair.size());

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy,HH:mm");
        Date dbDateBegin = null;
        Date dbDateEnd = null;

        for(int i = 0; i <allTimePair.size();i++){
            Pair<String,String> pair = allTimePair.get(i);
            int id = getReserved_psID(allTimes2).get(i);
            try {
                String beginTime = pair.first.toString();
                String endTime = pair.second.toString();

                //Test
                System.out.println("========= Time begin String " + beginTime);
                System.out.println("========= Time end String " + endTime);

                dbDateBegin = sdf.parse(beginTime);
                dbDateEnd = sdf.parse(endTime);

            } catch (ParseException e) {
                e.printStackTrace();
            }

            System.out.println("========= Time begin Date " + dbDateBegin.toString());
            System.out.println("========= Time end Date " + dbDateEnd.toString());
            int compareToNowBegin = new Date().compareTo(dbDateBegin);
            int compareToNowEnd = new Date().compareTo(dbDateEnd);
            if (compareToNowBegin == 1 && compareToNowEnd == -1) {
                this.setPs_ID(id);
                return true;
            }

        }

        //===========================================================================================
/*        for (String time : allTimes) {
            System.out.println("=======time section  :" + time);
            String[] result = time.split("-");  //   12/06/2017,09:00-10:00
            String newFormatTimeBegin = result[0]; //  12/06/2017,09:00
            System.out.println("=======time BEGIN  :" + newFormatTimeBegin);
            // get end-time
            String[] endTime = newFormatTimeBegin.split(","); // 12/06/2017,09:00
            String tempEnd = endTime[0]; //  12/06/2017
            String newFormatTimeEnd = tempEnd + "," + result[1]; //   12/06/2017,10:00
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
            if (compareToNowBegin == 1 && compareToNowEnd == -1) {
                this.setPs_ID(RestAPI.MapUserReservedParkingSavers.get(time));
                return true;
            }
        }*/

        return false;
    }


    //==================================For Location=======================================
    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Log.i(LOG_TAG, "GoogleApiClient connetion has been successful!");
        mLocationRequest = LocationRequest.create();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(1000); // 1s
        // =========================== request the permission====================
        boolean permissionGranted = ActivityCompat.checkSelfPermission(this.getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
        if (permissionGranted) {
            System.out.println("permissionGranted=== ture==========");
        } else {
            System.out.println("permissionGranted=== false==========");
            ActivityCompat.requestPermissions(this.getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 200);
        }
        //======================================================================

        if (ActivityCompat.checkSelfPermission(this.getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this.getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        //  Location mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        //  System.out.println("mLastLocation.getLatitude "+ mLastLocation.getLatitude() );android.location.LocationListener
        //  System.out.println("mLastLocation.getLongitude "+ mLastLocation.getLongitude() );
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.i(LOG_TAG, "GoogleApiClient connetion has been suspended");
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.i(LOG_TAG, "GoogleApiClient connetion has failed");
    }

    /**
     * Calculate the distance of two location according to latitude and longitude
     *
     * @param lat1  Latitude of point 1 (in decimal degrees)
     * @param lon1  Longitude of point 1 (in decimal degrees)
     * @param lat2  Latitude of point 2 (in decimal degrees)
     * @param lon2  Longitude of point 2 (in decimal degrees)
     * @param unit  the unit you desire for result. 'M' is statute miles (default)  'K' is kilometers  'N' is nautical miles
     * @return  distance between these 2 points
     */
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

    /**
     * This function converts decimal degrees to radians
     * @param deg  decimal degrees
     * @return  radians
     */
    private static double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    /**
     * This function converts radians to decimal degrees
     * @param rad  radians
     * @return  decimal degrees
     */
    private static double rad2deg(double rad) {
        return (rad * 180 / Math.PI);
    }
}
