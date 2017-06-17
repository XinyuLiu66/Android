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

import android.app.Activity;

import java.security.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import android.os.Bundle;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new MessagingFragment())
                    .commit();
        }
    }

//    public static void changeDateFormat(String time) {
//        SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd");
//
//        String input = "2015-11-11" ;
//
//        System.out.print(input + " Parses as ");
//
//        Date t;
//
//        try {
//            t = ft.parse(input);
//            System.out.println(t);
//        } catch (ParseException e) {
//            System.out.println("Unparseable using " + ft);
//        }
//    }
}
