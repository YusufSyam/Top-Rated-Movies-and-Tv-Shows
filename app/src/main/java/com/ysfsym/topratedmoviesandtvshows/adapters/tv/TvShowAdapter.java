package com.ysfsym.topratedmoviesandtvshows.adapters.tv;

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
import com.ysfsym.topratedmoviesandtvshows.models.tv.TvShow;
import com.ysfsym.topratedmoviesandtvshows.networks.Consts;

import java.util.List;

public class TvShowAdapter extends RecyclerView.Adapter<TvShowAdapter.ViewHolder> {
    private List<TvShow> tvShowList;
    Context mContext;

    private OnItemClickListener<Integer> clickListener;

    public TvShowAdapter(Context context){
        this.mContext= context;
    }

    public void setClickListener(OnItemClickListener<Integer> clickListener){
        this.clickListener = clickListener;
    }

    public void setTvShowList(List<TvShow> tvShowList){
        this.tvShowList = tvShowList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TvShowAdapter.ViewHolder holder, int position) {

        holder.onBind(tvShowList.get(position), mContext);
    }

    @Override
    public int getItemCount() {
        return tvShowList.size();
    }

    public void addNewTvShow(List<TvShow> newTvShow){
        tvShowList.addAll(newTvShow);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TvShow tvShow;
        TextView tvTitle, tvFirtAirDate;
        DonutProgress dpUserScore;
        ImageView photo, ivIsBookmarked;
        Integer tvId;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            tvTitle = itemView.findViewById(R.id.tvJudul);
            photo = itemView.findViewById(R.id.photo);
            dpUserScore= itemView.findViewById(R.id.dp_user_score);
            tvFirtAirDate= itemView.findViewById(R.id.tv_release_date);
            ivIsBookmarked= itemView.findViewById(R.id.iv_is_bookmarked);
        }

        public void onBind(TvShow tvShow, Context context){
            String uri = Consts.POSTERBASEURL + tvShow.getPosterImage();
            this.tvShow = tvShow;
            tvId = tvShow.getId();
            tvTitle.setText(tvShow.getName());
            dpUserScore.setDonut_progress(setUserScore(tvShow.getVoteAverage()));
            tvFirtAirDate.setText(tvShow.getFirstAirDate());
            Glide.with(this.itemView.getContext()).load(uri).into(photo);

            ivIsBookmarked.setImageResource(AppDatabase.getInstance(mContext).favoriteDao().isTvExists(tvId)? R.drawable.ic_action_bar_bookmark_24 : R.drawable.ic_action_bar_bookmark_border_24);
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
