package com.utabpars.gomgashteh.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStore;
import androidx.navigation.NavController;
import androidx.navigation.Navigator;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;

import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.utabpars.gomgashteh.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView bottomNavigationView=findViewById(R.id.bottomnav);

        NavHostFragment hostFragment= (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_host);
        NavigationUI.setupWithNavController(bottomNavigationView,hostFragment.getNavController());




    }
}