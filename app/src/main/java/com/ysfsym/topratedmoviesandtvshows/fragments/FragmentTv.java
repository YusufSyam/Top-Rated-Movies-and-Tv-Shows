package com.ysfsym.topratedmoviesandtvshows.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.ysfsym.topratedmoviesandtvshows.TvActivityDetail;
import com.ysfsym.topratedmoviesandtvshows.R;
import com.ysfsym.topratedmoviesandtvshows.adapters.movie.MovieAdapter;
import com.ysfsym.topratedmoviesandtvshows.helper.OnItemClickListener;
import com.ysfsym.topratedmoviesandtvshows.helper.Utility;
import com.ysfsym.topratedmoviesandtvshows.models.movie.MovieResult;
import com.ysfsym.topratedmoviesandtvshows.models.tv.TvResult;
import com.ysfsym.topratedmoviesandtvshows.adapters.tv.TvShowAdapter;
import com.ysfsym.topratedmoviesandtvshows.networks.Consts;
import com.ysfsym.topratedmoviesandtvshows.networks.GetRetrofit;
import com.ysfsym.topratedmoviesandtvshows.networks.ApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentTv extends Fragment implements OnItemClickListener<Integer>, SwipeRefreshLayout.OnRefreshListener, SearchView.OnQueryTextListener {

    private RecyclerView rvTvShow;
    private TvShowAdapter tvShowAdapter;
    private SwipeRefreshLayout refreshLayout;
    private GridLayoutManager layoutManager;
    private SearchView searchView;
    private int loadedPage;
    private boolean isFetching;

    public FragmentTv(){

    }

    public static FragmentTv newInstance(){
        FragmentTv fragmen = new FragmentTv();
        Bundle args = new Bundle();
        fragmen.setArguments(args);
        return fragmen;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tv, container, false);

        refreshLayout = view.findViewById(R.id.srl_movie);
        refreshLayout.setOnRefreshListener(this);

        rvTvShow = view.findViewById(R.id.rv_tv);
        // Dipakai kalau ingin recycle view nya responsif
        int mNoOfColumns = Utility.calculateNoOfColumns(getContext(), 120);

        layoutManager= new GridLayoutManager(getActivity(), mNoOfColumns);
        rvTvShow.setLayoutManager(layoutManager);
        rvTvShow.setHasFixedSize(true);

        tvShowAdapter= null;
        loadedPage = 1;
        load(loadedPage);
        onScrollListener();
        return view;
    }

    private void onScrollListener() {
        rvTvShow.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                int totalItem = layoutManager.getItemCount();
                int visibleItem = layoutManager.getChildCount();
                int firstVisibleItem = layoutManager.findFirstVisibleItemPosition();
                if (!isFetching && (visibleItem+firstVisibleItem) >= totalItem/2) {
                    isFetching = true;
                    loadedPage++;
                    load(loadedPage);
                }
            }
        });
    }

    private void load(int page) {
        isFetching = true;
        ApiService service = GetRetrofit.getInstance();
        Call<TvResult> call = service.topRatedtvShows(Consts.APIKEY, Consts.LANGUAGE, page);

        call.enqueue(new Callback<TvResult>() {
            @Override
            public void onResponse(Call<TvResult> call, Response<TvResult> response) {
                if (response.isSuccessful() && response.body().getTvShowList() != null){
                    if(tvShowAdapter == null) {
                        tvShowAdapter = new TvShowAdapter(getContext());
                        tvShowAdapter.setClickListener(FragmentTv.this);
                        tvShowAdapter.setTvShowList(response.body().getTvShowList());
                        tvShowAdapter.notifyDataSetChanged();
                        rvTvShow.setAdapter(tvShowAdapter);
                    }else{
                        tvShowAdapter.addNewTvShow(response.body().getTvShowList());
                    }
                }else {
                    Log.d(Consts.APIERROR, "error");
                }

                isFetching = false;
                refreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<TvResult> call, Throwable t) {

                Log.d(Consts.APIERROR, "error");
            }
        });


    }

    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.fragment_action_bar,menu);
        MenuItem item = menu.findItem(R.id.menu_item_search);

        searchView = (SearchView) item.getActionView();
        searchView.setQueryHint(Html.fromHtml("<font color = #ffffff>" + "Search for movies..." + "</font>"));
        searchView.setBackgroundColor(0xFFFA8072);
        searchView.setOnQueryTextListener(this);

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onClick(Integer id) {
        Intent intent = new Intent(getActivity(), TvActivityDetail.class);
        if (id != null){
            intent.putExtra("ID", id);
            startActivity(intent);
        }
    }

    @Override
    public void onRefresh() {
        tvShowAdapter= null;
        loadedPage= 1;
        load(loadedPage);
    }

    private void search(String keyword, int page){
        isFetching = true;
        ApiService searchService = GetRetrofit.getInstance();
        Call<TvResult> searchTvShowCall = searchService.searchTv(Consts.APIKEY, keyword, page);
        searchTvShowCall.enqueue(new Callback<TvResult>() {
            @Override
            public void onResponse(Call<TvResult> call, Response<TvResult> response) {
                try{
                    if(response.isSuccessful() && response.body().getTvShowList() != null){
                        if(tvShowAdapter == null){
                            tvShowAdapter = new TvShowAdapter(getContext());
                            tvShowAdapter.setClickListener(FragmentTv.this);
                            tvShowAdapter.setTvShowList(response.body().getTvShowList());
                            tvShowAdapter.notifyDataSetChanged();
                            rvTvShow.setAdapter(tvShowAdapter);
                        }else{
                            tvShowAdapter.addNewTvShow(response.body().getTvShowList());
                        }
                        loadedPage = page;
                        isFetching = false;
                        refreshLayout.setRefreshing(false);
                    }
                    else{
                        Toast.makeText(getActivity(), response.errorBody().string(), Toast.LENGTH_LONG).show();
                    }
                }catch(Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<TvResult> call, Throwable t) {
                Log.d("TV Shows", "onFailure: " + t.getLocalizedMessage());
                Toast.makeText(getActivity(), "Failed" + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return true;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        if (s.length() > 0) {
            tvShowAdapter = null;
            search(s, loadedPage);
        }else{
            tvShowAdapter = null;
            load(loadedPage);
        }
        return true;
    }

    @Override
    public void onPause() {
        super.onPause();
        if(searchView != null){
            searchView.setQuery("", false);
            searchView.setIconified(true);
        }
    }
}
