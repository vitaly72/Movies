package com.example.movies.presentation.details;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.movies.data.repository.VideoRepository;
import com.example.movies.domain.models.Video;
import com.example.movies.domain.models.VideoResponse;

import java.util.ArrayList;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

@HiltViewModel
public class VideoViewModel extends ViewModel {
    private MutableLiveData<ArrayList<Video>> videoList = new MutableLiveData<>();
    private final VideoRepository videoRepository;

    @Inject
    public VideoViewModel(VideoRepository videoRepository) {
        this.videoRepository = videoRepository;
    }

    public void getVideos(int id) {
        videoRepository.getVideo(id)
                .subscribeOn(Schedulers.io())
                .map(VideoResponse::getVideoList)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> videoList.setValue(result),
                        error -> System.out.println("error: " + error.getMessage()));
    }

    public MutableLiveData<ArrayList<Video>> getVideoList() {
        return videoList;
    }
}
