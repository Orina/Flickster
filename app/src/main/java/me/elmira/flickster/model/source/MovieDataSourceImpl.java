package me.elmira.flickster.model.source;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import me.elmira.flickster.model.Movie;
import me.elmira.flickster.model.MovieVideo;
import me.elmira.flickster.model.source.remote.MovieDataSourceRemote;

/**
 * Created by elmira on 3/7/17.
 */

public class MovieDataSourceImpl implements MovieDataSource {

    private static final String LOG_TAG = "MovieDataSourceImpl";

    private static MovieDataSourceImpl instance;

    private HashMap<Long, Movie> mMemoryCache;

    private MovieDataSourceRemote remoteMovieSource;

    /**
     * Marks the cache as invalid, to force an update the next time data is requested.
     */
    boolean mCacheIsDirty = false;

    private MovieDataSourceImpl(MovieDataSourceRemote remoteMovieSource) {
        mMemoryCache = new LinkedHashMap<>();
        this.remoteMovieSource = remoteMovieSource;
    }

    public static MovieDataSourceImpl getInstance(MovieDataSourceRemote remoteMovieSource) {
        if (instance == null) {
            instance = new MovieDataSourceImpl(remoteMovieSource);
        }
        return instance;
    }

    public void destroy() {
        remoteMovieSource.destroy();
        instance = null;
    }

    @Override
    public void loadMovies(final LoadMoviesCallback callback) {
        if (!mCacheIsDirty && mMemoryCache.size() > 0) {
            callback.onMoviesLoaded(new ArrayList<Movie>(mMemoryCache.values()));
            return;
        }
        remoteMovieSource.loadMovies(new LoadMoviesCallback() {
            @Override
            public void onMoviesLoaded(List<Movie> movies) {
                refreshCache(movies);
                callback.onMoviesLoaded(movies);
            }

            @Override
            public void onMoviesNotLoaded() {
                callback.onMoviesNotLoaded();
            }
        });
    }

    @Override
    public void refreshMovies() {
        mCacheIsDirty = true;
    }

    @Override
    public void getMovieVideo(final long id, final GetMovieVideoCallback callback) {
        if (!mMemoryCache.containsKey(id)) {
            callback.onMovieVideoNotLoaded();
        }
        Movie movie = mMemoryCache.get(id);
        if (movie.getMovieVideo() != null) {
            callback.onMovieVideoLoaded(movie.getMovieVideo());
            return;
        }
        remoteMovieSource.getMovieVideo(id, new GetMovieVideoCallback() {
            @Override
            public void onMovieVideoLoaded(MovieVideo movieVideo) {
                if (mMemoryCache.containsKey(id)) {
                    mMemoryCache.get(id).setMovieVideo(movieVideo);
                }
                callback.onMovieVideoLoaded(movieVideo);
            }

            @Override
            public void onMovieVideoNotLoaded() {
                callback.onMovieVideoNotLoaded();
            }
        });
    }

    @Override
    public void getMovieDetails(final long id, final GetMovieDetailsCallback callback) {
        if (!mMemoryCache.containsKey(id)) {
            Log.d(LOG_TAG, "movie is not saved in memory cache, id: " + id);
            callback.onMovieDetailsNotLoaded();
        }
        Movie movie = mMemoryCache.get(id);
        if (movie.getMovieDetails() != null) {
            Log.d(LOG_TAG, "movie has details in memory cache, id: " + id);
            callback.onMovieDetailsLoaded(movie);
            return;
        }
        remoteMovieSource.getMovieDetails(id, new GetMovieDetailsCallback() {
            @Override
            public void onMovieDetailsLoaded(Movie movie) {
                Log.d(LOG_TAG, "movie details was successfully loaded, id: "+id);
                mMemoryCache.get(id).setMovieDetails(movie.getMovieDetails());
                callback.onMovieDetailsLoaded(mMemoryCache.get(id));
            }

            @Override
            public void onMovieDetailsNotLoaded() {
                callback.onMovieDetailsNotLoaded();
            }
        });
    }

    private void refreshCache(List<Movie> movies) {
        if (mMemoryCache == null) {
            mMemoryCache = new LinkedHashMap<>();
        }
        mMemoryCache.clear();
        for (Movie movie : movies) {
            mMemoryCache.put(movie.getId(), movie);
        }
        mCacheIsDirty = false;
    }
}