package com.example.movies.presentation.movie;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.movies.data.local.MovieDatabase;
import com.example.movies.domain.models.FavouriteMovie;
import com.example.movies.domain.models.Movie;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class MainViewModel extends AndroidViewModel {
    private static MovieDatabase database;
    private final LiveData<List<Movie>> movies;
    private final LiveData<List<FavouriteMovie>> favouriteMovies;

    public MainViewModel(@NonNull Application application) {
        super(application);
        database = MovieDatabase.getInstance(application);
        movies = database.movieDao().getAllMovies();
        favouriteMovies = database.movieDao().getAllFavouriteMovies();
    }

    public Movie getMovieByID(int id) {
        try {
            return new GetMovieTask().execute(id).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public FavouriteMovie getFavouriteMovieByID(int id) {
        try {
            return new GetFavouriteMovieTask().execute(id).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public LiveData<List<FavouriteMovie>> getFavouriteMovies() {
        return favouriteMovies;
    }

    public void deleteAllMovies() {
        new DeleteAllTask().execute();
    }

    public void insertMovie(Movie movie) {
        new InsertMoviesTask().execute(movie);
    }

    public void deleteMovie(Movie movie) {
        new DeleteMoviesTask().execute(movie);
    }

    public void insertFavouriteMovie(FavouriteMovie movie) {
        new InsertFavouriteMoviesTask().execute(movie);
    }

    public void deleteFavouriteMovie(FavouriteMovie movie) {
        new DeleteFavouriteMoviesTask().execute(movie);
    }

    private static class DeleteFavouriteMoviesTask extends AsyncTask<FavouriteMovie, Void, Void> {
        @Override
        protected Void doInBackground(FavouriteMovie... movies) {
            if (movies != null && movies.length > 0) {
                database.movieDao().deleteFavouriteMovie(movies[0]);
            }
            return null;
        }
    }

    private static class InsertFavouriteMoviesTask extends AsyncTask<FavouriteMovie, Void, Void> {
        @Override
        protected Void doInBackground(FavouriteMovie... movies) {
            if (movies != null && movies.length > 0) {
                database.movieDao().insertFavouriteMovie(movies[0]);
            }
            return null;
        }
    }

    private static class DeleteMoviesTask extends AsyncTask<Movie, Void, Void> {
        @Override
        protected Void doInBackground(Movie... movies) {
            if (movies != null && movies.length > 0) {
                database.movieDao().deleteMovie(movies[0]);
            }
            return null;
        }
    }

    private static class InsertMoviesTask extends AsyncTask<Movie, Void, Void> {
        @Override
        protected Void doInBackground(Movie... movies) {
            if (movies != null && movies.length > 0) {
                database.movieDao().insertMovie(movies[0]);
            }
            return null;
        }
    }

    private static class DeleteAllTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            database.movieDao().deleteAllMovies();
            return null;
        }
    }


    private static class GetMovieTask extends AsyncTask<Integer, Void, Movie> {
        @Override
        protected Movie doInBackground(Integer... integers) {
            if (integers != null && integers.length > 0) {
                return database.movieDao().getMovieById(integers[0]);
            }
            return null;
        }
    }

    private static class GetFavouriteMovieTask extends AsyncTask<Integer, Void, FavouriteMovie> {
        @Override
        protected FavouriteMovie doInBackground(Integer... integers) {
            if (integers != null && integers.length > 0) {
                return database.movieDao().getFavouriteMovieById(integers[0]);
            }
            return null;
        }
    }

    public LiveData<List<Movie>> getMovies() {
        return movies;
    }
}
