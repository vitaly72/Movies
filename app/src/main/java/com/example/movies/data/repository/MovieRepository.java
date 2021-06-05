package com.example.movies.data.repository;

import com.example.movies.data.remote.IMovieApi;
import com.example.movies.domain.models.MovieQuery;
import com.example.movies.domain.models.MovieResponse;
import com.example.movies.domain.repository.IMovieRepository;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Observable;

public class MovieRepository implements IMovieRepository {
    private final IMovieApi movieApi;

    @Inject
    public MovieRepository(IMovieApi movieApi) {
        this.movieApi = movieApi;
    }

    public Observable<MovieResponse> getMovies(MovieQuery movieQuery) {
        return movieApi.movie(movieQuery);
    }
}
