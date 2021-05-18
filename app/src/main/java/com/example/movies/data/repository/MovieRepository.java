package com.example.movies.data.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.movies.data.remote.NetworkService;
import com.example.movies.domain.models.Movie;
import com.example.movies.domain.models.MovieResponse;
import com.example.movies.domain.repository.IMovieRepository;
import com.example.movies.utils.Constants;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public final class MovieRepository implements IMovieRepository {
    private static MovieRepository instance;
    private LiveData<List<Movie>> movieList;

    private MovieRepository() {
    }

    public static MovieRepository getInstance() {
        if (instance == null) instance = new MovieRepository();
        return instance;
    }

    public MutableLiveData<MovieResponse> getMovies(boolean sortByPopular) {
        MutableLiveData<MovieResponse> movieData = new MutableLiveData<>();

        NetworkService.createService()
                .movie(Constants.PARAMS.VALUE.API_KEY,
                        Constants.PARAMS.VALUE.LANGUAGE_VALUE,
                        sortByPopular ? Constants.PARAMS.VALUE.SORT_BY_POPULARITY : Constants.PARAMS.VALUE.SORT_BY_TOP_RATED,
                        Constants.PARAMS.VALUE.MIN_VOTE_COUNT_VALUE,
                        Integer.toString(50))
                .enqueue(new Callback<MovieResponse>() {
                    @Override
                    public void onResponse(@NotNull Call<MovieResponse> call,
                                           @NotNull Response<MovieResponse> response) {
                        movieData.setValue(response.body());
                    }

                    @Override
                    public void onFailure(@NotNull Call<MovieResponse> call,
                                          @NotNull Throwable throwable) {
                        movieData.setValue(null);
                    }
                });
        return movieData;
    }

    public LiveData<List<Movie>> getMovieList() {
        return movieList;
    }
}
