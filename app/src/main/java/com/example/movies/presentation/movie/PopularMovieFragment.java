package com.example.movies.presentation.movie;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.movies.data.repository.MovieRepository;
import com.example.movies.databinding.FragmentPopularMovieBinding;
import com.example.movies.domain.models.Movie;
import com.example.movies.domain.models.MovieQuery;
import com.example.movies.presentation.details.DetailActivity;
import com.example.movies.utils.JSONUtils;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;
import dagger.hilt.android.WithFragmentBindings;

@WithFragmentBindings
@AndroidEntryPoint
public class PopularMovieFragment extends Fragment {
    private MovieAdapter movieAdapter;
    private List<Movie> movies;
    private int page = 1;
    private MovieQuery movieQuery;
    private MovieViewModel viewModel;
    public MovieRepository movieRepository;
    private FragmentPopularMovieBinding binding;


    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentPopularMovieBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view,
                              @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(this).get(MovieViewModel.class);
        initRecyclerView();
        observeData();
        addOnScrollListener();
    }

    private void loadNext() {
        System.out.println("PopularMovieFragment.loadNext");
//        movieViewModel.initMovie(movieRepository, true, ++page);
//        movieViewModel.nextPage();
//        movieViewModel.getMovieData(true, page).observe(getViewLifecycleOwner(), movieResponse -> {
//            movies = movieResponse.getMovieList();
//            System.out.println("movies.size() = " + movies.size());
//            movieAdapter.addMovies(movies);
//        });
    }

    public void addOnScrollListener() {
        LinearLayoutManager layoutManager = (LinearLayoutManager) binding.movieList.recyclerViewMain.getLayoutManager();
        binding.movieList.recyclerViewMain.addOnScrollListener(
                new EndlessRecyclerOnScrollListener(layoutManager) {
                    @Override
                    public void onLoadMore(int current_page) {
                        System.out.println("current_page = " + current_page);
                        loadNext();
                    }
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

    private void observeData() {
        viewModel.getMovieList().observe(getViewLifecycleOwner(),
                movies -> movieAdapter.setMovies(movies)
        );
    }

    private void initRecyclerView() {
        movieAdapter = new MovieAdapter();
        binding.movieList.textViewTitleList.setText("Популярні фільми");
        binding.movieList.recyclerViewMain.setLayoutManager(new GridLayoutManager(getContext(), 2));
        binding.movieList.recyclerViewMain.setAdapter(movieAdapter);
        movieAdapter.setOnPosterClickListener(this::onPosterClick);
    }

    private int getColumnCount() {
//        DisplayMetrics displayMetrics = new DisplayMetrics();
//        this.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
//        int width = (int) (displayMetrics.widthPixels / displayMetrics.density);
//        return Math.max(width / 260, 2);
        return 0;
    }
}