package me.elmira.flickster.playmovie;

import me.elmira.flickster.BasePresenter;
import me.elmira.flickster.BaseView;
import me.elmira.flickster.model.MovieVideo;

/**
 * Created by elmira on 3/9/17.
 */

public interface PlayYoutubeContract {

    interface View extends BaseView<Presenter>{

        void playYoutubeVideo(MovieVideo movieVideo);

        void movieVideoNotLoaded();
    }

    interface Presenter extends BasePresenter{

        void loadMovieVideoUrl(long id);
    }
}
