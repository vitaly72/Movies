package com.example.movies.presentation.movie.base;

public interface IFragment {
    void loadNext();

    void onPosterClick(int position);

    void addOnScrollListener();

    void observeData();

    void initRecyclerView();
}
