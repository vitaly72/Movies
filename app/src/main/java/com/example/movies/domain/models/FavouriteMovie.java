package com.example.movies.domain.models;

import androidx.room.Entity;
import androidx.room.Ignore;

@Entity(tableName = "favourite_movies")
public class FavouriteMovie extends Movie {
    public FavouriteMovie(int uniqueId, int id, int vote_count, String title,
                          String originalTitle, String overview, String posterPath,
                          String bigPosterPath, String backdropPath,
                          double voteAverage, String releaseDate) {
        super(uniqueId, id, vote_count, title, originalTitle,
                overview, posterPath, bigPosterPath, backdropPath,
                voteAverage, releaseDate);
    }

    @Ignore
    public FavouriteMovie(Movie movie) {
        super(movie.getUniqueId(), movie.getId(), movie.getVote_count(),
                movie.getTitle(), movie.getOriginalTitle(), movie.getOverview(),
                movie.getPosterPath(), movie.getBigPosterPath(),
                movie.getBackdropPath(), movie.getVoteAverage(), movie.getReleaseDate());
    }
}
