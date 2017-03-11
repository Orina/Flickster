package me.elmira.flickster.model;

/**
 * Created by elmira on 3/7/17.
 */

public class Movie {

    private long mId;
    private String mTitle;
    private String mOverview;
    private String mPosterPath;
    private String mBackdropPath;
    private double mVoteAverage;

    private MovieVideo mMovieVideo;
    private MovieDetails mMovieDetails;

    public Movie(MovieDetails mMovieDetails) {
        this.mMovieDetails = mMovieDetails;
    }

    private Movie(long mId, String mTitle, String mOverview, String mPosterPath, String mBackdropPath, double voteAverage) {
        this.mId = mId;
        this.mTitle = mTitle;
        this.mOverview = mOverview;
        this.mPosterPath = mPosterPath;
        this.mBackdropPath = mBackdropPath;
        this.mVoteAverage = voteAverage;
    }

    public static class Builder {
        private long mId;
        private String mTitle;
        private String mOverview;
        private String mPosterPath;
        private String mBackdropPath;
        private double mVoteAverage;

        public Builder() {

        }

        public Builder id(long id) {
            this.mId = id;
            return this;
        }

        public Builder title(String title) {
            this.mTitle = title;
            return this;
        }

        public Builder overview(String overview) {
            this.mOverview = overview;
            return this;
        }

        public Builder posterPath(String posterPath) {
            this.mPosterPath = posterPath;
            return this;
        }

        public Builder backdropPath(String backdropPath) {
            this.mBackdropPath = backdropPath;
            return this;
        }

        public Builder voteAverage(double voteAverage) {
            this.mVoteAverage = voteAverage;
            return this;
        }

        public Movie build() {
            return new Movie(mId, mTitle, mOverview, mPosterPath, mBackdropPath, mVoteAverage);
        }

        public void clear() {
            this.mId = -1;
            mTitle = mOverview = mPosterPath = mBackdropPath = null;
            mVoteAverage = 0;
        }
    }

    public long getId() {
        return mId;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getOverview() {
        return mOverview;
    }

    public String getPosterPath() {
        return mPosterPath;
    }

    public String getBackdropPath() {
        return mBackdropPath;
    }

    public boolean isPopular() {
        return mVoteAverage > 7;
    }

    public MovieVideo getMovieVideo() {
        return mMovieVideo;
    }

    public void setMovieVideo(MovieVideo mMovieVideo) {
        this.mMovieVideo = mMovieVideo;
    }

    public MovieDetails getMovieDetails() {
        return mMovieDetails;
    }

    public void setMovieDetails(MovieDetails mMovieDetails) {
        this.mMovieDetails = mMovieDetails;
    }

    public String getPosterImageUrl() {
        return "https://image.tmdb.org/t/p/w342" + mPosterPath;
    }

    public String getBackdropImageUrl() {
        return "http://image.tmdb.org/t/p/w780" + mBackdropPath;
    }

    public String getLongBackdropImageUrl() {
        return "http://image.tmdb.org/t/p/w1280" + mBackdropPath;
    }
}
