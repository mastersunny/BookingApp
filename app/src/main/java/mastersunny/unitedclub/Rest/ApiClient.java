package mastersunny.unitedclub.Rest;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by ASUS on 1/20/2018.
 */

public class ApiClient {
    //    public static final String API_KEY = "35e1efe2694731228a71fe05cd424848";
    private static Retrofit retrofit = null;

    public static final String BASE_URL = "http://63de92fb.ngrok.io/";
    public static final String API_KEY = "";

    private ApiClient() {

    }

    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
