package com.example.apple.liu_androidauto_parkingsaverapp.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by apple on 2017/5/28.
 */

public class Server_ParkingSaver {
  //  @SerializedName("coordination")
    private double[] coordination; // later should be changed to coordination object
  //  @SerializedName("id")
    private int id;
 //   @SerializedName("status")
    private String status;


    //anti - Serialization
    public Server_ParkingSaver() {

    }

    public double[] getCoordination() {
        return coordination;
    }

    public void setCoordination(double[] coordination) {
        this.coordination = coordination;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
