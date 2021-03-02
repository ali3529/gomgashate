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
import com.utabpars.gomgashteh.database.categoryDatabase.CategoryDataBase;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //single activity design pattern
        setContentView(R.layout.activity_main);
        BottomNavigationView bottomNavigationView=findViewById(R.id.bottomnav);

        NavHostFragment hostFragment= (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_host);
        NavigationUI.setupWithNavController(bottomNavigationView,hostFragment.getNavController());

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}