package com.example.movies.presentation.movie;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.movies.data.repository.MovieRepository;
import com.example.movies.data.repository.VideoRepository;
import com.example.movies.domain.models.MovieResponse;
import com.example.movies.domain.models.VideoResponse;

public class MovieViewModel extends ViewModel {
    private MutableLiveData<MovieResponse> movieData;
    private MutableLiveData<VideoResponse> videoData;

    public void initMovie() {
        if (movieData != null) return;
        movieData = MovieRepository.getInstance().getMovies();
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
