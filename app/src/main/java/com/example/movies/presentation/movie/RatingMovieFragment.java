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
import com.example.movies.databinding.FragmentRatingMovieBinding;
import com.example.movies.domain.models.Movie;
import com.example.movies.domain.models.MovieQuery;
import com.example.movies.presentation.details.DetailActivity;
import com.example.movies.utils.Constants;
import com.example.movies.utils.JSONUtils;

import org.jetbrains.annotations.NotNull;

import dagger.hilt.android.AndroidEntryPoint;
import dagger.hilt.android.WithFragmentBindings;

@WithFragmentBindings
@AndroidEntryPoint
public class RatingMovieFragment extends Fragment {
    private MovieAdapter movieAdapter;
    private int page = 1;
    public MovieRepository movieRepository;
    private FragmentRatingMovieBinding binding;
    private MovieViewModel viewModel;
    private MovieQuery movieQuery;

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentRatingMovieBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view,
                              @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(this).get(MovieViewModel.class);
        movieQuery = new MovieQuery(Constants.PARAMS.VALUE.SORT_BY_TOP_RATED, page);
        initRecyclerView();
        observeData();
        addOnScrollListener();
        viewModel.getMovies(movieQuery);
    }

    private void loadNext() {
        viewModel.getMovies(movieQuery);
        viewModel.getMovieList().observe(getViewLifecycleOwner(),
                movies -> movieAdapter.addMovies(movies)
        );
    }

    public void onPosterClick(int position) {
        Intent intent = new Intent(getActivity(), DetailActivity.class);
        Movie movie = movieAdapter.getMovies().get(position);
        String movieJsonString = JSONUtils.getGsonParser().toJson(movie);
        intent.putExtra("id", movieJsonString);
        startActivity(intent);
    }

    public void addOnScrollListener() {
        LinearLayoutManager layoutManager = (LinearLayoutManager) binding.movieList.recyclerViewMain.getLayoutManager();
        binding.movieList.recyclerViewMain.addOnScrollListener(
                new EndlessRecyclerOnScrollListener(layoutManager) {
                    @Override
                    public void onLoadMore(int current_page) {
                        loadNext();
                    }
                }
        );
    }

    private void observeData() {
        viewModel.getMovieList().observe(getViewLifecycleOwner(),
                movies -> movieAdapter.setMovies(movies)
        );
    }

    private void initRecyclerView() {
        movieAdapter = new MovieAdapter();
        binding.movieList.textViewTitleList.setText("Рейтингові фільми");
        binding.movieList.recyclerViewMain.setLayoutManager(new GridLayoutManager(getContext(), 2));
        binding.movieList.recyclerViewMain.setAdapter(movieAdapter);
        movieAdapter.setOnPosterClickListener(this::onPosterClick);
    }
}