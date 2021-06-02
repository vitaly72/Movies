package com.example.movies.domain.repository;

import com.example.movies.domain.models.MovieQuery;
import com.example.movies.domain.models.MovieResponse;

import io.reactivex.rxjava3.core.Observable;

public interface IMovieRepository {
    Observable<MovieResponse> getMovies(MovieQuery movieQuery);
}
