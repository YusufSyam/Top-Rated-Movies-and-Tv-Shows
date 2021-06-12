package com.ysfsym.topratedmoviesandtvshows.adapters.bookmark;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.github.lzyzsd.circleprogress.DonutProgress;
import com.ysfsym.topratedmoviesandtvshows.R;
import com.ysfsym.topratedmoviesandtvshows.helper.OnItemClickListener;
import com.ysfsym.topratedmoviesandtvshows.dbs.table.FavoriteMovie;
import com.ysfsym.topratedmoviesandtvshows.networks.Consts;

import java.util.List;

public class MovieBookmarkAdapter extends RecyclerView.Adapter<MovieBookmarkAdapter.ViewHolder> {
    //attribute
    private final List<FavoriteMovie> movieList;
    private OnItemClickListener clickListener;



    public MovieBookmarkAdapter(List<FavoriteMovie> movieList){
        this.movieList = movieList;
    }
    public void setClickListener(OnItemClickListener clickListener) {
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row, parent, false);
        return new ViewHolder(v, clickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull  MovieBookmarkAdapter.ViewHolder holder, int position) {
        holder.onBind(movieList.get(position));
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        FavoriteMovie favoriteMovie;
        OnItemClickListener onItemClick;
        ImageView ivCover, ivIsBookmarked;
        TextView tvTitle, tvReleaseDate;
        DonutProgress dpUserScore;

        public ViewHolder(@NonNull  View itemView, OnItemClickListener onItemClick) {
            super(itemView);
            itemView.setOnClickListener(this);
            ivCover = itemView.findViewById(R.id.photo);
            ivIsBookmarked= itemView.findViewById(R.id.iv_is_bookmarked);
            tvTitle = itemView.findViewById(R.id.tvJudul);
            dpUserScore= itemView.findViewById(R.id.dp_user_score);
            tvReleaseDate= itemView.findViewById(R.id.tv_release_date);
            this.onItemClick = onItemClick;
        }


        @Override
        public void onClick(View view) {
            onItemClick.onClick(getAdapterPosition());
        }

        public void onBind(FavoriteMovie favoriteMovie) {
            this.favoriteMovie= favoriteMovie;

            String posterUri= Consts.POSTERBASEURL + favoriteMovie.getImgPath();
            Glide.with(this.itemView.getContext()).load(posterUri).into(ivCover);

            tvTitle.setText(favoriteMovie.getTitle());
            tvReleaseDate.setText(favoriteMovie.getReleaseDate());
            ivIsBookmarked.setImageResource(R.drawable.ic_action_bar_bookmark_24);
            dpUserScore.setDonut_progress(setUserScore(favoriteMovie.getVoteAverage()));
        }

        public String setUserScore(double number){
            int numberInteger= (int) (number*10);
            return String.valueOf(numberInteger);
        }
    }

}
