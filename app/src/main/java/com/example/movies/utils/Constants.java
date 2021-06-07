package com.example.movies.utils;

public final class Constants {
    public static final class BASE {
        public static final String URL = "https://api.themoviedb.org/3/";
        public static final String POSTER_URL = "https://image.tmdb.org/t/p/";
        public static final String YOUTUBE_URL = "https://www.youtube.com/watch?v=";
    }

    public static final class PARAMS {
        public static final class VALUE {
            public static final String API_KEY = "0b8c6374b7229947ab2f764ffc203052";
            public static final String LANGUAGE_VALUE = "en-US";
            public static final String SORT_BY_POPULARITY = "popularity.desc";
            public static final String SORT_BY_TOP_RATED = "vote_average.desc";
            public static final String MIN_VOTE_COUNT_VALUE = "1000";
        }
    }

    public static final class POSTER_SIZE {
        public static final String SMALL = "w185";
        public static final String BIG = "w780";
    }
}
