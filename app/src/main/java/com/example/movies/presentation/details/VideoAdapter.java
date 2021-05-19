package com.example.movies.presentation.details;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movies.R;
import com.example.movies.domain.models.Video;
import com.example.movies.utils.Constants;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.TrailerViewHolder> {
    private List<Video> videos = new ArrayList<>();
    private OnVideoClickListener onVideoClickListener;

    public interface OnVideoClickListener {
        void clickOnVideo(String url);
    }

    @NonNull
    @Override
    public TrailerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.trailer_item, parent, false);
        return new TrailerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TrailerViewHolder holder, int position) {
        Video trailer = videos.get(position);
        holder.nameTrailer.setText(trailer.getName());
    }

    @Override
    public int getItemCount() {
        return videos.size();
    }

    public void setVideos(List<Video> videos) {
        this.videos = videos;
        notifyDataSetChanged();
    }

    public List<Video> getVideos() {
        return videos;
    }

    class TrailerViewHolder extends RecyclerView.ViewHolder {
        private final TextView nameTrailer;

        public TrailerViewHolder(@NonNull final View itemView) {
            super(itemView);
            nameTrailer = itemView.findViewById(R.id.textViewOfVideo);
            itemView.setOnClickListener(v -> {
                if (onVideoClickListener != null) {
                    System.out.println("url() = " + videos.get(getAbsoluteAdapterPosition()).getKey());
                    onVideoClickListener.clickOnVideo(Constants.BASE.YOUTUBE_URL + videos.get(getAbsoluteAdapterPosition()).getKey());
                }
            });
        }
    }

    public void setOnVideoClickListener(OnVideoClickListener onVideoClickListener) {
        this.onVideoClickListener = onVideoClickListener;
    }
}
