package com.example.movies.domain.repository;

import com.example.movies.domain.models.VideoResponse;

import io.reactivex.rxjava3.core.Observable;

public interface IVideoRepository {
    Observable<VideoResponse> getVideo(int idMovie);
}
