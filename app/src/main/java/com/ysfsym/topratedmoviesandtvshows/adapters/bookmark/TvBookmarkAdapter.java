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
import com.ysfsym.topratedmoviesandtvshows.dbs.table.FavoriteTv;
import com.ysfsym.topratedmoviesandtvshows.networks.Consts;

import java.util.List;

public class TvBookmarkAdapter extends RecyclerView.Adapter<TvBookmarkAdapter.ViewHolder> {
    //attribute adapter
    private final List<FavoriteTv> tvList;
    private OnItemClickListener clickListener;

    public TvBookmarkAdapter(List<FavoriteTv> tvList){
        this.tvList = tvList;
    }

    public void setClickListener(OnItemClickListener clickListener){
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row, parent, false);
        return new ViewHolder(v, clickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull  ViewHolder holder, int position) {
        Glide.with(holder.itemView.getContext())
                .load(Consts.POSTERBASEURL + tvList.get(position).getImgPath())
                .apply(RequestOptions.bitmapTransform(new RoundedCorners(25)))
                .into(holder.ivCover);
        holder.tvTitle.setText(tvList.get(position).getTitle());
        holder.dpUserScore.setDonut_progress(setUserScore(tvList.get(position).getVoteAverage()));
        holder.tvReleaseDate.setText(tvList.get(position).getReleaseDate());
        holder.ivIsBookmarked.setImageResource(R.drawable.ic_action_bar_bookmark_24);
    }

    public String setUserScore(double number){
        int numberInteger= (int) (number*10);
        return String.valueOf(numberInteger);
    }

    @Override
    public int getItemCount() {
        return tvList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
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
    }
}
