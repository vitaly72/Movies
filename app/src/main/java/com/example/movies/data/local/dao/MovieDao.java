package com.example.movies.data.local.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.movies.domain.models.FavouriteMovie;
import com.example.movies.domain.models.Movie;

import java.util.List;

@Dao
public interface MovieDao {
    @Query("SELECT * FROM movies2")
    LiveData<List<Movie>> getAllMovies();

    @Query("SELECT * FROM favourite_movies")
    LiveData<List<FavouriteMovie>> getAllFavouriteMovies();

    @Query("SELECT * FROM movies2 WHERE id == :movieId")
    Movie getMovieById(int movieId);

    @Query("SELECT * FROM favourite_movies WHERE id == :movieId")
    FavouriteMovie getFavouriteMovieById(int movieId);

    @Query("DELETE FROM movies2")
    void deleteAllMovies();

    @Insert
    void insertMovie(Movie movie);

    @Delete
    void deleteMovie(Movie movie);

    @Insert
    void insertFavouriteMovie(FavouriteMovie favouriteMovie);

    @Delete
    void deleteFavouriteMovie(FavouriteMovie favouriteMovie);
}
