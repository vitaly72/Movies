package com.example.movies.data.repository;

import androidx.lifecycle.MutableLiveData;

import com.example.movies.data.remote.IMovieApi;
import com.example.movies.domain.models.MovieResponse;
import com.example.movies.domain.repository.IMovieRepository;
import com.example.movies.utils.Constants;

import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@Singleton
public final class MovieRepository implements IMovieRepository {
    private final IMovieApi movieApi;

    @Inject
    public MovieRepository(IMovieApi movieApi) {
        this.movieApi = movieApi;
    }

    public MutableLiveData<MovieResponse> getMovies(boolean sortByPopular, int page) {
        MutableLiveData<MovieResponse> movieData = new MutableLiveData<>();

        movieApi.movie(Constants.PARAMS.VALUE.API_KEY,
                        Constants.PARAMS.VALUE.LANGUAGE_VALUE,
                        sortByPopular ? Constants.PARAMS.VALUE.SORT_BY_POPULARITY : Constants.PARAMS.VALUE.SORT_BY_TOP_RATED,
                        Constants.PARAMS.VALUE.MIN_VOTE_COUNT_VALUE,
                        Integer.toString(page))
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
}
