package com.example.movies.presentation.movie;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.movies.R;
import com.example.movies.databinding.ActivityMainBinding;
import com.example.movies.presentation.movie.favorite.FavoriteMovieFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.jetbrains.annotations.NotNull;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity implements
        BottomNavigationView.OnNavigationItemSelectedListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mainBinding.navigationView.setOnNavigationItemSelectedListener(this);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, new PopularMovieFragment())
                .commit();
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
        Fragment selectedFragment = null;
        switch (item.getItemId()) {
            case R.id.popular:
                selectedFragment = new PopularMovieFragment();
                break;
            case R.id.rating:
                selectedFragment = new RatingMovieFragment();
                break;
            case R.id.favorite:
                selectedFragment = new FavoriteMovieFragment();
                break;
        }
        assert selectedFragment != null;
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, selectedFragment)
                .commit();
        return true;
    }
}
