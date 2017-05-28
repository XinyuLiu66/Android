package com.example.apple.parkingsaver;

import com.google.gson.annotations.SerializedName;
/**
 * Created by apple on 2017/5/28.
 */

public class ServerParkingSaver {
    @SerializedName("user")
    private String user;
    @SerializedName("coordination")
    private String coordination; // later should be changed to coordination object
    @SerializedName("ifReservation")
    private boolean ifReservation;
    @SerializedName("parkingSaverID")
    private int parkingSaverID;
    @SerializedName("startTime")
    private String startTime;
    @SerializedName("endTime")
    private String endTime;


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
    public boolean isReservation() {
        return ifReservation;
    }
    public void setReservation(boolean reservation) {
        this.ifReservation = reservation;
    }
    public int getParkingSaverID() {
        return parkingSaverID;
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
}
