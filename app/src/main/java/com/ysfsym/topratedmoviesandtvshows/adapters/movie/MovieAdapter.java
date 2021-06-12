package com.ysfsym.topratedmoviesandtvshows.adapters.movie;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.github.lzyzsd.circleprogress.DonutProgress;
import com.ysfsym.topratedmoviesandtvshows.R;
import com.ysfsym.topratedmoviesandtvshows.dbs.AppDatabase;
import com.ysfsym.topratedmoviesandtvshows.helper.OnItemClickListener;
import com.ysfsym.topratedmoviesandtvshows.models.movie.Movie;
import com.ysfsym.topratedmoviesandtvshows.networks.Consts;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {
    private Context mContext;
    private List<Movie> movieList;

    private OnItemClickListener<Integer> clickListener;

    public MovieAdapter(Context context){
        this.mContext= context;
    }

    public void setClickListener(OnItemClickListener<Integer> clickListener){
        this.clickListener = clickListener;
    }

    public void setMovieList(List<Movie> movieList){
        this.movieList = movieList;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.onBind(movieList.get(position), mContext);
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public void addNewMovies(List<Movie> newMovie){
        movieList.addAll(newMovie);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        Movie movie;
        TextView tvTitle, tvReleaseDate;
        DonutProgress dpUserScore;
        ImageView photo, ivIsBookmarked;
        Integer tvId;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            tvTitle = itemView.findViewById(R.id.tvJudul);
            photo = itemView.findViewById(R.id.photo);
            dpUserScore= itemView.findViewById(R.id.dp_user_score);
            tvReleaseDate= itemView.findViewById(R.id.tv_release_date);
            ivIsBookmarked= itemView.findViewById(R.id.iv_is_bookmarked);

        }
        public void onBind(Movie movie, Context mContext){
            String uri = Consts.POSTERBASEURL + movie.getPosterImage();
            this.movie = movie;
            tvId = movie.getId();
            tvTitle.setText(movie.getTitle());
            dpUserScore.setDonut_progress(setUserScore(movie.getVoteAverage()));
            tvReleaseDate.setText(movie.getReleaseDate());
            Glide.with(this.itemView.getContext()).load(uri).into(photo);

            ivIsBookmarked.setImageResource(AppDatabase.getInstance(mContext).favoriteDao().isMovieExists(tvId)? R.drawable.ic_action_bar_bookmark_24 : R.drawable.ic_action_bar_bookmark_border_24);
        }

        @Override
        public void onClick(View view) {
            Log.d("DEBUG", tvId.toString());
            clickListener.onClick(tvId);
        }

        public String setUserScore(double number){
            int numberInteger= (int) (number*10);
            return String.valueOf(numberInteger);
        }
    }
}
