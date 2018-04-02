package builderspace.restaurantfinder.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Service Creator for connections.
 */
public class ServiceCreator {

    private static final String BASE_URL = "https://maps.googleapis.com/maps/api/place/";

    private static HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);

    private static OkHttpClient.Builder okHttpBuilder = new OkHttpClient.Builder().addInterceptor(loggingInterceptor);

    private static Gson gson = new GsonBuilder().create();

    private static Retrofit.Builder retrofitBuilder = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson));
    private static Retrofit retrofit = retrofitBuilder.client(okHttpBuilder.build()).build();

    /* package */
    static Retrofit getRetrofit() {
        return retrofit;
    }

    public static <S> S createService(Class<S> retrofitAPI) {
        return retrofit.create(retrofitAPI);
    }

}
