package com.example.movies.domain.models;

import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;

public class MovieDataSourceFactory extends DataSource.Factory<Integer, Movie> {
    public MutableLiveData<MovieDataSource> movieLiveDataSource = new MutableLiveData<>();

    @Override
    public DataSource<Integer, Movie> create() {
        MovieDataSource userDataSource = new MovieDataSource();
        movieLiveDataSource.postValue(userDataSource);
        return userDataSource;
    }
}
