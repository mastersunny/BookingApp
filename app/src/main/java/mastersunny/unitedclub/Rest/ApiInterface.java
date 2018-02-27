package mastersunny.unitedclub.Rest;

import java.util.List;

import mastersunny.unitedclub.Model.AccessModel;
import mastersunny.unitedclub.Model.CategoryDTO;
import mastersunny.unitedclub.Model.MoviesResponse;
import mastersunny.unitedclub.Model.ResponseModel;
import mastersunny.unitedclub.Model.SliderDTO;
import mastersunny.unitedclub.Model.StoreDTO;
import mastersunny.unitedclub.Model.StoreOfferDTO;
import mastersunny.unitedclub.Model.TransactionDTO;
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

    //Offer List
    @GET("api/get_store")
    Call<StoreDTO> getStoreById(@Query("store_id") int store_id, @Query("access_token") String accessToken);

    @GET("api/get_store_offer")
    Call<List<StoreOfferDTO>> getStoreOffers(@Query("store_id") int store_id, @Query("access_token") String accessToken);

    @GET("api/get_category_offer")
    Call<List<StoreOfferDTO>> getCategoryOffers(@Query("category_id") int category_id, @Query("access_token") String accessToken);
    //Offer List


    //Login and registration
    @GET("api/is_access_token_valid/{access_token}")
    Call<AccessModel> isAccessTokenValid(@Path("access_token") String accessToken);

    @POST("api/get_access")
    @FormUrlEncoded
    Call<AccessModel> initRegistration(@Field("phone_number") String phoneNumber);

    @POST("api/get_access")
    @FormUrlEncoded
    Call<AccessModel> verifyCode(@Field("phone_number") String phoneNumber, @Field("code") String code);

    @POST("api/sign_up")
    @FormUrlEncoded
    Call<AccessModel> signUp(@Field("first_name") String firstName, @Field("last_name") String lastName, @Field("email") String email, @Field("phone_number") String phoneNumber);

    @POST("api/client_info")
    @FormUrlEncoded
    Call<AccessModel> sendRegistrationToServer(@Field("access_token") String accessToken, @Field("fcm_token") String fcmToken);
    //Login and registration


    //Category
    @GET("api/get_categories")
    Call<List<CategoryDTO>> getCategories(@Query("access_token") String accessToken);
    //Category

    //Transaction Details
    @GET("api/recent_transaction")
    Call<List<TransactionDTO>> getRecentTransactions(@Query("access_token") String accessToken);

    @GET("api/paid_transaction")
    Call<List<TransactionDTO>> getPaidTransactions(@Query("access_token") String accessToken);

    @GET("api/due_transaction")
    Call<List<TransactionDTO>> getDueTransactions(@Query("access_token") String accessToken);

    @POST("api/submit_transaction")
    @FormUrlEncoded
    Call<ResponseModel> submitTransaction(@Field("offer_id") int offerId, @Field("amount") double amount,
                                          @Query("access_token") String accessToken);
    //Transaction Details


}
