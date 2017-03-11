package me.elmira.flickster.model.source.remote;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Process;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import cz.msebera.android.httpclient.Header;
import me.elmira.flickster.model.Movie;
import me.elmira.flickster.model.MovieDetails;
import me.elmira.flickster.model.MovieVideo;
import me.elmira.flickster.model.source.MovieDataSource;

/**
 * Created by elmira on 3/7/17.
 */

public class MovieDataSourceRemote implements MovieDataSource {

    public static String LOG_TAG = "MovieDataSourceRemote";
    private static MovieDataSourceRemote instance;

    private static AsyncHttpClient client = null;
    private HandlerThread mHandlerThread;
    private Handler mHandler = null;
    private Handler mUiHandler = null;

    private static final String BASE_URL = "https://api.themoviedb.org/3/";
    private static final String API_KEY = "a07e22bc18f5cb106bfe4cc1f83ad8ed";
    private static final String API_KEY_PARAM = "?api_key=" + API_KEY;

    private static final String NOW_PLAYING_URL = BASE_URL + "movie/now_playing" + API_KEY_PARAM;
    private static final String GET_MOVIE_VIDEO_URL = BASE_URL + "movie/#/videos" + API_KEY_PARAM;
    private static final String GET_MOVIE_DETAILS_URL = BASE_URL + "movie/#" + API_KEY_PARAM;

    private MovieDataSourceRemote() {
        client = new AsyncHttpClient();

        mHandlerThread = new HandlerThread("Movie Handler Thread", Process.THREAD_PRIORITY_LESS_FAVORABLE);
        mHandlerThread.start();

        mHandler = new Handler(mHandlerThread.getLooper());
        mUiHandler = new Handler(Looper.getMainLooper());
    }

    public static MovieDataSourceRemote getInstance() {
        if (instance == null) {
            instance = new MovieDataSourceRemote();
        }
        return instance;
    }

    @Override
    public void loadMovies(final LoadMoviesCallback callback) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                client.get(NOW_PLAYING_URL, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        try {
                            Log.d(LOG_TAG, "Movies are loaded");
                            final List<Movie> result = JsonParser.parseMovies(response);

                            mUiHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    callback.onMoviesLoaded(result);
                                }
                            });

                        } catch (JSONException ex) {
                            Log.e(LOG_TAG, ex.toString());
                            mUiHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    callback.onMoviesNotLoaded();
                                }
                            });
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                        mUiHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                callback.onMoviesNotLoaded();
                            }
                        });
                    }
                });
            }
        });
    }

    @Override
    public void getMovieVideo(final long id, final GetMovieVideoCallback callback) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                client.get(GET_MOVIE_VIDEO_URL.replace("#", "" + id), new JsonHttpResponseHandler() {
                            @Override
                            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                                Log.d(LOG_TAG, "Movie details are loaded!");
                                try {
                                    final MovieVideo video = JsonParser.parseMovieVideo(response);
                                    mUiHandler.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            callback.onMovieVideoLoaded(video);
                                        }
                                    });
                                } catch (JSONException ex) {
                                    Log.e(LOG_TAG, ex.toString());
                                    mUiHandler.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            callback.onMovieVideoNotLoaded();
                                        }
                                    });
                                }
                            }

                            @Override
                            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                                mUiHandler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        callback.onMovieVideoNotLoaded();
                                    }
                                });
                            }
                        }
                );
            }
        });
    }

    @Override
    public void getMovieDetails(final long id, final GetMovieDetailsCallback callback) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                client.get(GET_MOVIE_DETAILS_URL.replace("#", "" + id), new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        Log.d(LOG_TAG, "Movie details was successfully loaded, movie_id: "+id);
                        try {
                            final MovieDetails details = JsonParser.parseMovieDetails(response);
                            final Movie movie = new Movie(details);
                            mUiHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    callback.onMovieDetailsLoaded(movie);
                                }
                            });
                        } catch (JSONException ex) {
                            Log.e(LOG_TAG, ex.toString());
                            mUiHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    callback.onMovieDetailsNotLoaded();
                                }
                            });
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                        Log.e(LOG_TAG, throwable.toString());
                        mUiHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                callback.onMovieDetailsNotLoaded();
                            }
                        });
                    }
                });
            }
        });
    }

    @Override
    public void refreshMovies() {
        //ignore this
    }

    @Override
    public void destroy() {
        if (mHandlerThread != null) {
            mHandlerThread.quit();
        }
    }
}
