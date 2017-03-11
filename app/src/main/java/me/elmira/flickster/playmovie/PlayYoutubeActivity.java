package me.elmira.flickster.playmovie;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import me.elmira.flickster.R;
import me.elmira.flickster.model.MovieVideo;
import me.elmira.flickster.model.source.MovieDataSourceImpl;
import me.elmira.flickster.model.source.remote.MovieDataSourceRemote;
import me.elmira.flickster.moviedetail.MovieDetailsActivity;

import static android.content.Intent.FLAG_ACTIVITY_SINGLE_TOP;

/**
 * Created by elmira on 3/9/17.
 */

public class PlayYoutubeActivity extends YouTubeBaseActivity implements PlayYoutubeContract.View {

    private static final String LOG_TAG = "PlayYoutubeActivity";

    private static final String API_KEY = "AIzaSyC-CTMCVB6fPMFmIi2Z0SHaeFcJdg8I2uU";
    public static final String MOVIE_ID = "movie_id";

    private PlayYoutubeContract.Presenter mPresenter;
    private YouTubePlayerView mYouTubePlayerView;

    private long mMovieId = 0;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        mMovieId = getIntent().getLongExtra(MOVIE_ID, 0);
        Log.d(LOG_TAG, "start display movie for id: "+mMovieId);
        if (mMovieId == 0) {
            finish();
            return;
        }
        setContentView(R.layout.activity_play_movie);
        mYouTubePlayerView = (YouTubePlayerView) findViewById(R.id.player);

        mPresenter = new PlayYoutubePresenter(this, MovieDataSourceImpl.getInstance(MovieDataSourceRemote.getInstance()));
        mPresenter.loadMovieVideoUrl(mMovieId);
    }

    @Override
    public void setPresenter(PlayYoutubeContract.Presenter presenter) {
        this.mPresenter = presenter;
    }

    @Override
    public void playYoutubeVideo(final MovieVideo movieVideo) {
        mYouTubePlayerView.initialize(API_KEY,
                new YouTubePlayer.OnInitializedListener() {
                    @Override
                    public void onInitializationSuccess(YouTubePlayer.Provider provider,
                                                        YouTubePlayer youTubePlayer, boolean b) {

                        youTubePlayer.setPlayerStateChangeListener(new YoutubeListener());
                        youTubePlayer.setFullscreen(true);
                        youTubePlayer.loadVideo(movieVideo.getKey());
                    }

                    @Override
                    public void onInitializationFailure(YouTubePlayer.Provider provider,
                                                        YouTubeInitializationResult youTubeInitializationResult) {
                        Log.e(LOG_TAG, youTubeInitializationResult.toString());
                        Toast.makeText(PlayYoutubeActivity.this, "Check that Youtube is installed on the device.", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
    }

    @Override
    public void movieVideoNotLoaded() {
        finish();
    }

    private class YoutubeListener implements YouTubePlayer.PlayerStateChangeListener {

        public String LOG_TAG = "PlayerStateChange";

        @Override
        public void onLoading() {

        }

        @Override
        public void onLoaded(String s) {

        }

        @Override
        public void onAdStarted() {
        }

        @Override
        public void onVideoStarted() {
            Log.d(LOG_TAG, "onVideoStarted()");
        }

        @Override
        public void onVideoEnded() {
            Log.d(LOG_TAG, "onVideoEnded()");

            Intent intent = new Intent(getApplicationContext(), MovieDetailsActivity.class);
            intent.putExtra(MovieDetailsActivity.MOVIE_ID_EXTRA, mMovieId);
            intent.setFlags(FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);

            finish();
        }

        @Override
        public void onError(YouTubePlayer.ErrorReason errorReason) {
            Log.d(LOG_TAG, "onError" + errorReason);
            finish();
        }
    }
}
