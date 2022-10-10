package com.trungdunghoang125.mymovie.ui.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.trungdunghoang125.mymovie.R;
import com.trungdunghoang125.mymovie.remote.model.MovieModel;
import com.trungdunghoang125.mymovie.utils.Constant;

import java.util.List;

/**
 * Created by trungdunghoang125 on 07/10/2022
 */
public class MovieListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<MovieModel> movieModelList;
    private ItemClick itemClick;
    private static final int POP_MOVIES_LIST = 1;
    private static final int SEARCH_MOVIES_LIST = 2;

    public MovieListAdapter(ItemClick itemClick) {
        this.itemClick = itemClick;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setMovieModelList(List<MovieModel> movieModelList) {
        this.movieModelList = movieModelList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = null;
        if (viewType == POP_MOVIES_LIST) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.movie_pop_item, parent, false);
            return new PopMovieViewHolder(view);
        }
        else {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.movie_search_item, parent, false);
            return new MovieSearchViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        int viewType = holder.getItemViewType();
        MovieModel movieModel = movieModelList.get(position);

        if (viewType == 1) {
            ((PopMovieViewHolder) holder).bind(movieModel, holder);
            holder.itemView.setOnClickListener(view -> {
                itemClick.onItemClick(holder.getAdapterPosition());
            });
        }
        else {
            ((MovieSearchViewHolder) holder).bind(movieModel, holder);
            holder.itemView.setOnClickListener(view -> {
                itemClick.onItemClick(holder.getAdapterPosition());
            });
        }
    }

    @Override
    public int getItemCount() {
        if (movieModelList != null) {
            return movieModelList.size();
        }
        return 0;
    }

    @Override
    public int getItemViewType(int position) {
        if (Constant.IS_POPULAR) {
            return POP_MOVIES_LIST;
        }
        else return SEARCH_MOVIES_LIST;
    }

    public MovieModel getSelectedMovie(int pos) {
        if (movieModelList != null) {
            return movieModelList.get(pos);
        }
        return null;
    }

    static class PopMovieViewHolder extends RecyclerView.ViewHolder {
        private TextView popMovieTitle;
        private ImageView popMoviePoster;

        public PopMovieViewHolder(@NonNull View itemView) {
            super(itemView);
            popMovieTitle = itemView.findViewById(R.id.pop_movie_title);
            popMoviePoster = itemView.findViewById(R.id.pop_movie_img);
        }

        public void bind(MovieModel movieModel, RecyclerView.ViewHolder holder) {
            popMovieTitle.setText(movieModel.getTitle());
            Glide.with(holder.itemView.getContext())
                    .load(Constant.BASE_IMG_URL + movieModel.getPosterPath())
                    .into(popMoviePoster);
        }
    }

    static class MovieSearchViewHolder extends RecyclerView.ViewHolder {
        private TextView title, release_date;
        private ImageView moviePoster;
        private RatingBar rating;

        public MovieSearchViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.movie_title);
            release_date = itemView.findViewById(R.id.release_date);
            moviePoster = itemView.findViewById(R.id.movie_poster);
            rating = itemView.findViewById(R.id.rating);
        }

        public void bind(MovieModel movieModel, RecyclerView.ViewHolder holder) {
            title.setText(movieModel.getTitle());
            release_date.setText(movieModel.getReleaseDate());
            rating.setRating(movieModel.getVoteAverage() / 2);
            // Glide to load image
            String url = movieModel.getPosterPath();
            Glide.with(holder.itemView.getContext())
                    .load(Constant.BASE_IMG_URL + url)
                    .into(moviePoster);
        }
    }
}
