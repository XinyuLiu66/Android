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

/**
 * A receiver that gets called when a reply is sent to a given conversationId.
 * @author Xinyu Liu, Yue Hu
 */
public class MessageReplyReceiver extends BroadcastReceiver {

    private static final String TAG = MessageReplyReceiver.class.getSimpleName();
    RestAPI restAPI = new RestAPI();

    @Override
    public void onReceive(Context context, Intent intent) {

        if (com.example.apple.liu_androidauto_parkingsaverapp.MessagingService.REPLY_ACTION.equals(intent.getAction())) {
            int conversationId = intent.getIntExtra(com.example.apple.liu_androidauto_parkingsaverapp.MessagingService.CONVERSATION_ID, -1);
            CharSequence reply = getMessageText(intent);
            if (conversationId != -1) {
                Log.d(TAG, "Got reply (" + reply + ") for ConversationId " + conversationId);
                MessageLogger.logMessage(context, "ConversationId: " + conversationId +
                        " received a reply: [" + reply + "]");

                // ===========handle this reply blocked the parking saver or unblocked the parking saver====================
                assert reply != null;
                String strReply = reply.toString();

/*                if(MessagingService.getFlag_indicate_block_in_service() == 0) {
                    // ==========justify the reply String========
                 //   if(strReply.contains("yes") || strReply.contains("block")) {
                    restAPI.updateParkingSaverState(3*//*MessagingFragment.getPs_ID()*//*,"blocked");
//                    System.out.println("=====implement isIfBlock() == true ");
//                        getAllParkingSaver("blocked");

                //    }
                }
                if(MessagingService.getFlag_indicate_block_in_service() == 1) {
                    // ==========justify the reply String========
                 //   if(strReply.contains("yes") || strReply.contains("unblock")) {

                     //   getAllParkingSaver("unblocked");
                    restAPI.updateParkingSaverState(3*//*MessagingFragment.getPs_ID()*//*,"unblocked");


                 //   }
                }
                if(MessagingService.getFlag_indicate_block_in_service() == 2) {
                    // ==========justify the reply String========
                    //   if(strReply.contains("yes") || strReply.contains("unblock")) {

                    //getAllParkingSaver("unblocked");
                    restAPI.updateParkingSaverState(3*//*MessagingFragment.getPs_ID()*//*,"unblocked");


                    //   }
                }*/

                //====================Hu Yue======================================================
                //TODO Because the emulator cant simulate the reply through voice!
                // condition: Car nearby the PS
                if((strReply.contains("yes") || strReply.contains("unblock")) &&  MessagingFragment.get_isPS_Activated()){
                    int ps_ID = MessagingFragment.getPs_ID();
                    restAPI.updateParkingSaverState(ps_ID,"unblocked");
                }
                //================================================================
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
}


