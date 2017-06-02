package com.example.apple.parkingsaver;

import com.google.gson.annotations.SerializedName;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by apple on 2017/5/28.
 */

public class ServerParkingSaver {
    @SerializedName("user")
    private String user;
    @SerializedName("coordination")
    private String coordination; // later should be changed to coordination object
    @SerializedName("reservation")
    private String reservation;
    @SerializedName("parkingSaverID")
    private int parkingSaverID;
    @SerializedName("reservation_status")
    private Map<String,String> reservation_status;
    private static final String[] startTimeSection = new String[]{
            "timeSectionFrom0","timeSectionFrom6", "timeSectionFrom8",
            "timeSectionFrom10","timeSectionFrom12","timeSectionFrom14",
            "timeSectionFrom16","timeSectionFrom18","timeSectionFrom21"};

    //anti - Serialization
    public ServerParkingSaver() {

    }


    public String getUser() {
        return user;
    }
    public void setUser(String user) {
        this.user = user;
    }
    public String getCoordination() {
        return coordination;
    }
    public void setCoordination(String coordination) {
        this.coordination = coordination;
    }

    public int getParkingSaverID() {
        return parkingSaverID;
    }
    public void setParkingSaverID(int parkingSaverID) {
        this.parkingSaverID = parkingSaverID;
    }

    public Map<String, String> getReservation_status() {
        return reservation_status;
    }

    public void setReservation_status(Map<String, String> reservation_status) {
        this.reservation_status = reservation_status;
    }

    public static String[] getStarttimesection() {
        return startTimeSection;
    }

}
