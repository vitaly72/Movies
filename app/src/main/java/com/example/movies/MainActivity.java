package com.example.movies;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProviders;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.GridLayoutManager;

import com.example.movies.adapters.MovieAdapter;
import com.example.movies.data.MainViewModel;
import com.example.movies.data.Movie;
import com.example.movies.databinding.ActivityMainBinding;
import com.example.movies.utils.JSONUtils;
import com.example.movies.utils.NetworkUtils;

import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<JSONObject> {
    private MovieAdapter movieAdapter;
    ActivityMainBinding mainBinding;

    private MainViewModel mainViewModel;

    private static final int LOADER_ID = -1;
    private LoaderManager loaderManager;

    private static int page = 1;
    private static int methodOfSort;
    private static boolean isLoading = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        loaderManager = LoaderManager.getInstance(this);
        mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);

        mainBinding.recyclerViewMain.setLayoutManager(new GridLayoutManager(this, getColumnCount()));
        movieAdapter = new MovieAdapter();
        mainBinding.recyclerViewMain.setAdapter(movieAdapter);
        mainBinding.switchSort.setChecked(true);
        mainBinding.switchSort.setOnCheckedChangeListener((buttonView, isChecked) -> {
            page = 1;
            setMethodOfSort(isChecked);
        });
        mainBinding.switchSort.setChecked(false);
        movieAdapter.setOnPosterClickListener(position -> {
            Intent intent = new Intent(MainActivity.this, DetailActivity.class);
            Movie movie = movieAdapter.getMovies().get(position);
            intent.putExtra("id", movie.getId());
            startActivity(intent);
        });

        movieAdapter.setOnReachEndListener(() -> {
            if (!isLoading) downLoadData(methodOfSort, page);
        });

        LiveData<List<Movie>> moviesFromLiveData = mainViewModel.getMovies();
        moviesFromLiveData.observe(this, movies -> {
            if (page == 1) movieAdapter.setMovies(movies);
        });

        mainBinding.floatingActionButtonFavorite.setOnClickListener(v -> {
            startActivity(new Intent(this, FavoriteActivity.class));
        });
    }

    private int getColumnCount() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = (int) (displayMetrics.widthPixels / displayMetrics.density);
        return Math.max(width / 260, 2);
    }

    public void onClickSetPopularity(View view) {
        setMethodOfSort(false);
        mainBinding.switchSort.setChecked(false);
        mainBinding.switchSort.setOnCheckedChangeListener((buttonView, isChecked) -> setMethodOfSort(isChecked));
    }

    public void onClickSetTopRated(View view) {
        setMethodOfSort(true);
        mainBinding.switchSort.setChecked(true);
    }

    private void setMethodOfSort(boolean isTopRated) {
        if (isTopRated) {
            methodOfSort = NetworkUtils.TOP_RATED;
            mainBinding.textViewTopRated.setTextColor(getResources().getColor(R.color.colorAccent));
            mainBinding.textViewPopularity.setTextColor(getResources().getColor(R.color.white_color));
        } else {
            methodOfSort = NetworkUtils.POPULARITY;
            mainBinding.textViewPopularity.setTextColor(getResources().getColor(R.color.colorAccent));
            mainBinding.textViewTopRated.setTextColor(getResources().getColor(R.color.white_color));
        }
        downLoadData(methodOfSort, page);
    }

    private void downLoadData(int methodOfSort, int page) {
        URL url = NetworkUtils.buildURL(methodOfSort, page);
        Bundle bundle = new Bundle();
        bundle.putString("url", url.toString());
        loaderManager.restartLoader(LOADER_ID, bundle, this);
    }

    @NonNull
    @Override
    public Loader<JSONObject> onCreateLoader(int id, @Nullable Bundle args) {
        NetworkUtils.JSONLoader jsonLoader = new NetworkUtils.JSONLoader(this, args);
        jsonLoader.setOnStartLoadingListener(() -> {
            isLoading = true;
            mainBinding.progressBarLoading.setVisibility(View.VISIBLE);
        });
        return jsonLoader;
    }

    @Override
    public void onLoadFinished(@NonNull Loader<JSONObject> loader, JSONObject data) {
        ArrayList<Movie> movies = JSONUtils.getMoviesFromJSON(data);
        if (!movies.isEmpty()) {
            if (page == 1) {
                mainViewModel.deleteAllMovies();
                movieAdapter.clear();
            }
            for (Movie movie : movies) {
                mainViewModel.insertMovie(movie);
            }
            movieAdapter.addMovies(movies);
            page++;
        }
        isLoading = false;
        mainBinding.progressBarLoading.setVisibility(View.INVISIBLE);
        loaderManager.destroyLoader(LOADER_ID);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<JSONObject> loader) {}
}
