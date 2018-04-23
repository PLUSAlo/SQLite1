package com.own.sqlite1.adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.own.sqlite1.R;
import com.own.sqlite1.model.Movie;

import java.util.List;

/**
 * Created by alo_m on 11/04/2018.
 */

public class MovieAdapter extends RecyclerView.Adapter <MovieAdapter.MovieViewHolder>
        implements View.OnClickListener {
    List<Movie> movies;
    View.OnClickListener listener;

    public MovieAdapter(List<Movie> movies){
        this.movies=movies;
    }
    //getter and setter de listener
    public View.OnClickListener getListener() {
        return listener;
    }

    public void setListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //se inflael cardview al reciclerview
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_cell_layout,parent,false);
        MovieViewHolder holder=new MovieViewHolder(view);
        view.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
        System.out.println(movies.get(position));
        holder.tvMovieName.setText(movies.get(position).getName());
        holder.tvMovieDirector.setText(movies.get(position).getDirector());
        holder.tvMovieClasification.setText(movies.get(position).getClasification());
        holder.tvMovieYear.setText(String.valueOf(movies.get(position).getYear()));
        holder.tvMovieDuration.setText(String.valueOf(movies.get(position).getDuration()));
        holder.setListener(this);
    }


    @Override
    public int getItemCount() {
        return movies.size();
    }

    @Override
    public void onClick(View v) {
        if(listener!=null){
            listener.onClick(v);
        }
    }

    public static class MovieViewHolder extends  RecyclerView.ViewHolder implements View.OnClickListener{
        CardView cvMovie;
        TextView tvMovieName;
        TextView tvMovieDirector;
        TextView tvMovieClasification;
        TextView tvMovieYear;
        TextView tvMovieDuration;
        ImageButton btEditMovie;
        ImageButton btDeleteMovie;
        View.OnClickListener listener;




        public MovieViewHolder(View itemView) {
            super(itemView);
            cvMovie =(CardView) itemView.findViewById(R.id.crv_movie);
            tvMovieName=(TextView)itemView.findViewById(R.id.txv_movie);
            tvMovieDirector=(TextView)itemView.findViewById(R.id.txv_director_movie);
            tvMovieClasification=(TextView)itemView.findViewById(R.id.txv_clasification);
            tvMovieYear=(TextView)itemView.findViewById(R.id.txv_year);
            tvMovieDuration=(TextView)itemView.findViewById(R.id.txv_duration);

            btEditMovie=(ImageButton) itemView.findViewById(R.id.btn_edit_movie);
            btDeleteMovie=(ImageButton) itemView.findViewById(R.id.btn_delete_movie);
            btEditMovie.setOnClickListener(this);
            btDeleteMovie.setOnClickListener(this);


        }

        @Override
        public void onClick(View v) {
            if (listener!=null){
                listener.onClick(v);
            }
        }

        public void setListener(View.OnClickListener listener){
            this.listener=listener;

        }
    }



}
