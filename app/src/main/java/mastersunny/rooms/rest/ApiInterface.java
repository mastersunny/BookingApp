package mastersunny.rooms.rest;

import java.util.List;

import mastersunny.rooms.gmap.GooglePlaceDTO;
import mastersunny.rooms.gmap.GooglePlaceDetails;
import mastersunny.rooms.models.AccessModel;
import mastersunny.rooms.models.ApiResponse;
import mastersunny.rooms.models.CategoryDTO;
import mastersunny.rooms.models.ExamDTO;
import mastersunny.rooms.models.MoviesResponse;
import mastersunny.rooms.models.OfferDTO;
import mastersunny.rooms.models.DivisionResponseDto;
import mastersunny.rooms.models.RestModel;
import mastersunny.rooms.models.RoomBookingDTO;
import mastersunny.rooms.models.RoomDTO;
import mastersunny.rooms.models.SliderDTO;
import mastersunny.rooms.models.StoreDTO;
import mastersunny.rooms.models.TransactionDTO;
import mastersunny.rooms.models.UserDTO;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
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

    @GET("api/get_store")
    Call<StoreDTO> getStoreById(@Query("store_id") int store_id, @Query("access_token") String accessToken);

    @GET("api/get_store_offer")
    Call<List<OfferDTO>> getStoreOffers(@Query("store_id") int store_id, @Query("access_token") String accessToken);

    @GET("api/get_category_offer")
    Call<List<OfferDTO>> getCategoryOffers(@Query("category_id") int category_id, @Query("access_token") String accessToken);

    @GET("api/get_code/{phone_number}")
    Call<RestModel> getCode(@Path("phone_number") String phoneNumber);

    @POST("api/get_client_access")
    @FormUrlEncoded
    Call<RestModel> verifyCode(@Field("phone_number") String phoneNumber, @Field("code") String code);

    @POST("api/sign_up_client")
    @FormUrlEncoded
    Call<RestModel> signUp(@Field("first_name") String firstName, @Field("last_name") String lastName, @Field("email") String email, @Field("phone_number") String phoneNumber);


    @POST("api/submit_transaction")
    @FormUrlEncoded
    Call<RestModel> submitTransaction(@Field("offer_id") int offerId, @Field("amount") double amount,
                                      @Query("access_token") String accessToken);

    @GET("api/get_transaction")
    Call<TransactionDTO> getTransactionDetails(@Query("transaction_id") int transaction_id,
                                               @Query("access_token") String accessToken);

    @POST(ApiClient.APP_NAME + "api/login")
    @FormUrlEncoded
    Call<UserDTO> login(@Field("access_token") String accessToken);

    @POST(ApiClient.APP_NAME + "api/signup")
    @Headers({"Content-Type: application/json;charset=UTF-8"})
    Call<UserDTO> signup(@Query("access_token") String accessToken, @Body UserDTO userDTO);

    @POST(ApiClient.APP_NAME + "api/fcmtoken")
    @FormUrlEncoded
    Call<String> sendRegistrationToServer(@Field("fcm_token") String fcmToken);

    @GET(ApiClient.APP_NAME + "api/v1/places")
    Call<List<DivisionResponseDto>> getPlaces(@Query("page") int page,
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
    Call<RoomBookingDTO> createBooking(@Field("start_date") String startDate,
                                       @Field("end_date") String endDate,
                                       @Field("room_id") Long roomId,
                                       @Field("room_cost") double roomCost,
                                       @Field("guest_count") int guestCount);

    @GET(ApiClient.APP_NAME + "api/bookings")
    Call<List<RoomBookingDTO>> getBookings(@Query("page") int page,
                                           @Query("size") int size,
                                           @Query("sort") String sort);

    @DELETE(ApiClient.APP_NAME + "api/bookings/{id}")
    Call<RoomBookingDTO> deleteBooking(@Path("id") Long bookingId);


    @GET("https://maps.googleapis.com/maps/api/place/autocomplete/json")
    Call<GooglePlaceDTO> getPlaceFromGoogle(@Query("input") String input,
                                            @Query("key") String apiKey);

    @GET("https://maps.googleapis.com/maps/api/place/details/json")
    Call<GooglePlaceDetails> getPlaceDetailsFromGoogle(@Query("placeid") String placeId,
                                                       @Query("fields") String fields,
                                                       @Query("key") String apiKey);

    @GET("api/divisions")
    Call<ApiResponse> getDivisions();

    @GET("api/banners")
    Call<ApiResponse> getBanners();

    @GET("api/districts")
    Call<ApiResponse> getDistricts(@Query("division_id") Long divisionId);

    @GET("api/hotel")
    Call<ApiResponse> getHotels(@Query("start_date") String startDate,
                                @Query("end_date") String endDate,
                                @Query("no_of_guest") int noOfGuest,
                                @Query("latitude") double latitude,
                                @Query("longitude") double longitude);


    @GET("api/popular/districts")
    Call<ApiResponse> getPopularDistricts();

}
