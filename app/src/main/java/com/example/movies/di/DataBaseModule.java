package com.example.movies.di;

import android.app.Application;

import androidx.room.Room;

import com.example.movies.data.local.MovieDatabase;
import com.example.movies.data.local.dao.MovieDao;
import com.example.movies.data.remote.IMovieApi;
import com.example.movies.utils.Constants;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
@InstallIn(SingletonComponent.class)
public class DataBaseModule {
    @Provides
    @Singleton
    public static MovieDatabase providePokemonDB(Application application) {
        return Room.databaseBuilder(application, MovieDatabase.class, "movies.db")
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build();
    }

    @Provides
    @Singleton
    public static MovieDao providePokeDao(MovieDatabase movieDatabase) {
        return movieDatabase.movieDao();
    }
}
