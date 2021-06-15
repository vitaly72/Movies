package com.example.movies.presentation.movie;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movies.R;
import com.example.movies.domain.models.Movie;
import com.example.movies.utils.Constants;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {
    private OnPosterClickListener onPosterClickListener;
    private List<Movie> movies;

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_item, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        String url = Constants.BASE.POSTER_URL
                + Constants.POSTER_SIZE.SMALL
                + movies.get(position).getPosterPath();
        holder.textViewTitle.setText(movies.get(position).getTitle());
        Picasso.get().load(url).into(holder.imageViewSmallPoster);
    }

    @Override
    public int getItemCount() {
        return movies == null ? 0 : movies.size();
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder {

        private final ImageView imageViewSmallPoster;
        private final TextView textViewTitle;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewSmallPoster = itemView.findViewById(R.id.imageViewSmallPoster);
            textViewTitle = itemView.findViewById(R.id.textViewTitle);

            itemView.setOnClickListener(v -> {
                if (onPosterClickListener != null) {
                    onPosterClickListener.onPosterClick(getAbsoluteAdapterPosition());
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
        for(Movie movie : this.movies) {
            System.out.println("movie.getTitle() = " + movie.getTitle());
        }
    }

    public void clear() {
        this.movies.clear();
        notifyDataSetChanged();
    }

    public List<Movie> getMovies() {
        return movies;
    }

    public interface OnPosterClickListener {
        void onPosterClick(int position);
    }

    public void setOnPosterClickListener(OnPosterClickListener onPosterClickListener) {
        this.onPosterClickListener = onPosterClickListener;
    }
}
