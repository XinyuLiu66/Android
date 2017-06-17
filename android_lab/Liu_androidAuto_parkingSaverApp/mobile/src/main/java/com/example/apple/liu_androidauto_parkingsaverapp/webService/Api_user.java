package com.example.apple.liu_androidauto_parkingsaverapp.webService;

import com.example.apple.liu_androidauto_parkingsaverapp.model.Server_ParkingSaver;
import com.example.apple.liu_androidauto_parkingsaverapp.model.Server_User;

import java.util.List;
import java.util.Map;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by apple on 2017/6/15.
 */

public interface Api_user {
    @GET("users/reservationstatus")
    Call<Map<String,Integer>> getUser(@Query("username") String username);
}


//@Path("/users")
//public class UsersResource {
//
//    UserService us = new UserService();
//
//    //=================do not use any more=============
//    @GET
//    @Produces(MediaType.APPLICATION_JSON)
//    public List<User> getUsers() {
//        return us.getAllUsers();
//    }
//
//    @GET
//    @Path("/reservationstatus")
//    @Produces(MediaType.APPLICATION_JSON)
//    public Map<String,Integer> getUser(@QueryParam("username") String username) {
//        //	User theUser = null;
//        List<User> list_users = us.getAllUsers();
//        for(User user : list_users) {
//            if(user.getUserName().equals(username) ) {
//                //	theUser = user;
//                return us.getUser(user.getId()).getReservationStatus();
//
//            }
//        }
//        return null;
//
//    }