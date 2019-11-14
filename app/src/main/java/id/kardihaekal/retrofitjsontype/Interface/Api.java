package id.kardihaekal.retrofitjsontype.Interface;

import id.kardihaekal.retrofitjsontype.Photos;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface Api {

  @GET("/photos")
  Call<List<Photos>> getData();

}
