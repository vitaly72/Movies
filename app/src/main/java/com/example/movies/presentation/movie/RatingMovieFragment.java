package com.example.movies.presentation.movie;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.movies.R;
import com.example.movies.data.repository.MovieRepository;
import com.example.movies.databinding.FragmentRatingMovieBinding;
import com.example.movies.domain.models.Movie;
import com.example.movies.presentation.details.DetailActivity;
import com.example.movies.utils.JSONUtils;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;
import dagger.hilt.android.WithFragmentBindings;

@WithFragmentBindings
@AndroidEntryPoint
public class RatingMovieFragment extends Fragment {
    private MovieAdapter movieAdapter;
    private MovieViewModel movieViewModel;
    private int page = 1;
    private List<Movie> movies;
    public MovieRepository movieRepository;

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
        binding.movieList.textViewTitleList.setText("Рейтингові фільми");

        movieAdapter = new MovieAdapter();
        binding.movieList.recyclerViewMain.setLayoutManager(new GridLayoutManager(view.getContext(), 2));
        binding.movieList.recyclerViewMain.setAdapter(movieAdapter);

        movieViewModel = ViewModelProviders.of(this).get(MovieViewModel.class);
        movieViewModel.initMovie(movieRepository, false, 1);
        movieViewModel.getMovieData().observe(getViewLifecycleOwner(), movieResponse -> {
            movies = movieResponse.getMovieList();
            System.out.println("movies.size() = " + movies.size());
            movieAdapter.setMovies(movies);
        });

        LinearLayoutManager layoutManager = (LinearLayoutManager) binding.movieList.recyclerViewMain.getLayoutManager();
        binding.movieList.recyclerViewMain.addOnScrollListener(
                new EndlessRecyclerOnScrollListener(layoutManager) {
                    @Override
                    public void onLoadMore(int current_page) {
                        System.out.println("current_page = " + current_page);
                        loadNext();
                    }
                });

        movieAdapter.setOnPosterClickListener(this::onPosterClick);

        return view;
    }

    private void loadNext() {
        movieViewModel.initMovie(movieRepository, false, ++page);
        movieViewModel.getMovieData().observe(getViewLifecycleOwner(), movieResponse -> {
            movies = movieResponse.getMovieList();
            System.out.println("movies.size() = " + movies.size());
            movieAdapter.addMovies(movies);
        });
    }

    public void onPosterClick(int position) {
        Intent intent = new Intent(getActivity(), DetailActivity.class);
        Movie movie = movieAdapter.getMovies().get(position);
        String movieJsonString = JSONUtils.getGsonParser().toJson(movie);
        intent.putExtra("id", movieJsonString);
        startActivity(intent);
    }
}