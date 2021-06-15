package com.example.movies.domain.usecase;

import com.example.movies.data.repository.MovieRepository;

public class GetMoviesUseCase {
    private MovieRepository movieRepository;

    public GetMoviesUseCase(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

//    public Single<List<Movie>> buildUseCaseSingle() {
//        return movieRepository.getMovies();
//    }
}
