package com.example.movies.presentation.movie.favorite;

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

import com.example.movies.R;
import com.example.movies.databinding.FragmentFavoriteMovieBinding;
import com.example.movies.domain.models.Movie;
import com.example.movies.presentation.details.DetailActivity;
import com.example.movies.presentation.movie.MovieAdapter;
import com.example.movies.presentation.movie.base.IFragment;
import com.example.movies.utils.JSONUtils;

import org.jetbrains.annotations.NotNull;

import dagger.hilt.android.AndroidEntryPoint;
import dagger.hilt.android.WithFragmentBindings;

@WithFragmentBindings
@AndroidEntryPoint
public class FavoriteMovieFragment extends Fragment implements IFragment {
    private MovieAdapter movieAdapter;
    private FavoriteMoviesViewModel viewModel;
    private FragmentFavoriteMovieBinding binding;

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFavoriteMovieBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view,
                              @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this).get(FavoriteMoviesViewModel.class);
        initRecyclerView();
        observeData();
    }

    @Override
    public void loadNext() {
    }

    public void onPosterClick(int position) {
        Intent intent = new Intent(getActivity(), DetailActivity.class);
        Movie movie = movieAdapter.getMovies().get(position);
        String movieJsonString = JSONUtils.getGsonParser().toJson(movie);
        intent.putExtra("id", movieJsonString);
        startActivity(intent);
    }

    @Override
    public void addOnScrollListener() {
    }

    @Override
    public void observeData() {
        viewModel.getFavouriteMovies().observe(getViewLifecycleOwner(),
                observer -> movieAdapter.setMovies(observer)
        );
    }

    @Override
    public void initRecyclerView() {
        binding.movieList.textViewTitleList.setText(getResources().getString(R.string.favorite));
        movieAdapter = new MovieAdapter();
        binding.movieList.recyclerViewMain.setLayoutManager(new GridLayoutManager(getContext(), 2));
        binding.movieList.recyclerViewMain.setAdapter(movieAdapter);
        movieAdapter.setOnPosterClickListener(this::onPosterClick);
    }
}