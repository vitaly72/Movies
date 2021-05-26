package com.example.movies.data.local.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.movies.domain.models.Movie;

import java.util.List;

@Dao
public interface MovieDao {
    @Query("SELECT * FROM favourite_movies")
    LiveData<List<Movie>> getAllFavouriteMovies();

    @Query("SELECT * FROM favourite_movies WHERE id == :movieId")
    Movie getFavouriteMovieById(int movieId);

    @Insert
    void insertFavouriteMovie(Movie favouriteMovie);

    @Delete
    void deleteFavouriteMovie(Movie favouriteMovie);
}
