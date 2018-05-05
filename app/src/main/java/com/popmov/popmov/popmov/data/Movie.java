package com.popmov.popmov.popmov.data;

import android.os.Parcel;
import android.os.Parcelable;

public class Movie implements Parcelable {

    private int id;
    private String title;
    private String imageUrl;
    private String synopsis;
    private double rating;
    private String releaseDate;

    public Movie(int id, String title, String imageUrl, String synopsis, double rating, String releaseDate) {
        this.id = id;
        this.title = title;
        this.imageUrl = imageUrl;
        this.synopsis = synopsis;
        this.rating = rating;
        this.releaseDate = releaseDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeString(imageUrl);
        dest.writeString(synopsis);
        dest.writeDouble(rating);
        dest.writeString(releaseDate);
    }

    public static final Parcelable.Creator<Movie> CREATOR =
            new Parcelable.Creator<Movie>() {

                @Override
                public Movie createFromParcel(Parcel in) {
                    return new Movie(in);
                }

                @Override
                public Movie[] newArray(int size) {
                    return new Movie[size];
                }
            };

    private Movie(Parcel in) {
        id = in.readInt();
        title = in.readString();
        imageUrl = in.readString();
        synopsis = in.readString();
        rating = in.readDouble();
        releaseDate = in.readString();
    }
}