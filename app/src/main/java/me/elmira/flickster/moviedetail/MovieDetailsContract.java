package me.elmira.flickster.moviedetail;

import me.elmira.flickster.BasePresenter;
import me.elmira.flickster.BaseView;
import me.elmira.flickster.model.Movie;

/**
 * Created by elmira on 3/10/17.
 */

public interface MovieDetailsContract {

    interface View extends BaseView<Presenter> {

        void showMovieDetails(Movie movie);

        void showMovieDetailsNotLoaded();

        //void displayMovieVideoPreview(MovieVideo video);

        //void movieVideoNotLoaded();
    }

    interface Presenter extends BasePresenter {

        void getMovieDetails(long id);

        //void loadMovieVideo(long id);
    }
}
