package me.elmira.flickster.movies;

import java.util.List;

import me.elmira.flickster.BasePresenter;
import me.elmira.flickster.BaseView;
import me.elmira.flickster.model.Movie;

/**
 * Created by elmira on 3/7/17.
 */

public interface MovieContract {

    interface View extends BaseView<Presenter> {

        void showMovies(List<Movie> movies);

        void showNoMovies();

        void showMovieDetails(long id);

        void playMovieVideo(long id);
    }

    interface Presenter extends BasePresenter, MovieAdapter.OnMovieClickListener{

        void loadMovies(boolean forceUpdate);

    }
}
