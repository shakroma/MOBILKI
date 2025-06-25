package com.example.myapplication1;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class ActivityViewPagerAdapter extends FragmentStateAdapter {
    public ActivityViewPagerAdapter(@NonNull Fragment fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 0) return new WorkoutsFragment();
        else if (position == 1) return new StatsFragment();
        else if (position == 2) return new UsersFragment();
        else throw new IllegalArgumentException();
    }

    @Override
    public int getItemCount() {
        return 3;
    }
} 