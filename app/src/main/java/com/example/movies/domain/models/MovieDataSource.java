package com.example.movies.domain.models;

import androidx.annotation.NonNull;
import androidx.paging.PageKeyedDataSource;

import com.example.movies.data.remote.NetworkService;
import com.example.movies.utils.Constants;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieDataSource extends PageKeyedDataSource<Integer, Movie> {
    public static int PAGE_SIZE = 20;
    public static int FIRST_PAGE = 1;

    @Override
    public void loadInitial(@NonNull @NotNull LoadInitialParams<Integer> params,
                            @NonNull @NotNull LoadInitialCallback<Integer, Movie> callback) {
        System.out.println("MovieDataSource.loadInitial");
//        NetworkService.createService()
//                .movie()
//                .enqueue(new Callback<MovieResponse>() {
//                    @Override
//                    public void onResponse(@NotNull Call<MovieResponse> call,
//                                           @NotNull Response<MovieResponse> response) {
//                        MovieResponse apiResponse = response.body();
//                        if (apiResponse != null) {
//                            List<Movie> responseItems = apiResponse.getMovieList();
//                            System.out.println("responseItems = " + responseItems.size());
//                            callback.onResult(responseItems, null, FIRST_PAGE + 1);
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(@NotNull Call<MovieResponse> call, @NotNull Throwable t) {
//                    }
//                });
    }

    @Override
    public void loadBefore(@NonNull @NotNull LoadParams<Integer> params,
                           @NonNull @NotNull LoadCallback<Integer, Movie> callback) {
    }

    @Override
    public void loadAfter(@NonNull @NotNull LoadParams<Integer> params,
                          @NonNull @NotNull LoadCallback<Integer, Movie> callback) {
        System.out.println("MovieDataSource.loadAfter");
//        params.key++

    }
}
