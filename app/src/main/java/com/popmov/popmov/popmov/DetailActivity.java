package com.popmov.popmov.popmov;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.popmov.popmov.popmov.data.Movie;
import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {

    private TextView textViewtitle;
    private ImageView imageViewimageUrl;
    private TextView textViewsynopsis;
    private TextView textViewrating;
    private TextView textViewreleaseDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        textViewtitle = (TextView)findViewById(R.id.textView_title);
        imageViewimageUrl= (ImageView) findViewById(R.id.imageView_movie_image);;
        textViewsynopsis= (TextView)findViewById(R.id.textView_synopsis);
        textViewrating= (TextView)findViewById(R.id.textView_rating);
        textViewreleaseDate= (TextView)findViewById(R.id.textView_release_date);

        Movie movieDetails = getIntent().getParcelableExtra("movieDetails");
        String title = movieDetails.getTitle();
        String imageUrl = movieDetails.getImageUrl();
        String synopsis = movieDetails.getSynopsis();
        double rating = movieDetails.getRating();
        String releaseDate = movieDetails.getReleaseDate();

        textViewtitle.setText(title);

        String path = MoviesAdapter.BASE_URL_IMAGE + imageUrl;
        Picasso.get()
                .load(path)
                .placeholder(R.mipmap.poster)
                .error(R.mipmap.error)
                .into(imageViewimageUrl);
        textViewsynopsis.setText(synopsis);
        textViewrating.setText(String.valueOf(rating));
        textViewreleaseDate.setText(releaseDate);
    }
}
