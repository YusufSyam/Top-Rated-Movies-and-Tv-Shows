package com.ysfsym.topratedmoviesandtvshows.adapters.bookmark;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.ysfsym.topratedmoviesandtvshows.fragments.TabTvBookmark;
import com.ysfsym.topratedmoviesandtvshows.fragments.TabMovieBookmark;

import org.jetbrains.annotations.NotNull;

public class ViewPagerAdapter extends FragmentStateAdapter {
    // attribute
    private final Fragment[] fragments;

    public ViewPagerAdapter(@NonNull @NotNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
        //instance fragments2 favorite

        fragments = new Fragment[]{
                new TabMovieBookmark(),
                new TabTvBookmark()
        };
    }

    @NonNull
    @NotNull
    @Override
    public Fragment createFragment(int position) {
        return fragments[position];
    }

    @Override
    public int getItemCount() {
        return fragments.length;
    }


}
