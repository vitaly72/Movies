package com.example.movies.presentation.movie;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;

import com.example.movies.R;
import com.example.movies.databinding.ActivityMainBinding;
import com.example.movies.domain.Movie;
import com.example.movies.presentation.details.DetailActivity;
import com.example.movies.utils.JSONUtils;

import java.util.List;

public class MainActivity extends AppCompatActivity implements MovieAdapter.OnPosterClickListener {
    private MovieAdapter movieAdapter;
    private ActivityMainBinding mainBinding;
    private MovieViewModel movieViewModel;
    private List<Movie> movies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        movieAdapter = new MovieAdapter();
        mainBinding.recyclerViewMain.setLayoutManager(
                new GridLayoutManager(this, getColumnCount()));
        mainBinding.recyclerViewMain.setAdapter(movieAdapter);

        movieViewModel = ViewModelProviders.of(this).get(MovieViewModel.class);
        movieViewModel.initMovie();
        movieViewModel.getMovieData().observe(this, movieResponse -> {
            movies = movieResponse.getMovieList();
            movieAdapter.setMovies(movies);
        });

        movieAdapter.setOnPosterClickListener(this);
    }

    private int getColumnCount() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = (int) (displayMetrics.widthPixels / displayMetrics.density);
        return Math.max(width / 260, 2);
    }

    @Override
    public void onPosterClick(int position) {
        Intent intent = new Intent(MainActivity.this, DetailActivity.class);
        Movie movie = movieAdapter.getMovies().get(position);
        String movieJsonString = JSONUtils.getGsonParser().toJson(movie);
        intent.putExtra("id", movieJsonString);
        startActivity(intent);
    }
}
