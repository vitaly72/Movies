package com.example.movies.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;


@Database(entities = {Movie.class,FavouriteMovie.class},version = 3,exportSchema = false)
public abstract class MovieDatabase extends RoomDatabase {

    private static final String DB_NAME="mymovies.db";
    private static MovieDatabase database;

    public static synchronized MovieDatabase getInstance(Context context){
        if (database==null){
            database= Room.databaseBuilder(context,MovieDatabase.class,DB_NAME).fallbackToDestructiveMigration().build();
        }
        return database;
    }

    public abstract MovieDao movieDao();
}
