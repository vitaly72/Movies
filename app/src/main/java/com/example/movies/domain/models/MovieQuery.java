package com.example.movies.domain.models;

import com.example.movies.utils.Constants;

import java.util.HashMap;

public class MovieQuery extends HashMap<String, String> {
    public MovieQuery(String sortBy, int page) {
        put("api_key", Constants.PARAMS.VALUE.API_KEY);
        put("language", Constants.PARAMS.VALUE.LANGUAGE_VALUE);
        put("sort_by", sortBy);
        put("vote_count.gte", Constants.PARAMS.VALUE.MIN_VOTE_COUNT_VALUE);
        put("page", Integer.toString(page));
    }

    public void nextPage(int page) {
        put("page", Integer.toString(page));
    }
}
