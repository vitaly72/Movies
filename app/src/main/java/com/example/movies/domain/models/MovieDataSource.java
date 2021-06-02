//package com.example.movies.domain.models;
//
//import androidx.annotation.NonNull;
//import androidx.paging.PageKeyedDataSource;
//
//import com.example.movies.data.remote.NetworkService;
//import com.example.movies.utils.Constants;
//
//import org.jetbrains.annotations.NotNull;
//
//import java.util.List;
//
//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Response;
//
//public class MovieDataSource extends PageKeyedDataSource<Integer, Movie> {
//    public static int PAGE_SIZE = 20;
//    public static int FIRST_PAGE = 1;
//
//    @Override
//    public void loadInitial(@NonNull @NotNull LoadInitialParams<Integer> params,
//                            @NonNull @NotNull LoadInitialCallback<Integer, Movie> callback) {
//        System.out.println("MovieDataSource.loadInitial");
//        NetworkService.createService()
//                .movie(Constants.PARAMS.VALUE.API_KEY,
//                        Constants.PARAMS.VALUE.LANGUAGE_VALUE,
////                        sortByPopular ? Constants.PARAMS.VALUE.SORT_BY_POPULARITY : Constants.PARAMS.VALUE.SORT_BY_TOP_RATED,
//                        Constants.PARAMS.VALUE.SORT_BY_POPULARITY,
//                        Constants.PARAMS.VALUE.MIN_VOTE_COUNT_VALUE,
//                        Integer.toString(FIRST_PAGE))
//                .enqueue(new Callback<MovieResponse>() {
//                    @Override
//                    public void onResponse(@NotNull Call<MovieResponse> call,
//                                           @NotNull Response<MovieResponse> response) {
//                        MovieResponse apiResponse = response.body();
//                        if (apiResponse != null) {
//                            List<Movie> responseItems = apiResponse.getMovieList();
//                            System.out.println("responseItems = " + responseItems.size());
//                            callback.onResult(responseItems, null, FIRST_PAGE + 1);
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(@NotNull Call<MovieResponse> call, @NotNull Throwable t) {
//                    }
//                });
//    }
//
//    @Override
//    public void loadBefore(@NonNull @NotNull LoadParams<Integer> params,
//                           @NonNull @NotNull LoadCallback<Integer, Movie> callback) {
//        System.out.println("MovieDataSource.loadBefore");
////        Call<MovieResponse> call = NetworkService.createService().movie(
////                Constants.PARAMS.VALUE.API_KEY,
////                Constants.PARAMS.VALUE.LANGUAGE_VALUE,
////                Constants.PARAMS.VALUE.SORT_BY_POPULARITY,
////                Constants.PARAMS.VALUE.MIN_VOTE_COUNT_VALUE,
////                Integer.toString(FIRST_PAGE)
////        );
////        call.enqueue(new Callback<MovieResponse>() {
////            @Override
////            public void onResponse(@NotNull Call<MovieResponse> call,
////                                   @NotNull Response<MovieResponse> response) {
////                MovieResponse apiResponse = response.body();
////                if (apiResponse != null) {
////                    List<Movie> responseItems = apiResponse.getMovieList();
////                    long key;
////                    if (params.key > 1) {
////                        key = params.key - 1;
////                    } else {
////                        key = 0;
////                    }
////                    callback.onResult(responseItems, key);
////                }
////            }
////
////            @Override
////            public void onFailure(@NotNull Call<MovieResponse> call,
////                                  @NotNull Throwable t) {
////            }
////        });
//    }
//
//    @Override
//    public void loadAfter(@NonNull @NotNull LoadParams<Integer> params,
//                          @NonNull @NotNull LoadCallback<Integer, Movie> callback) {
//        System.out.println("MovieDataSource.loadAfter");
//        Call<MovieResponse> call = NetworkService.createService()
//                .movie(Constants.PARAMS.VALUE.API_KEY,
//                        Constants.PARAMS.VALUE.LANGUAGE_VALUE,
//                        Constants.PARAMS.VALUE.SORT_BY_POPULARITY,
//                        Constants.PARAMS.VALUE.MIN_VOTE_COUNT_VALUE,
//                        Integer.toString(params.key)
//                );
//        call.enqueue(new Callback<MovieResponse>() {
//            @Override
//            public void onResponse(@NotNull Call<MovieResponse> call,
//                                   @NotNull Response<MovieResponse> response) {
//                MovieResponse apiResponse = response.body();
//                if (apiResponse != null) {
//                    List<Movie> responseItems = apiResponse.getMovieList();
//                    callback.onResult(responseItems, params.key + 1);
//                }
//            }
//
//            @Override
//            public void onFailure(@NotNull Call<MovieResponse> call,
//                                  @NotNull Throwable t) {
//            }
//        });
//    }
//}
