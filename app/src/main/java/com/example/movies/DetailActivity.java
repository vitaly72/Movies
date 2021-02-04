package com.example.movies;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.movies.adapters.TrailerAdapter;
import com.example.movies.data.FavouriteMovie;
import com.example.movies.data.MainViewModel;
import com.example.movies.data.Movie;
import com.example.movies.data.Trailer;
import com.example.movies.databinding.ActivityDeteilBinding;
import com.example.movies.utils.JSONUtils;
import com.example.movies.utils.NetworkUtils;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity {
    private int id;
    private MainViewModel mainViewModel;
    private Movie movie;
    private FavouriteMovie favouriteMovie;
    private ActivityDeteilBinding detailBinding;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        detailBinding = DataBindingUtil.setContentView(this, R.layout.activity_deteil);
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("id")) {
            id = intent.getIntExtra("id", -1);
        } else finish();

        mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        movie = mainViewModel.getMovieByID(id);

        Picasso.get().load(movie.getBigPosterPath()).into(detailBinding.imageViewBigPoster);
        detailBinding.setMovie(movie);
        setFavourite();

        TrailerAdapter trailerAdapter = new TrailerAdapter();
        detailBinding.recyclerViewTrailers.setLayoutManager(new LinearLayoutManager(this));
        JSONObject jsonObjectTrailers = NetworkUtils.getJSONForVideos(movie.getId());
        ArrayList<Trailer> trailers = JSONUtils.getTrailersFromJSON(jsonObjectTrailers);
        trailerAdapter.setTrailers(trailers);
        detailBinding.recyclerViewTrailers.setAdapter(trailerAdapter);
        trailerAdapter.setSetClickOnVideo(url -> {
            Intent intentToTrailer = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(intentToTrailer);
        });
    }

    public void onClickChangeFavourite(View view) {
        if (favouriteMovie == null) {
            mainViewModel.insertFavouriteMovie(new FavouriteMovie(movie));
        } else {
            mainViewModel.deleteFavouriteMovie(favouriteMovie);
        }
        setFavourite();
    }

    private void setFavourite() {
        favouriteMovie = mainViewModel.getFavouriteMovieByID(id);
        if (favouriteMovie == null) {
            detailBinding.imageViewAddToFavourite.setImageResource(R.drawable.favourite_add_to);
        } else {
            detailBinding.imageViewAddToFavourite.setImageResource(R.drawable.favourite_remove);
        }
    }
}
