package com.example.daval.moviepopularsapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {
    private NavController navController;
    private androidx.appcompat.widget.Toolbar toolbar;
    private NavigationView navigation_view;
    private DrawerLayout drawer_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        navigation_view = findViewById(R.id.navigation_view);
        drawer_layout = findViewById(R.id.drawer_layout);

        setSupportActionBar(toolbar);
        navController = Navigation.findNavController(this, R.id.fragment);

        NavigationUI.setupWithNavController(navigation_view, navController);
        NavigationUI.setupActionBarWithNavController(this,navController, drawer_layout);
    }

    @Override
    public boolean onSupportNavigateUp() {
        return NavigationUI.navigateUp(
                navController,
                drawer_layout
        );
    }
 }
