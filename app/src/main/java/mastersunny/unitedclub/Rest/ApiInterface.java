package mastersunny.unitedclub.Rest;

import android.transition.Slide;

import java.util.List;

import mastersunny.unitedclub.Model.MoviesResponse;
import mastersunny.unitedclub.Model.SliderDTO;
import mastersunny.unitedclub.Model.StoreDTO;
import mastersunny.unitedclub.Model.StoreOfferDTO;
import mastersunny.unitedclub.Model.UserDTO;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by ASUS on 1/20/2018.
 */

public interface ApiInterface {
    @GET("movie/top_rated")
    Call<MoviesResponse> getTopRatedMovies(@Query("api_key") String apiKey);

    @GET("movie/{id}")
    Call<MoviesResponse> getMovieDetails(@Path("id") int id, @Query("api_key") String apiKey);

    @GET("api/popularStores")
    Call<List<StoreDTO>> getPopularStores();

    @GET("api/allStores")
    Call<List<StoreDTO>> getAllStores();

    @GET("api/getSliders")
    Call<List<SliderDTO>> getSliders();

    @GET("api/store/{id}")
    Call<List<StoreOfferDTO>> getStoreOffers(@Path("id") int id);

    @POST("api/login")
    @FormUrlEncoded
    Call<UserDTO> logIn(@Field("phone_number") String phoneNo, @Field("password") String password);

    @POST("api/initialRegistration")
    @FormUrlEncoded
    Call<String> initRegistration(@Field("phone_number") String phoneNo);

    @POST("api/checkCode")
    @FormUrlEncoded
    Call<String> checkCode(@Field("phone_number") String phoneNo, @Field("code") String code);

}
