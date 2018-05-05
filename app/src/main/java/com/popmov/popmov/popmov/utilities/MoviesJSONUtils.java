package com.popmov.popmov.popmov.utilities;

import android.content.Context;

import com.popmov.popmov.popmov.data.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MoviesJSONUtils {

    public static Movie[] getMovies(String movieJsonString) throws JSONException {
        JSONObject jsonMovieString = new JSONObject(movieJsonString);
        JSONArray jsonArrayResults = jsonMovieString.getJSONArray("results");
        Movie[] movies = new Movie[jsonArrayResults.length()];
        for (int i = 0; i < jsonArrayResults.length(); i++){
            JSONObject jsonMovie = jsonArrayResults.getJSONObject(i);
            int id = jsonMovie.getInt("id");
            String title = jsonMovie.getString("title");
            double rating = jsonMovie.getDouble("vote_average");
            String imageUrl = jsonMovie.getString("poster_path");
            String synopsis = jsonMovie.getString("overview");
            String releaseDate = jsonMovie.getString("release_date");
            movies[i] = new Movie(id, title, imageUrl, synopsis, rating, releaseDate);
        }
        return movies;
    }
}