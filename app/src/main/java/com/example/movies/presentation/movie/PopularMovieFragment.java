package com.example.movies.presentation.movie;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.movies.R;
import com.example.movies.databinding.FragmentPopularMovieBinding;
import com.example.movies.domain.models.Movie;
import com.example.movies.presentation.details.DetailActivity;
import com.example.movies.presentation.movie.EndlessRecyclerOnScrollListener;
import com.example.movies.presentation.movie.MovieAdapter;
import com.example.movies.presentation.movie.MovieViewModel;
import com.example.movies.utils.JSONUtils;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class PopularMovieFragment extends Fragment {
    private MovieAdapter movieAdapter;
    private List<Movie> movies;
    private int page = 1;
    private MovieViewModel movieViewModel;
    private boolean mIsLoading, mIsLastPage;

    final int pageSize = 20;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentPopularMovieBinding binding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_popular_movie,
                container,
                false);
        View view = binding.getRoot();

        movieAdapter = new MovieAdapter();
        binding.movieList.textViewTitleList.setText("Популярні фільми");
        binding.movieList.recyclerViewMain.setLayoutManager(new GridLayoutManager(view.getContext(), 2));

        movieViewModel = ViewModelProviders.of(this).get(MovieViewModel.class);
//        movieViewModel.init();
//        movieViewModel.moviePagedList.observe(getViewLifecycleOwner(), movies -> {
//            System.out.println("movies.size() = " + movies.size());
//            movieAdapter.submitList(movies);
//        });
        binding.movieList.recyclerViewMain.setAdapter(movieAdapter);

        movieViewModel.initMovie(true, page);
        movieViewModel.getMovieData().observe(getViewLifecycleOwner(), movieResponse -> {
            movies = movieResponse.getMovieList();
            System.out.println("movies.size() = " + movies.size());
            movieAdapter.setMovies(movies);
        });

        LinearLayoutManager layoutManager = (LinearLayoutManager) binding.movieList.recyclerViewMain.getLayoutManager();
        binding.movieList.recyclerViewMain.addOnScrollListener(
                new EndlessRecyclerOnScrollListener(layoutManager) {
                    @Override
                    public void onLoadMore(int current_page) {
                        System.out.println("current_page = " + current_page);
                        loadNext();
                    }
                });

        movieAdapter.setOnPosterClickListener(this::onPosterClick);

        return view;
    }

    private void loadNext(){
        movieViewModel.initMovie(true, ++page);
        movieViewModel.getMovieData().observe(getViewLifecycleOwner(), movieResponse -> {
            movies = movieResponse.getMovieList();
            System.out.println("movies.size() = " + movies.size());
            movieAdapter.addMovies(movies);
        });
    }

//    private void loadMoreItems(boolean isFirstPage) {
//        mIsLoading = true;
//        page++;
//
//        // update recycler adapter with next page
//        movieViewModel.initMovie(true, page);
//        NetworkService.createService().movie(
//                Constants.PARAMS.VALUE.API_KEY,
//                Constants.PARAMS.VALUE.LANGUAGE_VALUE,
//                Constants.PARAMS.VALUE.SORT_BY_POPULARITY,
//                Constants.PARAMS.VALUE.MIN_VOTE_COUNT_VALUE,
//                Integer.toString(page)
//        ).enqueue(new Callback<PagedList<MovieResponse>>() {
//            @Override
//            public void onResponse(Call<PagedList<MovieResponse>> call, Response<PagedList<MovieResponse>> response) {
//                PagedList<MovieResponse> result = response.body();
//
//                if (result == null) return;
//                else if (!isFirstPage) movieAdapter.addMovies(result.getResults().get(0).getMovieList());
//                else movieAdapter.setMovies(result.getResults().get(0).getMovieList());
//
//                mIsLoading = false;
//            }
//
//            @Override
//            public void onFailure(Call<PagedList<MovieResponse>> call, Throwable t) {
//
//            }
//        });
//    }

    public void onPosterClick(int position) {
        Intent intent = new Intent(getActivity(), DetailActivity.class);
        Movie movie = movieAdapter.getMovies().get(position);
        String movieJsonString = JSONUtils.getGsonParser().toJson(movie);
        System.out.println("movieJsonString = " + movieJsonString);
        intent.putExtra("id", movieJsonString);
        startActivity(intent);
    }

    private int getColumnCount() {
//        DisplayMetrics displayMetrics = new DisplayMetrics();
//        this.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
//        int width = (int) (displayMetrics.widthPixels / displayMetrics.density);
//        return Math.max(width / 260, 2);
        return 0;
    }
}