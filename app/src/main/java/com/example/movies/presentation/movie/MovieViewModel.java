package com.example.movies.presentation.movie;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.movies.data.repository.MovieRepository;
import com.example.movies.domain.models.Movie;
import com.example.movies.domain.models.MovieQuery;
import com.example.movies.domain.models.MovieResponse;

import java.util.ArrayList;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

@HiltViewModel
public class MovieViewModel extends ViewModel {
    private MutableLiveData<ArrayList<Movie>> movieList = new MutableLiveData<>();

    private final MovieRepository movieRepository;
    @Inject
    public MovieViewModel(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
//        this.sortByPopular = sortByPopular;
//        this.page = page;
    }

    public void nextPage(MovieQuery movieQuery) {
//        movieData = movieRepository.getMovies(movieQuery);
    }

    public void getMovies(MovieQuery movieQuery) {
        movieRepository.getMovies(movieQuery)
                .subscribeOn(Schedulers.io())
                .map(MovieResponse::getMovieList)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> movieList.setValue(result),
                        error -> System.out.println("error: " + error.getMessage()));
    }

    public MutableLiveData<ArrayList<Movie>> getMovieList() {
        return movieList;
    }
}
