package com.example.movies;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movies.adapters.MovieAdapter;
import com.example.movies.data.FavouriteMovie;
import com.example.movies.data.MainViewModel;
import com.example.movies.data.Movie;

import java.util.ArrayList;
import java.util.List;

public class FavoriteActivity extends AppCompatActivity {
    private RecyclerView recyclerViewFavouriteMovies;
    private MovieAdapter adapter;
    private MainViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorit);
        recyclerViewFavouriteMovies = findViewById(R.id.recyclerViewFavouriteMovies);
        recyclerViewFavouriteMovies.setLayoutManager(new GridLayoutManager(this, 2));
        adapter = new MovieAdapter();
        recyclerViewFavouriteMovies.setAdapter(adapter);
        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        LiveData<List<FavouriteMovie>> favouriteMovies = viewModel.getFavouriteMovies();
        favouriteMovies.observe(this, new Observer<List<FavouriteMovie>>() {
            @Override
            public void onChanged(List<FavouriteMovie> favouriteMovies) {
                List<Movie> movies = new ArrayList<>();
                if (favouriteMovies != null) {
                    movies.addAll(favouriteMovies);
                    adapter.setMovies(movies);
                }

            }
        });
        adapter.setOnPosterClickListener(position -> {
            Intent intent = new Intent(FavoriteActivity.this, DetailActivity.class);
            Movie movie = adapter.getMovies().get(position);
            intent.putExtra("id", movie.getId());
            startActivity(intent);
        });
    }
}
