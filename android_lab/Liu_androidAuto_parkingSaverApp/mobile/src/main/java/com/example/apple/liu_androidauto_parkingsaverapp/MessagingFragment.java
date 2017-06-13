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

import android.app.Fragment;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * The main fragment that shows the buttons and the text view containing the log.
 */
public class MessagingFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = MessagingFragment.class.getSimpleName();

    private Button /*mSendSingleConversation*/ bt_block;
    private Button /*mSendTwoConversations*/ bt_unblock;
//    private Button mSendConversationWithThreeMessages;
    private TextView mDataPortView;
    private Button mClearLogButton;

    private Messenger mService;
    private boolean mBound;
    private boolean ifBlock ;

    private final ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder service) {
         //   System.out.println("=============== connect");
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

    public MessagingFragment() {
    }

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
            setIfBlock(true);
            System.out.println("==== at the moment block status is : " + this.isIfBlock());
            sendMsg(1, 1);
        } else if (view == bt_unblock) {
            setIfBlock(false);
            System.out.println("==== at the moment block status is : " + this.isIfBlock());
            sendMsg(1, 1);
        }else if (view == mClearLogButton) {
            MessageLogger.clear(getActivity());
            mDataPortView.setText(MessageLogger.getAllMessages(getActivity()));
        }
    }

    public boolean isIfBlock() {
        return ifBlock;
    }

    public void setIfBlock(boolean ifBlock) {
        this.ifBlock = ifBlock;
    }

    @Override
    public void onStart() {
        super.onStart();
        getActivity().bindService(new Intent(getActivity(), MessagingService.class), mConnection,
                Context.BIND_AUTO_CREATE);
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
        if (mBound) {
            getActivity().unbindService(mConnection);
            mBound = false;
        }
    }

    private void sendMsg(int howManyConversations, int messagesPerConversation) {
        if (mBound) {
            Message msg = Message.obtain(null, MessagingService.MSG_SEND_NOTIFICATION,
                    howManyConversations, messagesPerConversation);
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
}
