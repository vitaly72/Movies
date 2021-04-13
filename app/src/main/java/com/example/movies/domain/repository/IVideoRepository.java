package com.example.movies.domain.repository;

import androidx.lifecycle.MutableLiveData;

import com.example.movies.domain.models.VideoResponse;

public interface IVideoRepository {
    MutableLiveData<VideoResponse> getVideo(int idMovie);
}
