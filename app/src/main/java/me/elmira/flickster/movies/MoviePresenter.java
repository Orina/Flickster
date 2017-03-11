package me.elmira.flickster.movies;

import java.util.List;

import me.elmira.flickster.model.Movie;
import me.elmira.flickster.model.source.MovieDataSource;

/**
 * Created by elmira on 3/7/17.
 */

public class MoviePresenter implements MovieContract.Presenter {

    private MovieContract.View mView;
    private MovieDataSource mMovieDataSource;

    private boolean mFirstLoad = true;

    public MoviePresenter(MovieContract.View mView, MovieDataSource mMovieDataSource) {
        this.mMovieDataSource = mMovieDataSource;
        this.mView = mView;
        this.mView.setPresenter(this);
    }

    @Override
    public void start() {
        loadMovies(false);
    }

    @Override
    public void loadMovies(boolean forceUpdate) {

        if (forceUpdate || mFirstLoad) {
            mMovieDataSource.refreshMovies();
        }

        mMovieDataSource.loadMovies(new MovieDataSource.LoadMoviesCallback() {
            @Override
            public void onMoviesLoaded(List<Movie> movies) {
                mView.showMovies(movies);
            }

            @Override
            public void onMoviesNotLoaded() {
                mView.showNoMovies();
            }
        });
        mFirstLoad = false;
    }

    @Override
    public void onMovieClick(Movie movie) {
        if (movie.isPopular()) {
            mView.playMovieVideo(movie.getId());
        }
        else {
            mView.showMovieDetails(movie.getId());
        }
    }
}
