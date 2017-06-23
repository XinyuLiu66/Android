package com.example.apple.liu_androidauto_parkingsaverapp.webService;

import com.example.apple.liu_androidauto_parkingsaverapp.model.Reservationsstatus;

import java.util.List;
import java.util.Map;
import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Query;

/**
 * This class uses retrofit to define user REST-API
 * @author  Xinyu Liu, Yue Hu
 * Created on 2017/6/15.
 */

public interface Api_user {

    /**
     * HTTP GET
     * Get the information of a user's reservation-status(time, parking saver id) through the username
     *
     * --------------------------------------------------------------------------
     * Our test URL:  http://localhost:8080/Parking/secured/users/reservationstatus?username=huyue@gmail.com
     * Your server URL: http://YourServer/users/reservationstatus?username=huyue@gmail.com
     * --------------------------------------------------------------------------
     * Return structure example: Map   KEY                      VALUE
     *                                "17/16/2017,09:00-11:00", 1
     *                                "17/16/2017,15:00-20:00", 3
     *
     * @param authHeader used to Auth_Basic
     * @param username the username of a user
     * @return the reservation-status of a user(Map type)
     */
    @GET("users/reservationsstatus")
   // Call<Map<String,Integer>> getUser(@Header("Authorization") String authHeader, @Query("username") String username);
   Call<List<Reservationsstatus>> getUser(@Header("Authorization") String authHeader, @Query("username") String username);
}
