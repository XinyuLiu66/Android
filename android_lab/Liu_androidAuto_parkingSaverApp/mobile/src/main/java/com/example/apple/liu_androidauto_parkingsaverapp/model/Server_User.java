package com.example.apple.liu_androidauto_parkingsaverapp.model;

import java.util.*;

/**
 * Created by apple on 2017/6/15.
 */

public class Server_User {

    //  @SerializedName("id")
    private int id; // later should be changed to coordination object
    //  @SerializedName("userName")
    private String userName;
    //   @SerializedName("reservationStatus")
    private Map<String, Integer> reservationStatus;

    public Server_User() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Map<String, Integer> getReservationStatus() {
        return reservationStatus;
    }

    public void setReservationStatus(Map<String, Integer> reservationStatus) {
        this.reservationStatus = reservationStatus;
    }

}
