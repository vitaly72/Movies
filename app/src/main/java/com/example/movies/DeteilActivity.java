package com.example.movies;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.movies.adapters.ReviewAdapter;
import com.example.movies.adapters.TrailerAdapter;
import com.example.movies.data.FavouriteMovie;
import com.example.movies.data.MainViewModel;
import com.example.movies.data.Movie;
import com.example.movies.data.MovieDatabase;
import com.example.movies.data.Review;
import com.example.movies.data.Trailer;
import com.example.movies.utils.JSONUtils;
import com.example.movies.utils.NetworkUtils;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.ArrayList;

public class DeteilActivity extends AppCompatActivity {
    private ImageView imageViewAddToFavourite;
    private ImageView imageViewBigPoster;
    private TextView textViewTitle;
    private TextView textViewOriginalTitle;
    private TextView textViewRating;
    private TextView textViewReleaseDate;
    private TextView textViewOverview;


    MainViewModel mainViewModel;

    private int id;
    private Movie movie;
    private FavouriteMovie favouriteMovie;


    private RecyclerView recyclerViewTrailers;
    private TrailerAdapter trailerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deteil);
        imageViewBigPoster=findViewById(R.id.imageViewBigPoster);
        textViewTitle=findViewById(R.id.textViewTitle);
        textViewOriginalTitle=findViewById(R.id.textViewOtiginalTitle);
        textViewRating=findViewById(R.id.textViewRaiting);
        textViewReleaseDate=findViewById(R.id.textViewReleaseData);
        textViewOverview=findViewById(R.id.textViewOverview);
        imageViewAddToFavourite=findViewById(R.id.imageViewAddToFavourite);
        Intent intent=getIntent();
        if (intent!=null && intent.hasExtra("id")){
            id=intent.getIntExtra("id",-1);
        }else finish();

        mainViewModel= ViewModelProviders.of(this).get(MainViewModel.class);
        movie=mainViewModel.getMovieByID(id);
        Picasso.get().load(movie.getBigPosterPath()).into(imageViewBigPoster);
        textViewTitle.setText(movie.getTitle());
        textViewOriginalTitle.setText(movie.getOriginalTitle());
        textViewOverview.setText(movie.getOverview());
        textViewRating.setText(Double.toString(movie.getVoteAverage()));
        textViewReleaseDate.setText(movie.getReleaseDate());
        setFavourite();
        recyclerViewTrailers=findViewById(R.id.recyle_viewTrailers);
        trailerAdapter=new TrailerAdapter();
        recyclerViewTrailers.setLayoutManager(new LinearLayoutManager(this));
        JSONObject jsonObjectTrailers= NetworkUtils.getJSONForVideos(movie.getId());
        ArrayList<Trailer> trailers= JSONUtils.getTrailrsFromJSON(jsonObjectTrailers);
        trailerAdapter.setTrailers(trailers);
        recyclerViewTrailers.setAdapter(trailerAdapter);
        trailerAdapter.setSetClickOnVideo(new TrailerAdapter.SetClickOnVideo() {
            @Override
            public void clickOnVideo(String url) {
                 Intent intentToTrailer=new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                 startActivity(intentToTrailer);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.main_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();
        switch (id){
            case R.id.itemMain:
                Intent intent=new Intent(this,MainActivity.class);
                startActivity(intent);
                break;

            case R.id.itemFavourite:
                Intent intentToFavourite=new Intent(this, FavoritActivity.class);
                startActivity(intentToFavourite);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onCkickChangeFavourite(View view) {

        if (favouriteMovie==null){
            mainViewModel.insertFavouriteMovie(new FavouriteMovie(movie));
            Toast.makeText(this, R.string.add_to_favourite, Toast.LENGTH_SHORT).show();
        }else {
            mainViewModel.deleteFavouriteMovie(favouriteMovie);
            Toast.makeText(this, R.string.delete_from_favourite, Toast.LENGTH_SHORT).show();
        }
        setFavourite();
    }

    private void setFavourite(){
        favouriteMovie=mainViewModel.getFavouriteMovieByID(id);
        if (favouriteMovie==null){
            imageViewAddToFavourite.setImageResource(R.drawable.favourite_add_to);
        }else {
            imageViewAddToFavourite.setImageResource(R.drawable.favourite_remove);

        }
    }


}
