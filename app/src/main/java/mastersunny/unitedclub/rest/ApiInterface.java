package mastersunny.unitedclub.rest;

import java.util.List;

import mastersunny.unitedclub.models.AccessModel;
import mastersunny.unitedclub.models.CategoryDTO;
import mastersunny.unitedclub.models.ExamDTO;
import mastersunny.unitedclub.models.MoviesResponse;
import mastersunny.unitedclub.models.OfferDTO;
import mastersunny.unitedclub.models.PlaceDTO;
import mastersunny.unitedclub.models.RestModel;
import mastersunny.unitedclub.models.RoomBookingDTO;
import mastersunny.unitedclub.models.RoomDTO;
import mastersunny.unitedclub.models.SliderDTO;
import mastersunny.unitedclub.models.StoreDTO;
import mastersunny.unitedclub.models.TransactionDTO;
import mastersunny.unitedclub.models.UserDTO;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
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
    Call<StoreDTO> getStoreByCode(@Query("store_code") String store_code, @Query("access_token") String accessToken);

    @GET("api/get_store")
    Call<StoreDTO> getStoreById(@Query("store_id") int store_id, @Query("access_token") String accessToken);

    @GET("api/get_store_offer")
    Call<List<OfferDTO>> getStoreOffers(@Query("store_id") int store_id, @Query("access_token") String accessToken);

    @GET("api/get_store_offer_all")
    Call<List<OfferDTO>> getStoreOfferAll(@Query("store_id") int store_id, @Query("access_token") String accessToken);

    @GET("api/get_category_offer")
    Call<List<OfferDTO>> getCategoryOffers(@Query("category_id") int category_id, @Query("access_token") String accessToken);
    //Offer List


    //Login and registration
    @GET("api/is_client_access_token_valid/{access_token}")
    Call<RestModel> isAccessTokenValid(@Path("access_token") String accessToken);

    @GET("api/get_code/{phone_number}")
    Call<RestModel> getCode(@Path("phone_number") String phoneNumber);

    @POST("api/get_client_access")
    @FormUrlEncoded
    Call<RestModel> verifyCode(@Field("phone_number") String phoneNumber, @Field("code") String code);

    @POST("api/sign_up_client")
    @FormUrlEncoded
    Call<RestModel> signUp(@Field("first_name") String firstName, @Field("last_name") String lastName, @Field("email") String email, @Field("phone_number") String phoneNumber);

    @POST("api/client_fcm_token")
    @FormUrlEncoded
    Call<AccessModel> sendRegistrationToServer(@Field("access_token") String accessToken, @Field("fcm_token") String fcmToken);
    //Login and registration


    //Category
    @GET("api/get_categories")
    Call<List<CategoryDTO>> getCategories(@Query("access_token") String accessToken);
    //Category

    //Transaction Details
    @GET("api/get_transactions_client")
    Call<List<TransactionDTO>> getTransactions(@Query("access_token") String accessToken,
                                               @Query("action") String action);

    @POST("api/submit_transaction")
    @FormUrlEncoded
    Call<RestModel> submitTransaction(@Field("offer_id") int offerId, @Field("amount") double amount,
                                      @Query("access_token") String accessToken);

    @GET("api/get_transaction")
    Call<TransactionDTO> getTransactionDetails(@Query("transaction_id") int transaction_id,
                                               @Query("access_token") String accessToken);
    //Transaction Details

    //user details
    @POST("api/update_client_profile")
    @FormUrlEncoded
    Call<RestModel> updateUserInfo(@Field("first_name") String firstName,
                                   @Field("last_name") String lastName,
                                   @Field("email") String email,
                                   @Field("phone_number") String phoneNumber,
                                   @Field("access_token") String accessToken);

    @Multipart
    @POST("api/update_client_profile_image")
    Call<RestModel> updateProfileImage(@Part MultipartBody.Part image, @Part("access_token") RequestBody accessToken);

    @GET("api/get_profile")
    Call<RestModel> getProfileDetails(@Query("access_token") String accessToken);
    ///user details


    @POST(ApiClient.APP_NAME + "api/login")
    @FormUrlEncoded
    Call<UserDTO> login(@Field("access_token") String accessToken);

    @POST(ApiClient.APP_NAME + "api/signup")
    @Headers({"Content-Type: application/json;charset=UTF-8"})
    Call<UserDTO> signup(@Query("access_token") String accessToken, @Body UserDTO userDTO);

    @GET(ApiClient.APP_NAME + "api/v1/places")
    Call<List<PlaceDTO>> getPlaces(@Query("page") int page,
                                   @Query("size") int size,
                                   @Query("sort") String sort);

    @GET(ApiClient.APP_NAME + "api/v1/exams")
    Call<List<ExamDTO>> getExams(@Query("page") int page,
                                 @Query("size") int size,
                                 @Query("sort") String sort);


    @GET(ApiClient.APP_NAME + "api/v1/searchrooms")
    Call<List<RoomDTO>> getRooms(@Query("start_date") String startDate,
                                 @Query("end_date") String endDate,
                                 @Query("latitude") double latitude,
                                 @Query("longitude") double longitude,
                                 @Query("guest") int guest);


    @POST(ApiClient.APP_NAME + "api/bookings")
    @FormUrlEncoded
    Call<RoomBookingDTO> bookRoom(@Field("start_date") String startDate,
                                  @Field("end_date") String endDate,
                                  @Field("room_id") Long roomId,
                                  @Field("room_cost") double roomCost,
                                  @Field("guest_count") int guestCount);

    @GET(ApiClient.APP_NAME + "api/bookings")
    Call<List<RoomDTO>> getBookings(@Query("page") int page,
                                    @Query("size") int size,
                                    @Query("sort") String sort);

}
