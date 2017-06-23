package com.example.apple.liu_androidauto_parkingsaverapp.webService;

import com.example.apple.liu_androidauto_parkingsaverapp.model.Server_ParkingSaver;

import java.util.List;

import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.PUT;
import retrofit.http.Query;

/**
 * This class uses retrofit to define parking saver REST-API
 * @author  Xinyu Liu, Yue Hu
 * Created on 2017/6/15.
 */

public interface Api_parkingSaver {

    /**
     * HTTP GET
     * Get information of all parking savers and save the information in a list
     *
     * Server_ParkingSaver: int id; String status; double[] coordination
     *
     * --------------------------------------------------------------------------
     * Our test URL:  http://localhost:8080/Parking/secured/parkingsaver
     * Your server URL: http://YourServer/parkingsaver
     * --------------------------------------------------------------------------
     *
     * @param authHeader used to Auth_Basic
     * @return a list of all parking savers from web-server
     */
    @GET("parkingsaver")
    Call<List<Server_ParkingSaver>> getAllParkingSavers(@Header("Authorization") String authHeader);

    /**
     * HTTP GET
     * Get the coordinate through the giving ID from web-server.
     *
     * --------------------------------------------------------------------------
     * Our test URL:  http://localhost:8080/Parking/secured/parkingsaver/coordination?id=1
     * Your server URL: http://YourServer/parkingsaver/coordination?id=1
     * --------------------------------------------------------------------------
     * Return structure example:   [11.11,22.22]
     *
     * @param authHeader used to Auth_Basic
     * @param id the ID of one parking saver
     * @return the coordinate of the parking saver(ID)
     */
    @GET("parkingsaver/coordination")
    Call<double[]> getCoordinationInfo(@Header("Authorization") String authHeader, @Query("id") int id);

    /**
     * HTTP GET
     * Get the status of one parking saver through a giving ID
     *
     * --------------------------------------------------------------------------
     * Our test URL:  http://localhost:8080/Parking/secured/parkingsaver/status?id=1
     * Your server URL: http://YourServer/parkingsaver/status?id=1
     * --------------------------------------------------------------------------
     *
     * @param authHeader used to Auth_Basic
     * @param id the ID of one parking saver
     * @return the status of the parking saver(id)
     */
    @GET("parkingsaver/status")
    Call<String> getStausInfo(@Header("Authorization") String authHeader, @Query("id") int id);

    /**
     * HTTP PUT
     * Update the information of one parking saver through the giving ID
     *
     * --------------------------------------------------------------------------
     * Our test URL:  http://localhost:8080/Parking/secured/parkingsaver?id=1
     * Your server URL: http://YourServer/parkingsaver?id=1
     * --------------------------------------------------------------------------
     *
     * @param authHeader used to Auth_Basic
     * @param id the ID of one parking saver
     * @param parkingSaver one Server_ParkingSaver object
     * @return the Server_ParkingSaver object you want to update
     */
    @PUT("parkingsaver")
    Call<Server_ParkingSaver> updateParkingSaverStatus(@Header("Authorization") String authHeader,@Query("id") int id ,@Body Server_ParkingSaver parkingSaver);

}



