package me.elmira.flickster.playmovie;

import me.elmira.flickster.model.MovieVideo;
import me.elmira.flickster.model.source.MovieDataSource;

/**
 * Created by elmira on 3/9/17.
 */

public class PlayYoutubePresenter implements PlayYoutubeContract.Presenter {

    private PlayYoutubeContract.View mView;
    private MovieDataSource mMovieDataSource;

    public PlayYoutubePresenter(PlayYoutubeContract.View mView, MovieDataSource mMovieDataSource) {
        this.mView = mView;
        this.mMovieDataSource = mMovieDataSource;
        this.mView.setPresenter(this);
    }

    @Override
    public void start() {
        // do nothing
    }

    @Override
    public void loadMovieVideoUrl(long id) {

        mMovieDataSource.getMovieVideo(id, new MovieDataSource.GetMovieVideoCallback() {
            @Override
            public void onMovieVideoLoaded(MovieVideo movieVideo) {
                mView.playYoutubeVideo(movieVideo);
            }

            @Override
            public void onMovieVideoNotLoaded() {
                mView.movieVideoNotLoaded();
            }
        });
    }
}
