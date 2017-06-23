package com.example.apple.liu_androidauto_parkingsaverapp.model;

import java.util.*;

/**
 * This class changes the JSON data of user to JAVA object
 * @author  Xinyu Liu, Yue Hu
 */

public class Server_User {

    //  @SerializedName("id")
    private int id; // later should be changed to coordination object
    //  @SerializedName("userName")
    private String userName;
    //   @SerializedName("reservationStatus")
    private List<Reservationsstatus> reservationsStatus;

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

    public List<Reservationsstatus> getReservationsStatus() {
        return reservationsStatus;
    }

    public void setReservationsStatus(List<Reservationsstatus> reservationsStatus) {
        this.reservationsStatus = reservationsStatus;
    }


}
