package com.ysfsym.topratedmoviesandtvshows.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.ysfsym.topratedmoviesandtvshows.MovieActivityDetail;
import com.ysfsym.topratedmoviesandtvshows.R;


import com.ysfsym.topratedmoviesandtvshows.adapters.bookmark.MovieBookmarkAdapter;
import com.ysfsym.topratedmoviesandtvshows.helper.OnItemClickListener;
import com.ysfsym.topratedmoviesandtvshows.dbs.AppDatabase;
import com.ysfsym.topratedmoviesandtvshows.dbs.table.FavoriteMovie;
import com.ysfsym.topratedmoviesandtvshows.helper.Utility;

import java.util.List;

public class TabMovieBookmark extends Fragment implements OnItemClickListener<Integer>{
    private RecyclerView recyclerView;
    private AppDatabase database;
    private LinearLayout tvNotFound;
    private GridLayoutManager layoutManager;
    private List<FavoriteMovie> favoriteList;

    public TabMovieBookmark(){

    }
    public static TabMovieBookmark newInstance(){
        TabMovieBookmark fragment = new TabMovieBookmark();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_bookmark_movie, container, false);
        database = AppDatabase.getInstance(getActivity().getApplicationContext());

        tvNotFound = view.findViewById(R.id.tv_no_bookmark);

        recyclerView = view.findViewById(R.id.rv_Movie);

        int mNoOfColumns = Utility.calculateNoOfColumns(getContext(), 120);
        layoutManager= new GridLayoutManager(getActivity(), mNoOfColumns);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setVisibility(View.GONE);
        tvNotFound.setVisibility(View.GONE);

        loadData();
        return view;
    }

    private void loadData() {

        favoriteList= database.favoriteDao().getAllMovie();

        MovieBookmarkAdapter adapter= new MovieBookmarkAdapter(favoriteList);
        adapter.setClickListener(this);

        recyclerView.setAdapter(adapter);
        if(favoriteList.size()==0){
            recyclerView.setVisibility(View.GONE);
            tvNotFound.setVisibility(View.VISIBLE);
        }else{
            recyclerView.setVisibility(View.VISIBLE);
            tvNotFound.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(Integer pos) {
        FragmentBookmark.currItem= 0;
        Intent detailActivity = new Intent(getActivity(), MovieActivityDetail.class);
        detailActivity.putExtra("ID", favoriteList.get(pos).getId());
        detailActivity.putExtra("IsFromBookmark", true);
        startActivity(detailActivity);
    }

    @Override
    public void onResume() {
        super.onResume();
        loadData();
    }
}