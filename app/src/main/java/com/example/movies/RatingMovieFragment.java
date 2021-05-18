package com.example.movies;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;

import com.example.movies.databinding.FragmentPopularMovieBinding;
import com.example.movies.databinding.FragmentRatingMovieBinding;
import com.example.movies.domain.Movie;
import com.example.movies.presentation.movie.MovieAdapter;
import com.example.movies.presentation.movie.MovieViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class RatingMovieFragment extends Fragment {
    private MovieAdapter movieAdapter;
    private List<Movie> movies;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentRatingMovieBinding binding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_rating_movie,
                container,
                false);
        View view = binding.getRoot();

        movieAdapter = new MovieAdapter();
        binding.movieList.recyclerViewMain.setLayoutManager(new GridLayoutManager(view.getContext(), 2));
        binding.movieList.recyclerViewMain.setAdapter(movieAdapter);

        MovieViewModel movieViewModel = ViewModelProviders.of(this).get(MovieViewModel.class);
        movieViewModel.initMovie(false);
        movieViewModel.getMovieData().observe(getViewLifecycleOwner(), movieResponse -> {
            movies = movieResponse.getMovieList();
            System.out.println("movies.size() = " + movies.size());
            movieAdapter.setMovies(movies);
        });

//        movieAdapter.setOnPosterClickListener(this);

        return view;
    }
}