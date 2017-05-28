package webservice;
import com.example.apple.parkingsaver.ServerParkingSaver;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.Call;

/**
 * Created by apple on 2017/5/28.
 */

public interface Api {
    @GET("/")
    public Call<ServerParkingSaver> getAllParkingSavers();
    @GET("/parkingSaver/{parkingSaverID}")
    public Call<ServerParkingSaver> getIndividualParkingSavers(@Path("parkingSaverID") int parkingSaverID);


}

