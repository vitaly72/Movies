package com.example.movies.presentation.details;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.movies.R;
import com.example.movies.databinding.ActivityDetailBinding;
import com.example.movies.domain.models.Movie;
import com.example.movies.presentation.movie.FavoriteMoviesViewModel;
import com.example.movies.presentation.movie.MovieViewModel;
import com.example.movies.utils.Constants;
import com.example.movies.utils.JSONUtils;
import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {
    private Movie movie;
    private ActivityDetailBinding detailBinding;
    private FavoriteMoviesViewModel favoriteMoviesViewModel;
    private boolean isFavoriteMovie = false;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        detailBinding = DataBindingUtil.setContentView(this, R.layout.activity_detail);
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("id")) {
            String movieJsonString = intent.getStringExtra("id");
            movie = JSONUtils.getGsonParser().fromJson(movieJsonString, Movie.class);
        } else finish();
        VideoAdapter videoAdapter = new VideoAdapter();
        detailBinding.recyclerViewTrailers.setLayoutManager(new LinearLayoutManager(this));
        detailBinding.recyclerViewTrailers.setAdapter(videoAdapter);

        favoriteMoviesViewModel = ViewModelProviders.of(this).get(FavoriteMoviesViewModel.class);
        MovieViewModel movieViewModel = ViewModelProviders.of(this).get(MovieViewModel.class);
        movieViewModel.initVideo(movie.getId());
        movieViewModel.getVideoData().observe(this, videoResponse ->
                videoAdapter.setVideos(videoResponse.getVideoList()));

        String urlImage = Constants.BASE.POSTER_URL + Constants.POSTER_SIZE.BIG + movie.getPosterPath();
        Picasso.get().load(urlImage).into(detailBinding.imageViewBigPoster);
        detailBinding.setMovie(movie);
        setFavourite();

        videoAdapter.setOnVideoClickListener(url -> {
            Intent intentToTrailer = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(intentToTrailer);
        });
    }

    public void onClickChangeFavourite(View view) {
        System.out.println("DetailActivity.onClickChangeFavourite " + isFavoriteMovie);
        if (isFavoriteMovie) {
            favoriteMoviesViewModel.deleteFavouriteMovie(movie);
        } else {
            favoriteMoviesViewModel.insertFavouriteMovie(movie);
        }
        setFavourite();
    }

    private void setFavourite() {
        System.out.println("DetailActivity.setFavourite");
        Movie favouriteMovie = favoriteMoviesViewModel.getFavouriteMovieByID(movie.getId());
        if (favouriteMovie == null) {
            isFavoriteMovie = false;
            detailBinding.imageViewAddToFavourite.setImageResource(R.drawable.ic_baseline_star_rate_gray_24);
        } else {
            isFavoriteMovie = true;
            detailBinding.imageViewAddToFavourite.setImageResource(R.drawable.ic_baseline_star_rate_yellow_24);
        }
    }
}
