package com.example.movies.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movies.R;
import com.example.movies.data.Trailer;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.TrailerViewHolder> {

    private ArrayList<Trailer> trailers=new ArrayList<>();

    private SetClickOnVideo setClickOnVideo;

    public interface SetClickOnVideo{
        void clickOnVideo(String url);
    }

    @NonNull
    @Override
    public TrailerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.trailer_item,parent,false);
        return new TrailerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TrailerViewHolder holder, int position) {
        Trailer trailer=trailers.get(position);
        holder.nameTrailer.setText(trailer.getName());
    }

    @Override
    public int getItemCount() {
        return trailers.size();
    }

    public void setTrailers(ArrayList<Trailer> trailers) {
        this.trailers = trailers;
        notifyDataSetChanged();
    }

    class TrailerViewHolder extends RecyclerView.ViewHolder {

        private TextView nameTrailer;


        public TrailerViewHolder(@NonNull final View itemView) {
            super(itemView);
            nameTrailer = itemView.findViewById(R.id.textViewOfVideo);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (setClickOnVideo!=null){
                        setClickOnVideo.clickOnVideo(trailers.get(getAdapterPosition()).getKey());
                    }
                }
            });
        }
    }

    public void setSetClickOnVideo(SetClickOnVideo setClickOnVideo) {
        this.setClickOnVideo = setClickOnVideo;
    }
}
