package com.example.movies.presentation.movie;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.movies.data.repository.MovieRepository;
import com.example.movies.domain.repository.IMovieRepository;

import org.jetbrains.annotations.NotNull;

public class MovieViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    @NotNull
    private final MovieRepository movieRepository;
    private final boolean sortByPopular;
    private final int page;

    public MovieViewModelFactory(@NotNull MovieRepository movieRepository,
                                 boolean sortByPopular, int page) {
        this.movieRepository = movieRepository;
        this.sortByPopular = sortByPopular;
        this.page = page;
    }

    @NotNull
    @Override
    public <T extends ViewModel> T create(@NotNull Class<T> modelClass) {
        if (modelClass == MovieViewModel.class) {
            return (T) new MovieViewModel(movieRepository);
        }
        return null;
    }
}
