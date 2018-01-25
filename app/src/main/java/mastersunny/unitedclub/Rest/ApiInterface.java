package mastersunny.unitedclub.Rest;

import java.util.List;

import mastersunny.unitedclub.Model.MoviesResponse;
import mastersunny.unitedclub.Model.StoreDTO;
import mastersunny.unitedclub.Model.StoreOfferDTO;
import retrofit2.Call;
import retrofit2.http.GET;
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

    @GET("all/0")
    Call<List<StoreDTO>> getAllStores(@Query("api_key") String apiKey);

    @GET("store/{id}")
    Call<List<StoreOfferDTO>> getStoreOffers(@Path("id") int id, @Query("api_key") String apiKey);


}
