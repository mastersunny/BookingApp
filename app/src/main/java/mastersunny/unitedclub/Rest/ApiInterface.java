package mastersunny.unitedclub.Rest;

import java.util.List;

import mastersunny.unitedclub.Model.AccessModel;
import mastersunny.unitedclub.Model.MoviesResponse;
import mastersunny.unitedclub.Model.SliderDTO;
import mastersunny.unitedclub.Model.StoreDTO;
import mastersunny.unitedclub.Model.StoreOfferDTO;
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

    @GET("api/popular_stores")
    Call<List<StoreDTO>> getPopularStores(@Query("access_token") String accessToken);

    @GET("api/all_stores")
    Call<List<StoreDTO>> getAllStores(@Query("access_token") String accessToken);

    @GET("api/get_sliders")
    Call<List<SliderDTO>> getSliders(@Query("access_token") String accessToken);

    @GET("api/store/{id}")
    Call<StoreDTO> getStoreById(@Path("id") int id, @Query("access_token") String accessToken);

    @GET("api/store_offer/{id}")
    Call<List<StoreOfferDTO>> getStoreOffers(@Path("id") int id, @Query("access_token") String accessToken);

    @GET("api/category/{id}")
    Call<List<StoreOfferDTO>> getCategoryOffers(@Path("id") int id, @Query("access_token") String accessToken);

    @POST("api/get_access")
    @FormUrlEncoded
    Call<AccessModel> initRegistration(@Field("phone_number") String phoneNumber);

    @POST("api/get_access")
    @FormUrlEncoded
    Call<AccessModel> verifyCode(@Field("phone_number") String phoneNumber, @Field("code") String code);

    @POST("api/sign_up")
    @FormUrlEncoded
    Call<AccessModel> signUp(@Field("first_name") String firstName, @Field("last_name") String lastName, @Field("email") String email, @Field("phone_number") String phoneNumber);


}
