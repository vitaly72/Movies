package com.example.movies.domain.models;

public class MovieQuery {
    private String apiKey;
    private String language;
    private String sortBy;
    private String voteCount;
    private String page;

    public MovieQuery(String apiKey, String language, String sortBy, String voteCount, String page) {
        this.apiKey = apiKey;
        this.language = language;
        this.sortBy = sortBy;
        this.voteCount = voteCount;
        this.page = page;
    }

    public String getApiKey() {
        return apiKey;
    }

    public String getLanguage() {
        return language;
    }

    public String getSortBy() {
        return sortBy;
    }

    public String getVoteCount() {
        return voteCount;
    }

    public String getPage() {
        return page;
    }
}
