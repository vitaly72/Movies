package com.example.movies.domain.repository;

import androidx.lifecycle.MutableLiveData;

import com.example.movies.domain.models.MovieResponse;

public interface IMovieRepository {
    MutableLiveData<MovieResponse> getMovies();
}
