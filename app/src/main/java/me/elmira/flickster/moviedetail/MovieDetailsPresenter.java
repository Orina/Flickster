package me.elmira.flickster.moviedetail;

import me.elmira.flickster.model.Movie;
import me.elmira.flickster.model.source.MovieDataSource;

/**
 * Created by elmira on 3/10/17.
 */

public class MovieDetailsPresenter implements MovieDetailsContract.Presenter {

    private MovieDetailsContract.View mView;
    private MovieDataSource mMovieDataSource;

    public MovieDetailsPresenter(MovieDetailsContract.View view, MovieDataSource mMovieDataSource) {
        this.mView = view;
        this.mMovieDataSource = mMovieDataSource;
        this.mView.setPresenter(this);
    }

    @Override
    public void start() {
        //ignore
    }

    @Override
    public void getMovieDetails(long id) {
        mMovieDataSource.getMovieDetails(id, new MovieDataSource.GetMovieDetailsCallback() {
            @Override
            public void onMovieDetailsLoaded(Movie movieDetails) {
                mView.showMovieDetails(movieDetails);
            }

            @Override
            public void onMovieDetailsNotLoaded() {
                mView.showMovieDetailsNotLoaded();
            }
        });
    }
}