package com.example.movies.data.remote;

import com.example.movies.domain.models.MovieResponse;
import com.example.movies.domain.models.VideoResponse;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface IMovieApi {
    @GET("discover/movie")
    Observable<MovieResponse> movie(@Query("api_key") String apiKey,
                                    @Query("language") String language,
                                    @Query("sort_by") String sortBy,
                                    @Query("vote_count.gte") String voteCount,
                                    @Query("page") String page);

    @GET("movie/{id}/videos")
    Call<VideoResponse> video(@Path("id") int idMovie,
                              @Query("api_key") String apiKey,
                              @Query("language") String language);
}
