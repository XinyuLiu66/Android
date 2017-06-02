package webservice;
import com.example.apple.parkingsaver.ServerParkingSaver;

import java.util.List;
import java.util.Map;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit.http.Field;

import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.Body;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Path;
import retrofit.Call;

/**
 * Created by apple on 2017/5/28.
 */

    //    ServerParkingSaver parkingSaver = new ServerParkingSaver("Li","29-30",false,
      //          6,"3:00","5:00");


public interface Api {
    @GET("AllParkingSavers")
    public Call<List<ServerParkingSaver>> getAllParkingSavers();

    @GET("/parkingSaver/{parkingSaverID}")
    public Call<ServerParkingSaver> getIndividualParkingSavers(@Path("parkingSaverID") int parkingSaverID);


    @POST("parkingSaver")
//   // @FormUrlEncoded
    Call<ServerParkingSaver> post(@Body ServerParkingSaver serverParkingSaver);

    @PUT("change/{parkingSaverID}")
    Call<ServerParkingSaver> put(@Path("parkingSaverID") int parkingSaverID,@Body ServerParkingSaver serverParkingSaver);
}

