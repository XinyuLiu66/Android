package com.example.apple.parkingsaver.model;

import android.app.ProgressDialog;
import android.util.Log;
import android.widget.Toast;

import com.example.apple.parkingsaver.ServerParkingSaver;
import com.example.apple.parkingsaver.ui.MainActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;
import webservice.Api;

/**
 * Created by apple on 2017/5/31.
 */

public class Data_ParkingSaver {
  //  private static List<ServerParkingSaver> list_totalParkingSavers ;

    private static final String[] timeSection=
            {"0:00 - 6:00", "6:00 - 8:00", "8:00 - 10:00",
                    "10:00 - 12:00", "12:00 - 14:00", "14:00 - 16:00",
                    "16:00 - 18:00", "18:00 - 21:00", "21:00 - 24:00"};
    private static String[] startTimeSection = new String[]{
            "timeSectionFrom0","timeSectionFrom6", "timeSectionFrom8",
            "timeSectionFrom10","timeSectionFrom12","timeSectionFrom14",
            "timeSectionFrom16","timeSectionFrom18","timeSectionFrom21"};

//======================get all data to display==================//
    public static List<Item> getListData() {

        List<Item> list_totalDisplayData = new ArrayList<Item>();
     //   getAllParkingSaver();
     //   MainActivity mainActivity = new MainActivity();

        // store all the parking saver message got from http into list_totalParkingSavers
        List<ServerParkingSaver> list_totalParkingSavers = MainActivity.list_totalParkingSavers;
        int numOfParkingSavers = list_totalParkingSavers.size();

        for(int i=0; i< numOfParkingSavers;i++) {
            Item item = new Item();
            String parkingSaverID = "ParkingSaverID: " + String.valueOf(list_totalParkingSavers.get(i).getParkingSaverID());
            item.setPs_ID(parkingSaverID);
            //!!!!!!!!!!TODO 将parkingSaver的预定时间段改为9个元素的Array，同时
            //!!!!!!!!!!TODO 将每个parkingSaver的ifReservation改为9个元素的Array

            //TODO change hier array name to startTimeSection
            item.setTimeSectionFrom0(
                    new TimeSectionReservationMessage(timeSection[0],list_totalParkingSavers.get(i).getReservation_status().get(startTimeSection[0])));
           // System.out.println("0:00-6:00 reservation status = " + list_totalParkingSavers.get(i).getReservation_status().get(startTimeSection[0]));

            item.setTimeSectionFrom6(
                    new TimeSectionReservationMessage(timeSection[1],list_totalParkingSavers.get(i).getReservation_status().get(startTimeSection[1])));

            item.setTimeSectionFrom8(
                    new TimeSectionReservationMessage(timeSection[2],list_totalParkingSavers.get(i).getReservation_status().get(startTimeSection[2])));

            item.setTimeSectionFrom10(
                    new TimeSectionReservationMessage(timeSection[3],list_totalParkingSavers.get(i).getReservation_status().get(startTimeSection[3])));

            item.setTimeSectionFrom12(
                    new TimeSectionReservationMessage(timeSection[4],list_totalParkingSavers.get(i).getReservation_status().get(startTimeSection[4])));

            item.setTimeSectionFrom14(
                    new TimeSectionReservationMessage(timeSection[5],list_totalParkingSavers.get(i).getReservation_status().get(startTimeSection[5])));

            item.setTimeSectionFrom16(
                    new TimeSectionReservationMessage(timeSection[6],list_totalParkingSavers.get(i).getReservation_status().get(startTimeSection[6])));

            item.setTimeSectionFrom18(
                    new TimeSectionReservationMessage(timeSection[7],list_totalParkingSavers.get(i).getReservation_status().get(startTimeSection[7])));

            item.setTimeSectionFrom21(
                    new TimeSectionReservationMessage(timeSection[8],list_totalParkingSavers.get(i).getReservation_status().get(startTimeSection[8])));




            // add all this single item information to list_totalDisplayData
            list_totalDisplayData.add(item);


        }
        return list_totalDisplayData;

    }




}
