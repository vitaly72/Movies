package com.example.movies.data.local;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.movies.data.local.dao.MovieDao;
import com.example.movies.domain.models.Movie;

@Database(entities = {Movie.class}, version = 4, exportSchema = false)
public abstract class MovieDatabase extends RoomDatabase {
//    private static final String DB_NAME = "movies.db";
//    private static MovieDatabase database;
//
//    public static synchronized MovieDatabase getInstance(Context context) {
//        if (database == null) {
//            database = Room.databaseBuilder(context, MovieDatabase.class, DB_NAME)
//                    .fallbackToDestructiveMigration().build();
//        }
//        return database;
//    }

    public abstract MovieDao movieDao();
}
