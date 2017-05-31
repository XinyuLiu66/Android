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
    @SerializedName("startTime")
    private String startTime;
    @SerializedName("endTime")
    private String endTime;

    //anti - Serialization
    public ServerParkingSaver() {

    }

    // Serialization
//    public ServerParkingSaver(String user, String coordination, String reservation,
//                              int parkingSaverID, String startTime, String endTime) {
//        this.user = user;
//        this.coordination = coordination;
//        this.reservation = reservation;
//        this.parkingSaverID = parkingSaverID;
//        this.startTime = startTime;
//        this.endTime = endTime;
//    }

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
    public String getReservation() {
        return reservation;
    }
    public void setReservation(String reservation) {
        this.reservation = reservation;
    }
    public int getParkingSaverID() {
        return this.parkingSaverID;
    }
    public void setPakingSaverID(int pakingSaverID) {
        this.parkingSaverID = pakingSaverID;
    }
    public String getStartTime() {
        return startTime;
    }
    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }
    public String getEndTime() {
        return endTime;
    }
    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

//    public Map<String,String> createCommitParams(){
//        Map<String,String> params = new HashMap<String,String>();
//        params.put("user", user);
//        params.put("coordination", coordination);
//        params.put("reservation", reservation);
//        params.put("parkingSaverID", parkingSaverID);
//        params.put("startTime", startTime);
//        params.put("endTime", endTime);
//        return params;
//    }
}
