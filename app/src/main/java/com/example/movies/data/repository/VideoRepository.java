package com.example.movies.data.repository;

import androidx.lifecycle.MutableLiveData;

import com.example.movies.data.remote.IMovieApi;
import com.example.movies.data.remote.NetworkService;
import com.example.movies.domain.models.VideoResponse;
import com.example.movies.domain.repository.IVideoRepository;
import com.example.movies.utils.Constants;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public final class VideoRepository implements IVideoRepository {
    private static VideoRepository instance;
    private IMovieApi movieApi;

    private VideoRepository(IMovieApi movieApi) {
        this.movieApi = movieApi;
    }

    public static VideoRepository getInstance() {
        if (instance == null) instance = new VideoRepository(instance.movieApi);
        return instance;
    }

    public MutableLiveData<VideoResponse> getVideo(int idMovie) {
        MutableLiveData<VideoResponse> videoData = new MutableLiveData<>();
        movieApi.video(idMovie,
                Constants.PARAMS.VALUE.API_KEY,
                Constants.PARAMS.VALUE.LANGUAGE_VALUE)
                .enqueue(new Callback<VideoResponse>() {
                    @Override
                    public void onResponse(@NotNull Call<VideoResponse> call,
                                           @NotNull Response<VideoResponse> response) {
                        videoData.setValue(response.body());
                    }

                    @Override
                    public void onFailure(@NotNull Call<VideoResponse> call,
                                          @NotNull Throwable throwable) {
                        videoData.setValue(null);
                    }
                });
        return videoData;
    }
}
