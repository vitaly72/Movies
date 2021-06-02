//package com.example.movies.di;
//
//import com.example.movies.data.remote.IMovieApi;
//import com.example.movies.data.repository.MovieRepository;
//import com.example.movies.domain.repository.IMovieRepository;
//import com.example.movies.utils.Constants;
//
//import javax.inject.Singleton;
//
//import dagger.Module;
//import dagger.Provides;
//import dagger.hilt.InstallIn;
//import dagger.hilt.android.components.ActivityComponent;
//import okhttp3.OkHttpClient;
//import okhttp3.logging.HttpLoggingInterceptor;
//import retrofit2.Retrofit;
//import retrofit2.converter.gson.GsonConverterFactory;
//
//@Module
//@InstallIn(ActivityComponent.class)
//public class MovieModule {
//    @Singleton
//    @Provides
//    public Retrofit provideRetrofit(GsonConverterFactory gsonConverterFactory, OkHttpClient okHttpClient) {
//        return new Retrofit.Builder()
//                .baseUrl(Constants.BASE.URL)
//                .addConverterFactory(gsonConverterFactory)
//                .client(okHttpClient)
//                .build();
//    }
//
//    @Provides
//    public GsonConverterFactory provideGsonConverterFactory() {
//        return GsonConverterFactory.create();
//    }
//
//    @Provides
//    public OkHttpClient provideOkHttpClient(HttpLoggingInterceptor httpLoggingInterceptor) {
//        return new OkHttpClient.Builder()
//                .addInterceptor(httpLoggingInterceptor)
//                .build();
//    }
//
//    @Provides
//    public HttpLoggingInterceptor provideInterceptor() {
//        return new HttpLoggingInterceptor()
//                .setLevel(HttpLoggingInterceptor.Level.BODY);
//    }
//
//    @Provides
//    public IMovieApi provideIMovieApi(Retrofit retrofit) {
//        return retrofit.create(IMovieApi.class);
//    }
//
////    @Singleton
////    @Provides
////    public provideCharacterRemoteDataSource(characterService: CharacterService) = CharacterRemoteDataSource(characterService)
////
////    @Singleton
////    @Provides
////    public provideDatabase(@ApplicationContext appContext:Context) = AppDatabase.getDatabase(appContext)
////
////    @Singleton
////    @Provides
////    public provideCharacterDao(db: AppDatabase) = db.characterDao()
//
//    @Singleton
//    @Provides
//    public IMovieRepository provideMovieRepository(IMovieApi movieApi) {
//        return new MovieRepository(movieApi);
//    }
//}
