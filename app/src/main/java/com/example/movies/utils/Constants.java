package com.example.movies.utils;

public final class Constants {
    public static final class BASE {
        public static final String URL = "https://api.themoviedb.org/3/";
        public static final String URL_VIDEOS = "https://api.themoviedb.org/3/";
        public static final String URL_REVIEWS = "https://api.themoviedb.org/3/movie/%s/reviews";
        public static final String POSTER_URL = "https://image.tmdb.org/t/p/";
        public static final String YOUTUBE_URL = "https://www.youtube.com/watch?v=";
    }

    public static final class PARAMS {
        public static final String API_KEY = "api_key";
        public static final String LANGUAGE = "language";
        public static final String SORT_BY = "sort_by";
        public static final String PAGE = "page";
        public static final String MIN_VOTE_COUNT = "vote_count.gte";

        public static final class VALUE {
            public static final String API_KEY = "0b8c6374b7229947ab2f764ffc203052";
            public static final String LANGUAGE_VALUE = "uk-UK";
            public static final String SORT_BY_POPULARITY = "popularity.desc";
            public static final String SORT_BY_TOP_RATED = "vote_average.desc";
            public static final String MIN_VOTE_COUNT_VALUE = "1000";
        }
    }

    public static final class KEY {
        private static final String RESULTS = "results";
        private static final String OF_VIDEO = "key";
        private static final String NAME = "name";
        private static final String ID = "id";
        private static final String VOTE_COUNT = "vote_count";
        private static final String TITLE = "title";
        private static final String ORIGINAL_TITLE = "original_title";
        private static final String OVERVIEW = "overview";
        private static final String POSTER_PATH = "poster_path";
        private static final String BACKDROP_PATH = "backdrop_path";
        private static final String VOTE_AVERAGE = "vote_average";
        private static final String RELEASE_DATA = "release_date";
    }

    public static final class POSTER_SIZE {
        public static final String SMALL = "w185";
        public static final String BIG = "w780";
    }
}
