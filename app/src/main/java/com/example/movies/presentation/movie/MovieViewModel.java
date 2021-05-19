package com.example.movies.presentation.movie;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.example.movies.data.repository.MovieRepository;
import com.example.movies.data.repository.VideoRepository;
import com.example.movies.domain.models.Movie;
import com.example.movies.domain.models.MovieDataSource;
import com.example.movies.domain.models.MovieDataSourceFactory;
import com.example.movies.domain.models.MovieResponse;
import com.example.movies.domain.models.VideoResponse;

public class MovieViewModel extends ViewModel {
    private MutableLiveData<MovieResponse> movieData;
    private MutableLiveData<VideoResponse> videoData;

    public LiveData<PagedList<Movie>> moviePagedList;
    private LiveData<MovieDataSource> liveDataSource;

    public MovieViewModel() {
    }

    public void init() {
        MovieDataSourceFactory itemDataSourceFactory = new MovieDataSourceFactory();
        liveDataSource = itemDataSourceFactory.movieLiveDataSource;
        PagedList.Config config = new PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setPageSize(MovieDataSource.PAGE_SIZE)
                .build();
        moviePagedList = new LivePagedListBuilder<>(itemDataSourceFactory, config).build();
    }

    public void initMovie(boolean sortByPopular, int page) {
//        if (movieData != null) return;
        movieData = MovieRepository.getInstance().getMovies(sortByPopular, page);
    }

    public void initVideo(int id) {
        if (videoData != null) return;
        videoData = VideoRepository.getInstance().getVideo(id);
    }

    public LiveData<MovieResponse> getMovieData() {
        return movieData;
    }

    public LiveData<VideoResponse> getVideoData() {
        return videoData;
    }
}
