package com.example.movies.presentation.details;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.movies.R;
import com.example.movies.databinding.ActivityDetailBinding;
import com.example.movies.domain.models.Movie;
import com.example.movies.presentation.movie.favorite.FavoriteMoviesViewModel;
import com.example.movies.utils.Constants;
import com.example.movies.utils.JSONUtils;
import com.squareup.picasso.Picasso;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class DetailActivity extends AppCompatActivity {
    private Movie movie;
    private ActivityDetailBinding binding;
    private FavoriteMoviesViewModel favoriteMoviesViewModel;
    private VideoViewModel videoViewModel;
    private VideoAdapter videoAdapter;
    private boolean isFavoriteMovie = false;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail);
        movie = getMovieFromGson(getIntent());
        binding.setMovie(movie);

        favoriteMoviesViewModel = new ViewModelProvider(this).get(FavoriteMoviesViewModel.class);
        videoViewModel = new ViewModelProvider(this).get(VideoViewModel.class);

        initRecyclerView();
        loadImage();
        setFavourite();
        observeData();
    }

    public void loadImage() {
        String urlImage = Constants.BASE.POSTER_URL + Constants.POSTER_SIZE.BIG + movie.getPosterPath();
        Picasso.get().load(urlImage).into(binding.imageViewBigPoster);
    }

    public void observeData() {
        videoViewModel.getVideos(movie.getId());
        videoViewModel.getVideoList().observe(this, videoAdapter::setVideos);
    }

    public void initRecyclerView() {
        videoAdapter = new VideoAdapter();
        binding.recyclerViewTrailers.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerViewTrailers.setAdapter(videoAdapter);
        videoAdapter.setOnVideoClickListener(this::onVideoClickListener);
    }

    public void onVideoClickListener(String url) {
        Intent intentToTrailer = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(intentToTrailer);
    }

    public Movie getMovieFromGson(Intent intent) {
        String movieJsonString = intent.getStringExtra("id");

        return JSONUtils.getGsonParser().fromJson(movieJsonString, Movie.class);
    }

    public void onClickChangeFavourite(View view) {
        if (isFavoriteMovie) {
            favoriteMoviesViewModel.deleteFavouriteMovie(movie);
        } else {
            favoriteMoviesViewModel.insertFavouriteMovie(movie);
        }
        setFavourite();
    }

    private void setFavourite() {
        Movie favouriteMovie = favoriteMoviesViewModel.getFavouriteMovieByID(movie.getId());
        if (favouriteMovie == null) {
            isFavoriteMovie = false;
            binding.imageViewAddToFavourite.setImageResource(R.drawable.ic_baseline_star_rate_gray_24);
        } else {
            isFavoriteMovie = true;
            binding.imageViewAddToFavourite.setImageResource(R.drawable.ic_baseline_star_rate_yellow_24);
        }
    }
}
