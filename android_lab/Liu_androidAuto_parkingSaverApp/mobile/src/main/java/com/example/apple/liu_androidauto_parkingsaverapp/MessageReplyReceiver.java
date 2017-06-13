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

import android.app.Notification;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.app.RemoteInput;
import android.util.Log;
import android.widget.Toast;

import com.example.apple.liu_androidauto_parkingsaverapp.model.Server_ParkingSaver;
import com.example.apple.liu_androidauto_parkingsaverapp.webService.Api_parkingSaver;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;


import retrofit.Call;
import retrofit.Retrofit;
import retrofit.GsonConverterFactory;


import retrofit.Callback;
import retrofit.Response;

/**
 * A receiver that gets called when a reply is sent to a given conversationId.
 */
public class MessageReplyReceiver extends BroadcastReceiver {

    private static final String TAG = MessageReplyReceiver.class.getSimpleName();
    private MessagingFragment mf = new MessagingFragment();
    public static List<Server_ParkingSaver> listAllParkingSavers = new ArrayList<>();

    @Override
    public void onReceive(Context context, Intent intent) {

        System.out.println("==================My Reply==============");

        if (com.example.apple.liu_androidauto_parkingsaverapp.MessagingService.REPLY_ACTION.equals(intent.getAction())) {
            int conversationId = intent.getIntExtra(com.example.apple.liu_androidauto_parkingsaverapp.MessagingService.CONVERSATION_ID, -1);
            CharSequence reply = getMessageText(intent);
            if (conversationId != -1) {
                Log.d(TAG, "Got reply (" + reply + ") for ConversationId " + conversationId);
                MessageLogger.logMessage(context, "ConversationId: " + conversationId +
                        " received a reply: [" + reply + "]");

                // ===========handle this reply blocked the parking saver or unblocked the parking saver====================
                String strReply = reply.toString();
                System.out.println("At reply class , the status of block " + mf.isIfBlock());
                if(mf.isIfBlock() == true) {
                 //   if(strReply.contains("yes") || strReply.contains("block")) {
                    System.out.println("=====implement isIfBlock() == true ");
                        getAllParkingSaver();
                        updateParkingSaverState(5,"true");
                //    }
                }
                if(mf.isIfBlock() == false) {
                 //   if(strReply.contains("yes") || strReply.contains("unblock")) {
                    System.out.println("=====implement isIfBlock() == false ");
                        getAllParkingSaver();
                        updateParkingSaverState(5,"false");
                 //   }
                }
                // Update the notification to stop the progress spinner.
                NotificationManagerCompat notificationManager =
                        NotificationManagerCompat.from(context);
                Notification repliedNotification = new NotificationCompat.Builder(context)
                        .setSmallIcon(R.drawable.notification_icon)
                        .setLargeIcon(BitmapFactory.decodeResource(
                                context.getResources(), R.drawable.android_contact))
                        .setContentText(context.getString(R.string.replied))
                        .build();
                notificationManager.notify(conversationId, repliedNotification);
            }
        }
    }

    /**
     * Get the message text from the intent.
     * Note that you should call {@code RemoteInput#getResultsFromIntent(intent)} to process
     * the RemoteInput.
     */
    private CharSequence getMessageText(Intent intent) {
        Bundle remoteInput = RemoteInput.getResultsFromIntent(intent);
        if (remoteInput != null) {
            return remoteInput.getCharSequence(
                    com.example.apple.liu_androidauto_parkingsaverapp.MessagingService.EXTRA_REMOTE_REPLY);
        }
        return null;
    }


    //==============================REST API==========================
    //=================PUT================

// TODO: (1)add dependency of retrofit (2): add data model class
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
     //   String blockedStatus = psWhichTochangeStatus.getStatus();
       // reservationStatusOfthisPS.put(startTimeSection[timeSectionTOchangeID],status);
        psWhichTochangeStatus.setStatus(status);
        Call<Server_ParkingSaver> putIndividualParkingSaverCall = api.updateParkingSaverStatus(parkingSaverID,psWhichTochangeStatus);


        //======= construct a new parkingSaver object  for post!!!!!!!!!!!!!!!!=========//



        putIndividualParkingSaverCall.enqueue(new Callback<Server_ParkingSaver>() {
            @Override
            public void onResponse(Response<Server_ParkingSaver> response, Retrofit retrofit) {
                System.out.println("======Callback of put !!!! also has been implemented");

                Log.d("onResponse", "" + response.code() +
                        "  response body " + response.body() +
                        " responseError " + response.errorBody() + " responseMessage " +
                        response.message());

                Server_ParkingSaver parkingSaver = response.body();
                String detail  = "";

                String coordination = parkingSaver.getCoordinate();
                int    parkingSaverID = parkingSaver.getId();
                String status = parkingSaver.getStatus();


                detail += "ID: "+ parkingSaverID + "\n" + "Coordination: " + coordination + "\n"
                        +"Status: " +status+  "\n\n";

                System.out.println(detail);
//                if(status == "true") {
//                    Toast.makeText(getApplicationContext(), "Reservarion success", Toast.LENGTH_SHORT).show();
//                }else {
//                    Toast.makeText(getApplicationContext(), "Reservarion ended", Toast.LENGTH_SHORT).show();
//                }


            }

            @Override
            public void onFailure(Throwable t) {
           //     Toast.makeText(getApplicationContext(), "Error while fetching parkingSaver", Toast.LENGTH_SHORT).show();
            }

        });
    }

    //=================GET================
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


                Log.d("onResponse", "" + response.code() +
                        "  response body " + response.body() +
                        " responseError " + response.errorBody() + " responseMessage " +
                        response.message());

                List<Server_ParkingSaver> list_parkingSavers = response.body();
                listAllParkingSavers = list_parkingSavers;


            }

            @Override
            public void onFailure(Throwable t) {
              //  Toast.makeText(getApplicationContext(), "Error while fetching parkingSaver", Toast.LENGTH_SHORT).show();
                System.out.println("======get all failure");

            }
        });
    }
}