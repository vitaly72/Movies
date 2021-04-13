package com.example.movies.presentation.details;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.movies.R;
import com.example.movies.presentation.details.VideoAdapter;
import com.example.movies.domain.models.FavouriteMovie;
import com.example.movies.presentation.movie.MainViewModel;
import com.example.movies.domain.Movie;
import com.example.movies.domain.models.Video;
import com.example.movies.databinding.ActivityDetailBinding;
import com.example.movies.utils.Constants;
import com.example.movies.utils.JSONUtils;
import com.example.movies.presentation.movie.MovieViewModel;
import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {
    private int id;
    private MainViewModel mainViewModel;
    private MovieViewModel movieViewModel;
    private Movie movie;
    private FavouriteMovie favouriteMovie;
    private ActivityDetailBinding detailBinding;

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

        movieViewModel = ViewModelProviders.of(this).get(MovieViewModel.class);
        movieViewModel.initVideo(movie.getId());
        movieViewModel.getVideoData().observe(this, videoResponse -> {
            videoAdapter.setVideos(videoResponse.getVideoList());
            for (Video item: videoAdapter.getVideos()) {
//            for (Video item: videoResponse.getVideoList()) {
                System.out.println("item.getName() = " + item.getName());
            }
        });

//        mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
//        movie = mainViewModel.getMovieByID(id);

        String urlImage = Constants.BASE.POSTER_URL + Constants.POSTER_SIZE.BIG + movie.getPosterPath();
        System.out.println("urlImage = " + urlImage);
        Picasso.get().load(urlImage).into(detailBinding.imageViewBigPoster);
        detailBinding.setMovie(movie);

//        setFavourite();
//        videoAdapter.setTrailers();
//        JSONObject jsonObjectTrailers = NetworkUtils.getJSONForVideos(movie.getId());
//        ArrayList<Video> trailers = JSONUtils.getTrailersFromJSON(jsonObjectTrailers);
        videoAdapter.setOnVideoClickListener(url -> {
//            Intent intentToTrailer = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
//            startActivity(intentToTrailer);
        });
    }

    public void onClickChangeFavourite(View view) {
//        if (favouriteMovie == null) {
//            mainViewModel.insertFavouriteMovie(new FavouriteMovie(movie));
//        } else {
//            mainViewModel.deleteFavouriteMovie(favouriteMovie);
//        }
//        setFavourite();
    }

    private void setFavourite() {
        favouriteMovie = mainViewModel.getFavouriteMovieByID(id);
        if (favouriteMovie == null) {
            detailBinding.imageViewAddToFavourite.setImageResource(R.drawable.ic_baseline_star_rate_gray_24);
        } else {
            detailBinding.imageViewAddToFavourite.setImageResource(R.drawable.ic_baseline_star_rate_yellow_24);
        }
    }
}
