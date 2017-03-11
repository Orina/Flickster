package me.elmira.flickster.moviedetail;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;
import me.elmira.flickster.R;
import me.elmira.flickster.model.Movie;
import me.elmira.flickster.model.source.MovieDataSourceImpl;
import me.elmira.flickster.model.source.remote.MovieDataSourceRemote;
import me.elmira.flickster.playmovie.PlayYoutubeActivity;

/**
 * Created by elmira on 3/9/17.
 */

public class MovieDetailsActivity extends AppCompatActivity implements MovieDetailsContract.View {

    private static final String LOG_TAG = "MovieDetailsActivity";
    public static final String MOVIE_ID_EXTRA = "movie_id";

    private MovieDetailsContract.Presenter mPresenter;

    @BindView(R.id.movie_title)
    TextView mMovieTitleTextView;

    @BindView(R.id.movie_overview)
    TextView mMovieOverviewTextView;

    @BindView(R.id.release_date_text)
    TextView mReleaseDateTextView;

    @BindView(R.id.genre_text)
    TextView mGenreTextView;

    @BindView(R.id.running_time_text)
    TextView mRunningTimeTextView;

    @BindView(R.id.movie_image)
    ImageView mImageView;

    @BindView(R.id.video_image)
    ImageView mVideoImage;

    private Movie mMovie;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        long movieId = getIntent().getLongExtra(MOVIE_ID_EXTRA, 0);
        if (movieId == 0) {
            finish();
            return;
        }
        setContentView(R.layout.activity_movie_details);
        ButterKnife.bind(this);

        mVideoImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMovieVideo();
            }
        });

        mPresenter = new MovieDetailsPresenter(this, MovieDataSourceImpl.getInstance(MovieDataSourceRemote.getInstance()));
        mPresenter.getMovieDetails(movieId);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.d(LOG_TAG, "onNewIntent()");

        long movieId = getIntent().getLongExtra(MOVIE_ID_EXTRA, 0);
        if (movieId > 0 && mPresenter != null) {
            mPresenter.getMovieDetails(movieId);
        }
    }

    @Override
    public void setPresenter(MovieDetailsContract.Presenter presenter) {
        this.mPresenter = presenter;
    }

    @Override
    public void showMovieDetails(Movie movie) {
        Log.d(LOG_TAG, "movie were loaded from presenter, showMovieDetails()");
        this.mMovie = movie;

        mMovieTitleTextView.setText(movie.getTitle());
        mMovieOverviewTextView.setText(movie.getOverview());
        mReleaseDateTextView.setText(movie.getMovieDetails().getReleaseDate());
        mGenreTextView.setText(movie.getMovieDetails().getGenresAsString());
        mRunningTimeTextView.setText(movie.getMovieDetails().getRuntime() + " min");

        loadImages();
    }

    private void loadImages() {
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        int fullImageViewWidthPx = displayMetrics.widthPixels;
        int imageViewWidthPx = (int) (displayMetrics.widthPixels * 0.3f);

        mVideoImage.getLayoutParams().width = fullImageViewWidthPx;

        if (mMovie == null) return;
        Picasso picasso = Picasso.with(this);

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            mImageView.getLayoutParams().width = imageViewWidthPx;

            picasso.setIndicatorsEnabled(false);
            picasso.load(mMovie.getPosterImageUrl())
                    .resize(imageViewWidthPx, 0)
                    .noPlaceholder()
                    .transform(new RoundedCornersTransformation(12, 10))
                    .into(mImageView);

            picasso.load(mMovie.getBackdropImageUrl())
                    .resize(fullImageViewWidthPx, 0)
                    .noPlaceholder()
                    .into(mVideoImage);
        }
        else {
            mImageView.setVisibility(View.GONE);

            mVideoImage.getLayoutParams().height = displayMetrics.heightPixels;
            picasso.load(mMovie.getBackdropImageUrl())
                    .resize(fullImageViewWidthPx, displayMetrics.heightPixels)
                    .noPlaceholder()
                    .into(mVideoImage);
        }
    }

    @Override
    public void showMovieDetailsNotLoaded() {
        finish();
    }

    private void showMovieVideo() {
        if (mMovie == null) return;
        Intent playMovieIntent = new Intent(this, PlayYoutubeActivity.class);
        playMovieIntent.putExtra(PlayYoutubeActivity.MOVIE_ID, mMovie.getId());
        startActivity(playMovieIntent);
    }
}