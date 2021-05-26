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
import com.example.movies.databinding.FragmentPopularMovieBinding;
import com.example.movies.domain.models.Movie;
import com.example.movies.presentation.details.DetailActivity;
import com.example.movies.utils.JSONUtils;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import dagger.hilt.android.WithFragmentBindings;

@WithFragmentBindings
@AndroidEntryPoint
public class PopularMovieFragment extends Fragment {
    private MovieAdapter movieAdapter;
    private List<Movie> movies;
    private int page = 1;
    private MovieViewModel movieViewModel;
    public MovieRepository movieRepository;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentPopularMovieBinding binding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_popular_movie,
                container,
                false);
        View view = binding.getRoot();

        movieAdapter = new MovieAdapter();
        binding.movieList.textViewTitleList.setText("Популярні фільми");
        binding.movieList.recyclerViewMain.setLayoutManager(new GridLayoutManager(view.getContext(), 2));
        binding.movieList.recyclerViewMain.setAdapter(movieAdapter);

        movieViewModel = ViewModelProviders.of(this).get(MovieViewModel.class);

        movieViewModel.initMovie(movieRepository, true, page);
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
        movieViewModel.initMovie(movieRepository, true, ++page);
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
        System.out.println("movieJsonString = " + movieJsonString);
        intent.putExtra("id", movieJsonString);
        startActivity(intent);
    }

    private int getColumnCount() {
//        DisplayMetrics displayMetrics = new DisplayMetrics();
//        this.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
//        int width = (int) (displayMetrics.widthPixels / displayMetrics.density);
//        return Math.max(width / 260, 2);
        return 0;
    }
}