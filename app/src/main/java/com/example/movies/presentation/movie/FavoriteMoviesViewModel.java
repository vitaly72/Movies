package com.example.movies.presentation.movie;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.movies.data.local.MovieDatabase;
import com.example.movies.domain.models.Movie;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class FavoriteMoviesViewModel extends AndroidViewModel {
    private static MovieDatabase database;
    private final LiveData<List<Movie>> favouriteMovies;

    public FavoriteMoviesViewModel(@NonNull Application application) {
        super(application);
        database = MovieDatabase.getInstance(application);
        favouriteMovies = database.movieDao().getAllFavouriteMovies();
    }

    public Movie getFavouriteMovieByID(int id) {
        try {
            return new GetFavouriteMovieTask().execute(id).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public LiveData<List<Movie>> getFavouriteMovies() {
        return favouriteMovies;
    }

    public void insertFavouriteMovie(Movie movie) {
        new InsertFavouriteMoviesTask().execute(movie);
    }

    public void deleteFavouriteMovie(Movie movie) {
        new DeleteFavouriteMoviesTask().execute(movie);
    }

    private static class DeleteFavouriteMoviesTask extends AsyncTask<Movie, Void, Void> {
        @Override
        protected Void doInBackground(Movie... movies) {
            if (movies != null && movies.length > 0) {
                database.movieDao().deleteFavouriteMovie(movies[0]);
            }
            return null;
        }
    }

    private static class InsertFavouriteMoviesTask extends AsyncTask<Movie, Void, Void> {
        @Override
        protected Void doInBackground(Movie... movies) {
            if (movies != null && movies.length > 0) {
                database.movieDao().insertFavouriteMovie(movies[0]);
            }
            return null;
        }
    }

    private static class GetFavouriteMovieTask extends AsyncTask<Integer, Void, Movie> {
        @Override
        protected Movie doInBackground(Integer... integers) {
            if (integers != null && integers.length > 0) {
                return database.movieDao().getFavouriteMovieById(integers[0]);
            }
            return null;
        }
    }
}
