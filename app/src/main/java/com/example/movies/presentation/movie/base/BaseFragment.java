package com.example.movies.presentation.movie.base;

import android.app.Activity;
import android.content.Intent;
import android.util.DisplayMetrics;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movies.domain.models.Movie;
import com.example.movies.presentation.details.DetailActivity;
import com.example.movies.presentation.movie.MovieAdapter;
import com.example.movies.utils.JSONUtils;

public class BaseFragment extends Fragment {
    public void onPosterClick(MovieAdapter movieAdapter, int position) {
        Intent intent = new Intent(getActivity(), DetailActivity.class);
        Movie movie = movieAdapter.getMovies().get(position);

        String movieJsonString = JSONUtils.getGsonParser().toJson(movie);
        intent.putExtra("id", movieJsonString);
        startActivity(intent);
    }

    private int getColumnCount(Activity activity) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = (int) (displayMetrics.widthPixels / displayMetrics.density);
        return Math.max(width / 260, 2);
    }

    public MovieAdapter initRecyclerViews(TextView textView, RecyclerView recyclerView) {
        MovieAdapter movieAdapter = new MovieAdapter();
        textView.setText("Популярні фільми");
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        recyclerView.setAdapter(movieAdapter);

        return movieAdapter;
    }
}
