package com.example.movies.api;

import com.example.movies.data.models.MovieResponse;
import com.example.movies.data.models.VideoList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface IMovieApi {
    @GET("discover/movie")
    Call<MovieResponse> movie(@Query("api_key") String apiKey,
                              @Query("language") String language,
                              @Query("sort_by") String sortBy,
                              @Query("vote_count.gte") String voteCount,
                              @Query("page") String page);

    @GET("movie/{id}/videos")
    Call<VideoList> video(@Path("id") int idMovie,
                          @Query("api_key") String apiKey,
                          @Query("language") String language);
}
