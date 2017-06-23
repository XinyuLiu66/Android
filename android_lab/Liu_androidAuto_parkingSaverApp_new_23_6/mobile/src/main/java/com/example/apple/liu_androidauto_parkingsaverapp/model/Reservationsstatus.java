package com.example.apple.liu_androidauto_parkingsaverapp.model;

/**
 * Created by dell on 19.06.2017.
 */

public class Reservationsstatus {

//    @SerializedName("endTime")
//    @Expose
    private String endTime;
//    @SerializedName("ps_ID")
//    @Expose
    private int ps_ID;
//    @SerializedName("rever_ID")
//    @Expose
    private int rever_ID;
//    @SerializedName("startTime")
//    @Expose
    private String beginTime;

    public int getPs_ID() {
        return ps_ID;
    }

    public void setPs_ID(int ps_ID) {
        this.ps_ID = ps_ID;
    }

    public int getRever_ID() {
        return rever_ID;
    }

    public void setRever_ID(int rever_ID) {
        this.rever_ID = rever_ID;
    }

    public Reservationsstatus(){


    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }


    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String startTime) {
        this.beginTime = startTime;
    }

}