package com.example.movies.data;

import androidx.databinding.BaseObservable;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "movies2")
public class Movie extends BaseObservable {
    @PrimaryKey(autoGenerate = true)
    private int uniqueId;
    private int id;
    private int vote_count;
    private String title;
    private String originalTitle;
    private String overview;
    private String posterPath;
    private String bigPosterPath;
    private String backdropPath;
    private double voteAverage;
    private String releaseDate;

    public Movie(int uniqueId, int id, int vote_count, String title, String originalTitle,
                 String overview, String posterPath, String bigPosterPath, String backdropPath,
                 double voteAverage, String releaseDate) {
        this.uniqueId = uniqueId;
        this.id = id;
        this.vote_count = vote_count;
        this.title = title;
        this.originalTitle = originalTitle;
        this.overview = overview;
        this.posterPath = posterPath;
        this.bigPosterPath = bigPosterPath;
        this.backdropPath = backdropPath;
        this.voteAverage = voteAverage;
        this.releaseDate = releaseDate;
    }

    @Ignore
    public Movie(int id, int vote_count, String title, String originalTitle, String overview,
                 String posterPath, String bigPosterPath, String backdropPath, double voteAverage,
                 String releaseDate) {
        this.id = id;
        this.vote_count = vote_count;
        this.title = title;
        this.originalTitle = originalTitle;
        this.overview = overview;
        this.posterPath = posterPath;
        this.bigPosterPath = bigPosterPath;
        this.backdropPath = backdropPath;
        this.voteAverage = voteAverage;
        this.releaseDate = releaseDate;
    }

    public int getUniqueId() {
        return uniqueId;
    }

    public String getBigPosterPath() {
        return bigPosterPath;
    }
    
    public int getId() {
        return id;
    }

    public int getVote_count() {
        return vote_count;
    }

    public String getTitle() {
        return title;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public String getPosterPath() {
        return posterPath;
    }
    
    public String getBackdropPath() {
        return backdropPath;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public String getReleaseDate() {
        return releaseDate;
    }
    
    public String getOverview() {
        return overview;
    }
}
