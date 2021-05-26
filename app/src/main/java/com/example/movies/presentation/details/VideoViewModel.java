package com.example.movies.presentation.details;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.movies.data.repository.VideoRepository;
import com.example.movies.domain.models.VideoResponse;

public class VideoViewModel extends ViewModel {
    private MutableLiveData<VideoResponse> videoData;

    public void initVideo(int id) {
        if (videoData != null) return;
        videoData = VideoRepository.getInstance().getVideo(id);
    }

    public LiveData<VideoResponse> getVideoData() {
        return videoData;
    }
}
