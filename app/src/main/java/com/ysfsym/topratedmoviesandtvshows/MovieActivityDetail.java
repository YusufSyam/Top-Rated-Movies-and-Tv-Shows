package com.ysfsym.topratedmoviesandtvshows;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.github.lzyzsd.circleprogress.DonutProgress;
import com.ysfsym.topratedmoviesandtvshows.dbs.AppDatabase;
import com.ysfsym.topratedmoviesandtvshows.dbs.dao.FavoriteDao;
import com.ysfsym.topratedmoviesandtvshows.dbs.table.FavoriteMovie;
import com.ysfsym.topratedmoviesandtvshows.models.movie.MovieDetail;
import com.ysfsym.topratedmoviesandtvshows.networks.Consts;
import com.ysfsym.topratedmoviesandtvshows.networks.GetRetrofit;
import com.ysfsym.topratedmoviesandtvshows.networks.ApiService;

import java.util.HashMap;
import java.util.Map;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieActivityDetail extends AppCompatActivity {

    ImageView ivBackdrop, ivPoster;
    View vGradient;
    TextView tvTitle, tvGenre, tvOverview, tvReleaseDate, tvYear, tvDuration;
    Integer tvId;
    DonutProgress dpUserScore;
    AppDatabase database;
    String favoriteTitle, favoriteImgUrl, favoriteReleaseDate = "";
    Double favouriteVoteAverage= 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        database = AppDatabase.getInstance(getApplicationContext());
        setActionBar("Movie Detail");

        ivBackdrop = findViewById(R.id.iv_backdrop);
        ivPoster = findViewById(R.id.iv_poster);
        tvTitle = findViewById(R.id.tv_title);
        tvGenre = findViewById(R.id.tv_genre);
        tvOverview= findViewById(R.id.tvOverview);
        tvYear= findViewById(R.id.tv_year);
        tvReleaseDate= findViewById(R.id.tv_release_date);
        tvDuration= findViewById(R.id.tv_duration);
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
                MainActivity.currFragmentIndex= 0;
            }

            tvId= extras.getInt("ID", 2);
        }

        tvId = getIntent().getIntExtra("ID", 0);
        load(tvId);
    }

    private void load(Integer id) {
        ApiService service = GetRetrofit.getInstance();

        Map<String, String> params = new HashMap<>();
        params.put("api_key", Consts.APIKEY);
        params.put("language", Consts.LANGUAGE);
        Call<MovieDetail> call = service.movieDetail(id, params);
        call.enqueue(new Callback<MovieDetail>() {
            @Override
            public void onResponse(Call<MovieDetail> call, Response<MovieDetail> response) {
                if (response.isSuccessful() && response.body() != null){
                    MovieDetail movieDetail = response.body();

                    favoriteTitle= movieDetail.getTitle();
                    favoriteImgUrl= movieDetail.getPosterImage();

                    tvTitle.setText(movieDetail.getTitle());

                    String getGenre = "";
                    for (int i = 0; i < movieDetail.getGenres().size(); i++){
                        getGenre += movieDetail.getGenres().get(i).getName() + (i==movieDetail.getGenres().size()-1 ? "." : ", ");
                    }
                    tvGenre.setText(getGenre);

                    tvOverview.setText(setParagraph(movieDetail.getOverview()));

                    tvDuration.setText(countingDuration(movieDetail.getRuntime()));

                    tvReleaseDate.setText(movieDetail.getReleaseDate());
                    tvYear.setText(("("+(""+movieDetail.getReleaseDate()).substring(0,4)+")"));
                    favoriteReleaseDate= movieDetail.getReleaseDate();

                    dpUserScore.setDonut_progress(setUserScore(movieDetail.getVoteAverage()));
                    favouriteVoteAverage= movieDetail.getVoteAverage();

                    String uri = Consts.BACKDROPBASEURL + movieDetail.getBackdropImage();
                    Glide.with(MovieActivityDetail.this).load(uri).into(ivBackdrop);

                    String uri2 = Consts.POSTERBASEURL + movieDetail.getPosterImage();
                    Glide.with(MovieActivityDetail.this).load(uri2).into(ivPoster);

                    measuringGradient();
                }else {
                    Log.d(Consts.APIERROR, "error");

                }
            }

            @Override
            public void onFailure(Call<MovieDetail> call, Throwable t) {
                Log.d(Consts.APIERROR, "error");
            }
        });

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

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.menu_like:
                int movieId = tvId;
                boolean exists = database.favoriteDao().isMovieExists(movieId);

                if(exists){
                    FavoriteMovie favorite = database.favoriteDao().findByMovieId(movieId);
                    database.favoriteDao().deleteFavoriteMovie(favorite).subscribe(() -> {
                        item.setIcon(ContextCompat.getDrawable(this,R.drawable.ic_action_bar_bookmark_border_24));
                        Toast.makeText(this, "Unbookmark", Toast.LENGTH_SHORT).show();
                    }, throwable -> {
                        Toast.makeText(this, "Operation Failed", Toast.LENGTH_SHORT).show();
                    });

                }else{
                    FavoriteMovie favorite = new FavoriteMovie(movieId,favoriteTitle, favoriteImgUrl, favouriteVoteAverage, favoriteReleaseDate);
                    database.favoriteDao().addFavoriteMovie(favorite).subscribe(() -> {
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

    public String countingDuration(int min){
        String newTime;

        if(min==0){
            newTime= "-";
        }else{
            String newHour= "";
            String newMin= "";

            if((min/60)!=0) newHour= (min/60)+"h";
            if((min%60)!=0) newMin= (min%60)+"m";

            String space= (newHour.equals("") || newMin.equals(""))? "" : " ";

            newTime= newHour+space+newMin;
        }

        return newTime;
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
        boolean exists = database.favoriteDao().isMovieExists(tvId);

        if(!exists){
            menu.getItem(0).setIcon(ContextCompat.getDrawable(this,R.drawable.ic_action_bar_bookmark_border_24));
        }else{
            menu.getItem(0).setIcon(ContextCompat.getDrawable(this,R.drawable.ic_action_bar_bookmark_24));
        }
        return true;
    }
}