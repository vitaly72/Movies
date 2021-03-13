package com.example.movies.api;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.movies.utils.ApiConst.BASE_URL;

public class NetworkService {
    private static final OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .build();

    private static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build();

    public static <T> T createService(Class<T> serviceClass) {
        return retrofit.create(serviceClass);
    }

    public static IMovieApi createService() {
        return retrofit.create(IMovieApi.class);
    }
}
