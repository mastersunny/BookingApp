package mastersunny.unitedclub.rest;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.io.IOException;
import java.util.HashSet;

import mastersunny.unitedclub.activities.LoginActivity;
import mastersunny.unitedclub.utils.Constants;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by ASUS on 1/20/2018.
 */

public class ApiClient {
    public static String TAG = "ApiClient";

    public static String serverUrl = "http://d9db5516.ngrok.io/";
    public static final String BASE_URL = serverUrl;
    public static final String APP_NAME = "unitel/";

    private static Retrofit retrofit = null;

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

    private static Retrofit.Builder builder
            = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create());

    private static OkHttpClient.Builder httpClient =
            new OkHttpClient.Builder();


    public static <S> S createService(Context context, Class<S> serviceClass) {
        httpClient.interceptors().clear();
        httpClient.addInterceptor(new AddCookiesInterceptor(context));
        httpClient.addInterceptor(new ReceivedCookiesInterceptor(context));
        builder.client(httpClient.build());
        retrofit = builder.build();
        return retrofit.create(serviceClass);
    }

    public static class AddCookiesInterceptor implements Interceptor {

        private Context context;

        public AddCookiesInterceptor(Context context) {
            this.context = context;
        }

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request.Builder builder = chain.request().newBuilder();

            HashSet<String> preferences = (HashSet<String>) PreferenceManager
                    .getDefaultSharedPreferences(context)
                    .getStringSet(Constants.PREF_COOKIES, new HashSet<String>());

            Constants.debugLog(TAG, preferences.size() + "");

            for (String cookie : preferences) {
                Constants.debugLog(TAG, cookie);
                builder.addHeader("Cookie", cookie);
            }

            return chain.proceed(builder.build());

        }
    }

    public static class ReceivedCookiesInterceptor implements Interceptor {

        private Context context;

        public ReceivedCookiesInterceptor(Context context) {
            this.context = context;
        }

        @Override
        public Response intercept(Chain chain) throws IOException {
            Response response = chain.proceed(chain.request());

            String url = response.request().url().toString();
            Constants.debugLog(TAG, url);

            if (!response.headers("Set-Cookie").isEmpty()) {
                HashSet<String> cookies = (HashSet<String>) PreferenceManager
                        .getDefaultSharedPreferences(context)
                        .getStringSet(Constants.PREF_COOKIES, new HashSet<String>());

                for (String header : response.headers("Set-Cookie")) {
                    cookies.add(header);
                }

                SharedPreferences.Editor editor = PreferenceManager
                        .getDefaultSharedPreferences(context)
                        .edit();
                editor.putStringSet(Constants.PREF_COOKIES, cookies).apply();
                editor.commit();
            } else {
                if (url.endsWith("login") && !url.contains("api")) {
                    Intent intent = new Intent(context, LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    context.startActivity(intent);
                }
            }

            return response;
        }
    }
}
