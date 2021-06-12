package com.ysfsym.topratedmoviesandtvshows;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;

import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.github.lzyzsd.circleprogress.DonutProgress;
import com.ysfsym.topratedmoviesandtvshows.dbs.table.FavoriteTv;
import com.ysfsym.topratedmoviesandtvshows.dbs.AppDatabase;
import com.ysfsym.topratedmoviesandtvshows.models.tv.TvShowDetail;
import com.ysfsym.topratedmoviesandtvshows.networks.Consts;
import com.ysfsym.topratedmoviesandtvshows.networks.GetRetrofit;
import com.ysfsym.topratedmoviesandtvshows.networks.ApiService;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TvActivityDetail extends AppCompatActivity {

    ImageView ivBackdrop, ivPoster;
    View vGradient;
    TextView tvTitle, tvGenre, tvOverview, tvNumOfEpisodes, tvFirstAirDate;
    Integer tvId;
    DonutProgress dpUserScore;
    String favoriteTitle, favoriteImgUrl, favoriteReleaseDate = "";
    Double favouriteVoteAverage= 0.0;

    AppDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tv_detail);

        database = AppDatabase.getInstance(getApplicationContext());
        setActionBar("TV Show Detail");

        ivBackdrop = findViewById(R.id.iv_backdrop);
        ivPoster = findViewById(R.id.iv_poster);
        tvTitle = findViewById(R.id.tv_title);
        tvNumOfEpisodes = findViewById(R.id.tv_episode);
        tvGenre = findViewById(R.id.tv_genre);
        tvFirstAirDate = findViewById(R.id.tv_first_air_date);
        tvOverview= findViewById(R.id.tv_overview);
        vGradient= findViewById(R.id.v_gradient);
        dpUserScore = findViewById(R.id.dp_user_score);
    }

    @Override
    protected void onStart() {
        super.onStart();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            if (extras.containsKey("IsFromBookmark")) {
                MainActivity.currFragmentIndex= 2;
            }else{
                MainActivity.currFragmentIndex= 1;
            }

            tvId= extras.getInt("ID", 2);
        }

        load(tvId);
    }

    private void load(Integer id) {
        ApiService service = GetRetrofit.getInstance();

        Map<String, String> params = new HashMap<>();
        params.put("api_key", Consts.APIKEY);
        params.put("language", Consts.LANGUAGE);
        Call<TvShowDetail> call = service.tvDetail(id, params);

        call.enqueue(new Callback<TvShowDetail>() {
            @Override
            public void onResponse(Call<TvShowDetail> call, Response<TvShowDetail> response) {
                if(response.isSuccessful() && response.body() != null) {
                    TvShowDetail tvShowDetail = response.body();

                    favoriteTitle= tvShowDetail.getTitle();
                    favoriteImgUrl= tvShowDetail.getPosterImage();

                    tvTitle.setText(tvShowDetail.getTitle());

                    dpUserScore.setDonut_progress(setUserScore(tvShowDetail.getVoteAverage()));
                    favouriteVoteAverage= tvShowDetail.getVoteAverage();

                    tvFirstAirDate.setText(tvShowDetail.getFirstAirDate());
                    favoriteReleaseDate= tvShowDetail.getFirstAirDate();

                    tvNumOfEpisodes.setText(tvShowDetail.getEpisodes()+" eps");

                    String getGenre = "";
                    for (int i = 0; i < tvShowDetail.getGenres().size(); i++){
                        getGenre += tvShowDetail.getGenres().get(i).getName() + (i==tvShowDetail.getGenres().size()-1 ? "." : ", ");
                    }
                    tvGenre.setText(getGenre);

                    tvOverview.setText(setParagraph(tvShowDetail.getOverview()));

                    String uri = Consts.BACKDROPBASEURL + tvShowDetail.getBackdropImage();
                    Glide.with(TvActivityDetail.this).load(uri).into(ivBackdrop);

                    String uri2 = Consts.POSTERBASEURL + tvShowDetail.getPosterImage();
                    Glide.with(TvActivityDetail.this).load(uri2).into(ivPoster);

                    measuringGradient();
                }else {
                    Log.d(Consts.APIERROR, "error");
                }
            }

            @Override
            public void onFailure(Call<TvShowDetail> call, Throwable t) {

                Log.d(Consts.APIERROR, t.getMessage());
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.menu_like:
                int tvShowId = tvId;
                boolean exists = database.favoriteDao().isTvExists(tvShowId);

                if(exists){
                    FavoriteTv favorite = database.favoriteDao().findByTvId(tvShowId);
                    database.favoriteDao().deleteFavoriteTv(favorite).subscribe(() -> {
                        item.setIcon(ContextCompat.getDrawable(this,R.drawable.ic_action_bar_bookmark_border_24));
                        Toast.makeText(this, "Unbookmark", Toast.LENGTH_SHORT).show();
                    }, throwable -> {
                        Toast.makeText(this, "Operation Failed", Toast.LENGTH_SHORT).show();
                    });

                }else{
                    FavoriteTv favorite = new FavoriteTv(tvShowId,favoriteTitle, favoriteImgUrl, favouriteVoteAverage, favoriteReleaseDate);
                    database.favoriteDao().addFavoriteTvShow(favorite).subscribe(() -> {
                        item.setIcon(ContextCompat.getDrawable(this,R.drawable.ic_action_bar_bookmark_24));

                        Toast.makeText(this, "Bookmarked", Toast.LENGTH_SHORT).show();
                    }, throwable -> {
                        Toast.makeText(this, "Failed To Add", Toast.LENGTH_SHORT).show();
                    });
                }

                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void setActionBar(String title){
        View view = getLayoutInflater().inflate(R.layout.action_bar,null);
        ActionBar.LayoutParams params = new ActionBar.LayoutParams(
                ActionBar.LayoutParams.WRAP_CONTENT,
                ActionBar.LayoutParams.MATCH_PARENT
        );

        TextView titleBar = view.findViewById(R.id.tv_action_bar_title);
        titleBar.setText(title);
        titleBar.setTextColor(getResources().getColor(R.color.text));

        ActionBar actionBar = getSupportActionBar();

        actionBar.setCustomView(view, params);
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FFFFFF")));
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_back_26);

    }

    public String setUserScore(double number){
        int numberInteger= (int) (number*10);
        return String.valueOf(numberInteger);
    }

    public String setParagraph(String paragraph){
        String [] arrParagraph= paragraph.split("\n\n");
        String newParagraph= "";

        for(int i=0; i<arrParagraph.length; i++){
            newParagraph+="\t\t"+arrParagraph[i]+"\n\n";
        }
        return newParagraph;
    }

    public void measuringGradient(){
        int imageWidth = ivBackdrop.getWidth();
        int imageHeight= ivBackdrop.getHeight();

        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) vGradient.getLayoutParams();
        layoutParams.width = (imageWidth/2)+50;
        layoutParams.height = imageHeight;

        vGradient.setLayoutParams(layoutParams);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detail_action_bar, menu);

        int movieId = tvId;
        boolean exists = database.favoriteDao().isTvExists(movieId);

        if(!exists){
            menu.getItem(0).setIcon(ContextCompat.getDrawable(this,R.drawable.ic_action_bar_bookmark_border_24));
        }else{
            menu.getItem(0).setIcon(ContextCompat.getDrawable(this,R.drawable.ic_action_bar_bookmark_24));
        }
        return true;
    }
}