package com.example.movies.presentation.movie.favorite;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.movies.data.local.dao.MovieDao;
import com.example.movies.domain.models.Movie;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class FavoriteMoviesViewModel extends ViewModel {
    private final MovieDao movieDao;

    @Inject
    public FavoriteMoviesViewModel(MovieDao movieDao) {
        this.movieDao = movieDao;
    }

    public LiveData<List<Movie>> getFavouriteMovies() {
        return movieDao.getAllFavouriteMovies();
    }

    public void insertFavouriteMovie(Movie movie) {
        movieDao.insertFavouriteMovie(movie);
    }

    public Movie getFavouriteMovieByID(int id) {
        return movieDao.getFavouriteMovieById(id);
    }

    public void deleteFavouriteMovie(Movie movie) {
        movieDao.deleteFavouriteMovie(movie);
    }
}
