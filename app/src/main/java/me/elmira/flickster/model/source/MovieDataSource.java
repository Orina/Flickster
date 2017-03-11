package me.elmira.flickster.model.source;

import java.util.List;

import me.elmira.flickster.model.Movie;
import me.elmira.flickster.model.MovieVideo;

/**
 * Created by elmira on 3/7/17.
 */

public interface MovieDataSource {

    interface LoadMoviesCallback {
        void onMoviesLoaded(List<Movie> movies);

        void onMoviesNotLoaded();
    }

    interface GetMovieVideoCallback {
        void onMovieVideoLoaded(MovieVideo movieVideo);

        void onMovieVideoNotLoaded();
    }

    interface GetMovieDetailsCallback {
        void onMovieDetailsLoaded(Movie movieDetails);

        void onMovieDetailsNotLoaded();
    }

    void loadMovies(LoadMoviesCallback callback);

    void getMovieVideo(long id, GetMovieVideoCallback callback);

    void getMovieDetails(long id, GetMovieDetailsCallback callback);

    void destroy();

    void refreshMovies();
}