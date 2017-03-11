package me.elmira.flickster.movies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.elmira.flickster.R;
import me.elmira.flickster.model.Movie;
import me.elmira.flickster.model.source.MovieDataSourceImpl;
import me.elmira.flickster.model.source.remote.MovieDataSourceRemote;
import me.elmira.flickster.moviedetail.MovieDetailsActivity;
import me.elmira.flickster.playmovie.PlayYoutubeActivity;

import static android.content.Intent.FLAG_ACTIVITY_SINGLE_TOP;

public class MoviesActivity extends AppCompatActivity implements MovieContract.View {

    private static final String LOG_TAG = "MoviesActivity";

    private MovieContract.Presenter mMoviePresenter;

    private MovieAdapter mMovieAdapter;

    @BindView(R.id.lvMovies)
    ListView movieList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_movies);
        ButterKnife.bind(this);

        mMoviePresenter = new MoviePresenter(this, MovieDataSourceImpl.getInstance(MovieDataSourceRemote.getInstance()));

        mMovieAdapter = new MovieAdapter(this);
        mMovieAdapter.setOnClickListener(mMoviePresenter);

        movieList.setAdapter(mMovieAdapter);
        movieList.setOnItemClickListener(mMovieAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMoviePresenter.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void setPresenter(MovieContract.Presenter presenter) {
        mMoviePresenter = presenter;
    }

    @Override
    public void showMovies(List<Movie> movies) {
        Log.d(LOG_TAG, "There are loaded movies: " + movies.size());
        mMovieAdapter.setItems(movies);
    }

    @Override
    public void showNoMovies() {

    }

    @Override
    public void showLoading(boolean active) {

    }

    @Override
    public void showMovieDetails(long id) {
        Intent intent = new Intent(this, MovieDetailsActivity.class);
        intent.putExtra(MovieDetailsActivity.MOVIE_ID_EXTRA, id);
        intent.setFlags(FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }

    @Override
    public void playMovieVideo(long id) {
        Intent playMovieIntent = new Intent(this, PlayYoutubeActivity.class);
        playMovieIntent.putExtra(PlayYoutubeActivity.MOVIE_ID, id);
        startActivity(playMovieIntent);
    }
}
