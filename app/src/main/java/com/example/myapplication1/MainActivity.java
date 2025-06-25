package com.example.myapplication1;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import android.view.MenuItem;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView bottomNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNav = findViewById(R.id.bottom_nav);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, new ActivityFragment(), "activity_fragment")
                    .commit();
        }

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(v -> {
            Intent intent = new Intent(this, NewActivityStartActivity.class);
            startActivity(intent);
        });

        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.nav_activity) {
                    showActivityFragment();
                } else if (item.getItemId() == R.id.nav_profile) {
                    showProfileFragment();
                }
                return true;
            }
        });
    }

    private void showActivityFragment() {
        Fragment activityFragment = getSupportFragmentManager().findFragmentByTag("activity_fragment");
        Fragment profileFragment = getSupportFragmentManager().findFragmentByTag("profile_fragment");

        androidx.fragment.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (profileFragment != null) transaction.hide(profileFragment);
        if (activityFragment == null) {
            transaction.add(R.id.fragment_container, new ActivityFragment(), "activity_fragment");
        } else {
            transaction.show(activityFragment);
        }
        transaction.commit();
    }

    private void showProfileFragment() {
        Fragment activityFragment = getSupportFragmentManager().findFragmentByTag("activity_fragment");
        Fragment profileFragment = getSupportFragmentManager().findFragmentByTag("profile_fragment");

        androidx.fragment.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (activityFragment != null) transaction.hide(activityFragment);
        if (profileFragment == null) {
            transaction.add(R.id.fragment_container, new ProfileFragment(), "profile_fragment");
        } else {
            transaction.show(profileFragment);
        }
        transaction.commit();
    }
}