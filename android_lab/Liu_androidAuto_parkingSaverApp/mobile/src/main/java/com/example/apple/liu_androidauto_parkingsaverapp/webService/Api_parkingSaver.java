package com.example.apple.liu_androidauto_parkingsaverapp.webService;

import com.example.apple.liu_androidauto_parkingsaverapp.model.Server_ParkingSaver;

import java.util.List;

import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by apple on 2017/5/28.
 */

    //    ServerParkingSaver parkingSaver = new ServerParkingSaver("Li","29-30",false,
      //          6,"3:00","5:00");


public interface Api_parkingSaver {
    @GET("parkingsaver")
    Call<List<Server_ParkingSaver>> getAllParkingSavers();
 //   public List<ParkingSaverInfo> getAllParkingSavers()

    @GET("parkingsaver/coordination")
    Call<double[]> getCoordinationInfo(@Query("id") int id);

    @GET("parkingsaver/status")
    Call<String> getStausInfo(@Query("id") int id);

//    @PUT("parkingsaver/{parkingSaverID}")
//    Call<Server_ParkingSaver> updateParkingSaverStatus(@Path("parkingSaverID") int id,@Body Server_ParkingSaver parkingSaver);
@PUT("parkingsaver")
Call<Server_ParkingSaver> updateParkingSaverStatus(@Query("id") int id ,@Body Server_ParkingSaver parkingSaver);
}

//    @PUT("change/{parkingSaverID}")
//    Call<ServerParkingSaver> put(@Path("parkingSaverID") int parkingSaverID,@Body ServerParkingSaver serverParkingSaver);



