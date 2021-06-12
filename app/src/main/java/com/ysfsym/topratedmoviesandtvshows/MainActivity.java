package com.ysfsym.topratedmoviesandtvshows;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;

import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ysfsym.topratedmoviesandtvshows.fragments.FragmentBookmark;
import com.ysfsym.topratedmoviesandtvshows.fragments.FragmentMovie;
import com.ysfsym.topratedmoviesandtvshows.fragments.FragmentTv;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.ysfsym.topratedmoviesandtvshows.networks.Consts;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private Map<Integer, Fragment> fragmentMap;
    private BottomNavigationView bottomNav;

    public static int currFragmentIndex= 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentMap = new HashMap<>();
        bottomNav = findViewById(R.id.bottom_navigation);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new FragmentMovie()).commit();
        }
    }

    @Override
    public void onConfigurationChanged(@NotNull Configuration newConfig){
        super.onConfigurationChanged(newConfig);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                getFragmentAfterChangePotrait()).commit();
    }

    @Override
    protected void onStart() {
        super.onStart();
        fragmentMap.put(R.id.nav_movie, FragmentMovie.newInstance());
        fragmentMap.put(R.id.nav_tvShow, FragmentTv.newInstance());
        fragmentMap.put(R.id.nav_bookmark, new FragmentBookmark());

        bottomNav.setOnNavigationItemSelectedListener(navListener);

        switch (currFragmentIndex){
            case 2:
                bottomNav.setSelectedItemId(R.id.nav_bookmark);
                break;

            case 1:
                bottomNav.setSelectedItemId(R.id.nav_tvShow);
                break;

            case 0:
                bottomNav.setSelectedItemId(R.id.nav_movie);
                break;
        }
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectedFragment = fragmentMap.get(item.getItemId());
            assert selectedFragment != null;

            switch (item.getItemId()){
                case R.id.nav_bookmark:
                    currFragmentIndex= 2;
                    setActionBar(Consts.TITLE_BOOKMARKS);
                    break;
                case R.id.nav_tvShow:
                    currFragmentIndex= 1;
                    setActionBar(Consts.TITLE_TVSHOW);
                    break;

                case R.id.nav_movie:
                    currFragmentIndex= 0;
                    setActionBar(Consts.TITLE_MOVIE);
                    break;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    selectedFragment).commit();

            return true;
        }
    };

    private void setActionBar(String title) {
        View view = getLayoutInflater().inflate(R.layout.action_bar,null);
        ActionBar.LayoutParams params = new ActionBar.LayoutParams(
                ActionBar.LayoutParams.WRAP_CONTENT,
                ActionBar.LayoutParams.MATCH_PARENT
        );

        TextView titleBar = view.findViewById(R.id.tv_action_bar_title);
        titleBar.setText(title);


        getSupportActionBar().setCustomView(view, params);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FFFFFF")));
        getSupportActionBar().setTitle(title);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    public Fragment getFragmentAfterChangePotrait(){
        Fragment newLandscapeFragment= null;

        switch (currFragmentIndex){
            case 2:
                newLandscapeFragment= new FragmentBookmark();
                assert newLandscapeFragment != null;
                break;

            case 1:
                newLandscapeFragment= new FragmentTv();
                assert newLandscapeFragment != null;
                break;

            case 0:
                newLandscapeFragment= new FragmentMovie();
                assert newLandscapeFragment != null;
                break;

            default:
                newLandscapeFragment= new FragmentMovie();
                assert newLandscapeFragment != null;
                break;
        }

        return newLandscapeFragment;
    }

}