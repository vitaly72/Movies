package com.example.movies.presentation.movie.favorite;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;

import com.example.movies.R;
import com.example.movies.databinding.FragmentFavoriteMovieBinding;
import com.example.movies.domain.models.Movie;
import com.example.movies.presentation.details.DetailActivity;
import com.example.movies.presentation.movie.MovieAdapter;
import com.example.movies.utils.JSONUtils;

import org.jetbrains.annotations.NotNull;

import dagger.hilt.android.AndroidEntryPoint;
import dagger.hilt.android.WithFragmentBindings;

@WithFragmentBindings
@AndroidEntryPoint
public class FavoriteMovieFragment extends Fragment {
    private MovieAdapter movieAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentFavoriteMovieBinding binding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_favorite_movie,
                container,
                false);
        View view = binding.getRoot();
        binding.movieList.textViewTitleList.setText("Улюблені фільми");

        FavoriteMoviesViewModel viewModel = ViewModelProviders.of(this)
                .get(FavoriteMoviesViewModel.class);
        movieAdapter = new MovieAdapter();

        binding.movieList.recyclerViewMain.setLayoutManager(new GridLayoutManager(view.getContext(), 2));
        binding.movieList.recyclerViewMain.setAdapter(movieAdapter);

        viewModel.getFavouriteMovies().observe(getViewLifecycleOwner(), observer ->
                movieAdapter.setMovies(observer));

        movieAdapter.setOnPosterClickListener(this::onPosterClick);

        return view;
    }

    public void onPosterClick(int position) {
        Intent intent = new Intent(getActivity(), DetailActivity.class);
        Movie movie = movieAdapter.getMovies().get(position);
        String movieJsonString = JSONUtils.getGsonParser().toJson(movie);
        intent.putExtra("id", movieJsonString);
        startActivity(intent);
    }
}