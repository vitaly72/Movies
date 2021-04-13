package com.example.movies.presentation.movie;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movies.R;
import com.example.movies.domain.Movie;
import com.example.movies.utils.Constants;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {
    private OnPosterClickListener onPosterClickListener;
    Context context;
    List<Movie> movies;

    public MovieAdapter(Context context, List<Movie> movieList) {
        this.context = context;
        this.movies = movieList;
    }

    public MovieAdapter() {
        movies = new ArrayList<>();
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_item, parent, false);
        return new MovieViewHolder(view);
    }

    public interface OnPosterClickListener {
        void onPosterClick(int position);
    }

    public void setOnPosterClickListener(OnPosterClickListener onPosterClickListener) {
        this.onPosterClickListener = onPosterClickListener;
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        String url = Constants.BASE.POSTER_URL + Constants.POSTER_SIZE.SMALL
                + movies.get(position).getPosterPath();
//        System.out.println("getPosterPath() = " + url);
        Picasso.get().load(url).into(holder.imageViewSmallPoster);
    }

    @Override
    public int getItemCount() {
        return movies == null ? 0 : movies.size();
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder {
        private final ImageView imageViewSmallPoster;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewSmallPoster = itemView.findViewById(R.id.imageViewSmallPOster);
            itemView.setOnClickListener(v -> {
                if (onPosterClickListener != null) {
                    onPosterClickListener.onPosterClick(getAdapterPosition());
                }
            });
        }
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
        notifyDataSetChanged();
    }

    public void addMovies(List<Movie> movies) {
        this.movies.addAll(movies);
        notifyDataSetChanged();
    }

    public List<Movie> getMovies() {
        return movies;
    }

    public void clear() {
        this.movies.clear();
        notifyDataSetChanged();
    }
}
