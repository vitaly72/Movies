package com.example.movies.presentation.movie;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.movies.data.repository.MovieRepository;
import com.example.movies.domain.models.MovieResponse;
import com.example.movies.domain.repository.IMovieRepository;

import javax.inject.Inject;

public class MovieViewModel extends ViewModel {
    private MutableLiveData<MovieResponse> movieData;

    @Inject
    public void initMovie(IMovieRepository movieRepository, boolean sortByPopular, int page) {
        movieData = movieRepository.getMovies(sortByPopular, page);
    }

    public LiveData<MovieResponse> getMovieData() {
        return movieData;
    }
}
