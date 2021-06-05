package com.example.movies.data.repository;

import com.example.movies.data.remote.IMovieApi;
import com.example.movies.domain.models.VideoResponse;
import com.example.movies.domain.repository.IVideoRepository;
import com.example.movies.utils.Constants;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Observable;

public class VideoRepository implements IVideoRepository {
    private final IMovieApi movieApi;

    @Inject
    public VideoRepository(IMovieApi movieApi) {
        this.movieApi = movieApi;
    }

    public Observable<VideoResponse> getVideo(int idMovie) {
        return movieApi.video(idMovie,
                Constants.PARAMS.VALUE.API_KEY,
                Constants.PARAMS.VALUE.LANGUAGE_VALUE);
    }
}
