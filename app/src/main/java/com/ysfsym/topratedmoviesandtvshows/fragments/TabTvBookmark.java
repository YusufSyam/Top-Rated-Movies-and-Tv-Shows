package com.ysfsym.topratedmoviesandtvshows.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.ysfsym.topratedmoviesandtvshows.R;


import com.ysfsym.topratedmoviesandtvshows.TvActivityDetail;
import com.ysfsym.topratedmoviesandtvshows.adapters.bookmark.TvBookmarkAdapter;
import com.ysfsym.topratedmoviesandtvshows.helper.OnItemClickListener;
import com.ysfsym.topratedmoviesandtvshows.dbs.AppDatabase;
import com.ysfsym.topratedmoviesandtvshows.dbs.table.FavoriteTv;
import com.ysfsym.topratedmoviesandtvshows.helper.Utility;

import java.util.List;

public class TabTvBookmark extends Fragment implements OnItemClickListener<Integer>{
    private RecyclerView recyclerView;
    private AppDatabase database;
    private LinearLayout tvNotFound;
    private GridLayoutManager layoutManager;
    private List<FavoriteTv> favoriteList;

    public  TabTvBookmark(){

    }
    public static TabTvBookmark newInstance(){
        TabTvBookmark fragment = new TabTvBookmark();
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
        View view = inflater.inflate(R.layout.fragment_bookmark_tv, container, false);
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
        database.favoriteDao().getAllTvSow();

        favoriteList= database.favoriteDao().getAllTvSow();

        TvBookmarkAdapter adapter= new TvBookmarkAdapter(favoriteList);

        recyclerView.setAdapter(adapter);
        adapter.setClickListener(this);
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
        FragmentBookmark.currItem= 1;
        Intent detailActivity = new Intent(getActivity(), TvActivityDetail.class);
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