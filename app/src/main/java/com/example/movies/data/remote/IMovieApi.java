package com.example.movies.data.remote;

import com.example.movies.domain.models.MovieQuery;
import com.example.movies.domain.models.MovieResponse;
import com.example.movies.domain.models.VideoResponse;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface IMovieApi {
    @GET("discover/movie")
    Observable<MovieResponse> movie(@QueryMap MovieQuery movieQuery);

    @GET("movie/{id}/videos")
    Observable<VideoResponse> video(@Path("id") int idMovie,
                              @Query("api_key") String apiKey,
                              @Query("language") String language);
}
