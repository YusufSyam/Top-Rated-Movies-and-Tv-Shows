package com.ysfsym.topratedmoviesandtvshows.fragments;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.BlendModeColorFilterCompat;
import androidx.core.graphics.BlendModeCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.ysfsym.topratedmoviesandtvshows.MainActivity;
import com.ysfsym.topratedmoviesandtvshows.MovieActivityDetail;
import com.ysfsym.topratedmoviesandtvshows.R;
import com.ysfsym.topratedmoviesandtvshows.adapters.movie.MovieAdapter;
import com.ysfsym.topratedmoviesandtvshows.helper.OnItemClickListener;
import com.ysfsym.topratedmoviesandtvshows.helper.Utility;
import com.ysfsym.topratedmoviesandtvshows.models.movie.MovieResult;
import com.ysfsym.topratedmoviesandtvshows.networks.Consts;
import com.ysfsym.topratedmoviesandtvshows.networks.GetRetrofit;
import com.ysfsym.topratedmoviesandtvshows.networks.ApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentMovie extends Fragment implements OnItemClickListener<Integer>, SwipeRefreshLayout.OnRefreshListener, SearchView.OnQueryTextListener {

    private RecyclerView rvMovie;
    private MovieAdapter movieAdapter;
    private SwipeRefreshLayout refreshLayout;
    private GridLayoutManager layoutManager;
    private SearchView searchView;
    private int loadedPage = 1;
    private final int fragmentIndex= 1;
    private boolean isFetching;

    public FragmentMovie(){
    }

    public static FragmentMovie newInstance(){

        FragmentMovie fragment = new FragmentMovie();
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
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_movie, container, false);

        refreshLayout = view.findViewById(R.id.srl_movie);
        refreshLayout.setOnRefreshListener(this);

//        if(MainActivity.currFragmentIndex!=fragmentIndex){
//
//        }

        // Dipakai kalau ingin recycle view nya responsif
        int mNoOfColumns = Utility.calculateNoOfColumns(getContext(), 120);

        layoutManager= new GridLayoutManager(getActivity(), mNoOfColumns);

        rvMovie = view.findViewById(R.id.rv_Movie);
        rvMovie.setLayoutManager(layoutManager);
        rvMovie.setHasFixedSize(true);

        movieAdapter= null;

        loadedPage = 1;
        load(loadedPage);
        onScrollListener();

        return view;
    }

    private void onScrollListener() {
        rvMovie.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
        Call<MovieResult> call = service.topRatedMovies(Consts.APIKEY, Consts.LANGUAGE, page);

        call.enqueue(new Callback<MovieResult>() {
            @Override
            public void onResponse(Call<MovieResult> call, Response<MovieResult> response) {
                if (response.isSuccessful() && response.body().getMovieList() != null){
                    if(movieAdapter == null) {
                        movieAdapter = new MovieAdapter(getContext());
                        movieAdapter.setClickListener(FragmentMovie.this);
                        movieAdapter.setMovieList(response.body().getMovieList());
                        movieAdapter.notifyDataSetChanged();
                        rvMovie.setAdapter(movieAdapter);
                    }else{
                        movieAdapter.addNewMovies(response.body().getMovieList());
                    }

                    isFetching = false;
                    refreshLayout.setRefreshing(false);
                }else {
                    Log.d(Consts.APIERROR, "error");
                }

            }

            @Override
            public void onFailure(Call<MovieResult> call, Throwable t) {

                Log.d(Consts.APIERROR, "error");
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.fragment_action_bar,menu);
        MenuItem item = menu.findItem(R.id.menu_item_search);
        searchView = (SearchView) item.getActionView();

        searchView.setQueryHint(Html.fromHtml("<font color = #ffffff>" + "Search for movies..." + "</font>"));
        searchView.setBackgroundColor(0xFFFA8072);
        searchView.setOnQueryTextListener(this);

        super.onCreateOptionsMenu(menu, inflater);
    }

    private void search(String keyword, int page){
        isFetching = true;
        ApiService searchService = GetRetrofit.getInstance();
        Call<MovieResult> searchMovieCall = searchService.searchMovie(Consts.APIKEY, keyword, page);
        searchMovieCall.enqueue(new Callback<MovieResult>() {
            @Override
            public void onResponse(Call<MovieResult> call, Response<MovieResult> response) {
                try{
                    if(response.isSuccessful() && response.body().getMovieList() != null){
                        if(movieAdapter == null){
                            movieAdapter = new MovieAdapter(getContext());
                            movieAdapter.setClickListener(FragmentMovie.this);
                            movieAdapter.setMovieList(response.body().getMovieList());
                            movieAdapter.notifyDataSetChanged();
                            rvMovie.setAdapter(movieAdapter);
                        }else{
                            movieAdapter.addNewMovies(response.body().getMovieList());
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
            public void onFailure(Call<MovieResult> call, Throwable t) {
                Log.d("Movies", "onFailure: " + t.getLocalizedMessage());
                Toast.makeText(getActivity(), "Failed" + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(Integer id) {
        Intent intent = new Intent(getActivity(), MovieActivityDetail.class);
        if (id != null){
            intent.putExtra("ID", id);
            startActivity(intent);
        }
    }

    @Override
    public void onRefresh() {
        movieAdapter= null;
        loadedPage= 1;
        load(loadedPage);
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return true;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        if (s.length() > 0) {
            movieAdapter = null;
            search(s, loadedPage);
        }else{
            movieAdapter = null;
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
