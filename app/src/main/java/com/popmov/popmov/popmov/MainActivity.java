package com.popmov.popmov.popmov;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.popmov.popmov.popmov.data.Movie;
import com.popmov.popmov.popmov.utilities.MoviesJSONUtils;
import com.popmov.popmov.popmov.utilities.NetworkUtils;

import org.json.JSONException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity implements MoviesAdapter.ListItemOnClickHandler {

    private static final String API_KEY = "b9c396b11187cafde092bd288f0909c1";
    private MoviesAdapter movieAdapter;
    private RecyclerView recyclerView;
    private String defaultSortType = "Most Popular";

    private static final String STRING_URL_POPULAR = "http://api.themoviedb.org/3/movie/popular?api_key=" + API_KEY;
    private static final String STRING_URL_TOP_RATED = "http://api.themoviedb.org/3/movie/top_rated?api_key=" + API_KEY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView_movies);
        RecyclerView.LayoutManager layoutManager
                = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        movieAdapter = new MoviesAdapter(this,MainActivity.this);
        recyclerView.setAdapter(movieAdapter);
        if (!isOnline()) {
            Toast.makeText(this, "No Connection", Toast.LENGTH_SHORT).show();
        } else {
            loadMovies(defaultSortType);
        }
    }

    private void loadMovies(String sortType) {

        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        String orderBy = sharedPrefs.getString("Order By" , sortType);
     
        String urlToExecute = " ";
        if (orderBy.equals("Most Popular")) {
            urlToExecute = STRING_URL_POPULAR;
        } else if (orderBy.equals("Top Rated")) {
            urlToExecute = STRING_URL_TOP_RATED;
        }
        new FetchMovieTask().execute(urlToExecute);
    }



    @Override
    public void onItemClickListener(int id, String title, String imageUrl, String synopsis, double rating, String releaseDate) {
        Movie movieDetails = new Movie(id, title, imageUrl, synopsis, rating, releaseDate);
        Intent intent = new Intent(MainActivity.this, DetailActivity.class);
        intent.putExtra("movieDetails", movieDetails);
        startActivity(intent);
    }

    @SuppressLint("StaticFieldLeak")
    public class FetchMovieTask extends AsyncTask<String, Void, Movie[]> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Movie[] doInBackground(String... params) {
            if (params.length == 0) {
                return null;
            }
            String urlString = params[0];
            URL movieRequestUrl = NetworkUtils.buildUrl(urlString);
            try {
                String jsonMovieResponse = NetworkUtils
                        .getResponseFromHttpUrl(movieRequestUrl);
                return MoviesJSONUtils.getMovies(jsonMovieResponse);
            } catch (MalformedURLException e) {
                e.printStackTrace();
                return null;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(Movie[] movies) {

            if (movies != null) {
                movieAdapter.setMovieData(movies);
            } else {
                Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        try {
            NetworkInfo netInfo = cm != null ? cm.getActiveNetworkInfo() : null;
            return netInfo != null && netInfo.isConnectedOrConnecting();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return false;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_top_rated:
                String sortByRate = "Top Rated";
                if (!isOnline()){
                    Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
                } else  {
                    loadMovies(sortByRate);
                }
                return true;

            case R.id.action_most_popular:
                String sortByMostPopular = "Most Popular";
                if (!isOnline()){
                    Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
                } else  {
                    loadMovies(sortByMostPopular);
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
