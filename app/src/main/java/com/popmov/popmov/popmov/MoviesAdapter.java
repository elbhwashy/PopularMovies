package com.popmov.popmov.popmov;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.popmov.popmov.popmov.data.Movie;
import com.squareup.picasso.Picasso;


class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MoviesViewHolder> {
    private final Context context;
    private Movie[] moviesData;
    public static final String BASE_URL_IMAGE = "http://image.tmdb.org/t/p/w342";

    private final MoviesAdapterOnClickHandler moviesAdapterOnClickHandler;

    public interface MoviesAdapterOnClickHandler {
        void onItemClickListener(int id, String title, String imageUrl, String synopsis, double rating, String releaseDate);
    }

    public MoviesAdapter(MoviesAdapterOnClickHandler clickHandler,Context context) {
        moviesAdapterOnClickHandler = clickHandler;
        this.context=context;
    }

    public class MoviesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public final ImageView imageView;

        public MoviesViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_view_movie);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();

            int id = moviesData[adapterPosition].getId();
            String title = moviesData[adapterPosition].getTitle();
            String imageUrl = moviesData[adapterPosition].getImageUrl();
            String synopsis = moviesData[adapterPosition].getSynopsis();
            double rating = moviesData[adapterPosition].getRating();
            String releaseDate = moviesData[adapterPosition].getReleaseDate();

            moviesAdapterOnClickHandler.onItemClickListener(id, title, imageUrl, synopsis, rating, releaseDate);
        }
    }

    @Override
    public MoviesViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutForItem = R.layout.movies_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(layoutForItem, viewGroup, false);
        return new MoviesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MoviesViewHolder holder, int position) {
        String path = BASE_URL_IMAGE + moviesData[position].getImageUrl();
        Picasso.get()
                .load(path)
                .placeholder(R.mipmap.poster)
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        if (null == moviesData) {
            return 0;
        } else {
            return moviesData.length;
        }
    }

    public void setMovieData(Movie[] movieData) {
        moviesData = movieData;
        notifyDataSetChanged();
    }
}
