package me.elmira.flickster.model;

import java.util.List;

/**
 * Created by elmira on 3/10/17.
 */

public class MovieDetails {

    private String mReleaseDate;
    private int mRuntime;
    private List<MovieGenre> mGenres;

    public MovieDetails(String mReleaseDate, int mRuntime, List<MovieGenre> mGenres) {
        this.mReleaseDate = mReleaseDate;
        this.mRuntime = mRuntime;
        this.mGenres = mGenres;
    }

    public String getReleaseDate() {
        return mReleaseDate;
    }

    public int getRuntime() {
        return mRuntime;
    }

    public List<MovieGenre> getGenres() {
        return mGenres;
    }

    public String getGenresAsString(){
        if (mGenres==null || mGenres.size()==0) return "";
        StringBuilder sb = new StringBuilder();
        for (int i=0; i< mGenres.size(); i++){
            sb.append(mGenres.get(i).getmName());
            sb.append(", ");
        }
        sb.delete(sb.length()-2, sb.length());
        return sb.toString();
    }
}
