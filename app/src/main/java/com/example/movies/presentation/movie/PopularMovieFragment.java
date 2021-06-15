package com.example.movies.presentation.movie;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.movies.R;
import com.example.movies.databinding.FragmentPopularMovieBinding;
import com.example.movies.domain.models.MovieQuery;
import com.example.movies.presentation.movie.base.BaseFragment;
import com.example.movies.presentation.movie.base.IFragment;
import com.example.movies.utils.Constants;

import org.jetbrains.annotations.NotNull;

import dagger.hilt.android.AndroidEntryPoint;
import dagger.hilt.android.WithFragmentBindings;

@WithFragmentBindings
@AndroidEntryPoint
public class PopularMovieFragment extends BaseFragment implements IFragment {
    private MovieAdapter movieAdapter;
    private MovieQuery movieQuery;
    private MovieViewModel viewModel;
    private FragmentPopularMovieBinding binding;
    private int page = 1;

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
        movieQuery = new MovieQuery(Constants.PARAMS.VALUE.SORT_BY_POPULARITY, page);
        initRecyclerView();
        observeData();
        addOnScrollListener();
        viewModel.getMovies(movieQuery);
    }

    public void loadNext() {
        movieQuery.nextPage(++page);
        viewModel.getMovies(movieQuery);
        viewModel.getMovieList().observe(getViewLifecycleOwner(),
                movies -> movieAdapter.addMovies(movies)
        );
    }

    @Override
    public void onPosterClick(int position) {
        onPosterClick(movieAdapter, position);
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

    @Override
    public void onPosterClick(MovieAdapter movieAdapter, int position) {
        super.onPosterClick(movieAdapter, position);
    }

    @Override
    public void observeData() {
        viewModel.getMovieList().observe(getViewLifecycleOwner(),
                movies -> movieAdapter.setMovies(movies)
        );
    }

    @Override
    public void initRecyclerView() {
        movieAdapter = new MovieAdapter();
        binding.movieList.textViewTitleList.setText(getResources().getString(R.string.most_popularity));
        binding.movieList.recyclerViewMain.setLayoutManager(new GridLayoutManager(getContext(), 2));
        binding.movieList.recyclerViewMain.setAdapter(movieAdapter);
        movieAdapter.setOnPosterClickListener(this::onPosterClick);
    }
}