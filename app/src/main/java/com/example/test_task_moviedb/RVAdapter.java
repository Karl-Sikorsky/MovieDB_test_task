package com.example.test_task_moviedb;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.test_task_moviedb.POJO.Result;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.FilmViewHolder> {

    public static class FilmViewHolder extends RecyclerView.ViewHolder {

        TextView filmName;
        TextView filmRate;
        ImageView filmPhoto;
        ConstraintLayout cardLayout;

        FilmViewHolder(View itemView) {
            super(itemView);
            itemView.setTag(this);
            cardLayout = (ConstraintLayout) itemView.findViewById(R.id.card_layout);
            filmName = (TextView) itemView.findViewById(R.id.textViewTitle);
            filmRate = (TextView) itemView.findViewById(R.id.textViewRate);
            filmPhoto = (ImageView) itemView.findViewById(R.id.imageViewFilmPicture);

        }
    }

    List<Result> films;
    Context context;

    RVAdapter(List<Result> films, Context context) {
        this.films = films;
        this.context = context;
    }


    @NonNull
    @Override
    public FilmViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.film_card, viewGroup, false);
        FilmViewHolder fvh = new FilmViewHolder(v);

        return fvh;
    }

    @Override
    public void onBindViewHolder(FilmViewHolder filmViewHolder, final int position) {
        if (films.get(position).getPopularity() > 5)
            filmViewHolder.cardLayout.setBackgroundColor(context.getResources().getColor(R.color.okFilmColor));
        if (films.get(position).getPopularity() > 6)
            filmViewHolder.cardLayout.setBackgroundColor(context.getResources().getColor(R.color.goodFilmColor));
        if (films.get(position).getPopularity() > 7)
            filmViewHolder.cardLayout.setBackgroundColor(context.getResources().getColor(R.color.greatFilmColor));
        filmViewHolder.filmName.setText(films.get(position).getTitle());
        filmViewHolder.filmRate.setText(films.get(position).getVoteAverage().toString() + "/10 (" + films.get(position).getVoteCount() + " votes)");
        Picasso.get()
                .load("http://image.tmdb.org/t/p/w185/" + films.get(position).getPosterPath())

                .placeholder(context.getResources().getDrawable(R.drawable.no_image))
                .error(context.getResources().getDrawable(R.drawable.no_image))
                .fit()
                .into(filmViewHolder.filmPhoto);
        filmViewHolder.cardLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, FilmActivity.class);
                intent.putExtra("image_url", "http://image.tmdb.org/t/p/w342/" + films.get(position).getPosterPath());
                intent.putExtra("film_name", films.get(position).getTitle());
                intent.putExtra("film_description", films.get(position).getOverview());
                context.startActivity(intent);
            }

        });
    }

    @Override
    public int getItemCount() {
        return films.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {

        super.onAttachedToRecyclerView(recyclerView);
    }
}