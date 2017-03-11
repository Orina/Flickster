package me.elmira.flickster.movies;

import android.content.Context;
import android.view.View;

import java.lang.ref.WeakReference;

import butterknife.ButterKnife;
import me.elmira.flickster.model.Movie;

/**
 * Created by elmira on 3/9/17.
 */

public abstract class BaseMovieViewHolder {

    WeakReference<Context> contextWeakReference;

    public BaseMovieViewHolder(View container, Context context) {
        ButterKnife.bind(this, container);
        contextWeakReference = new WeakReference<Context>(context);
    }

    public abstract void bindView(Movie movie, boolean orientationPortrait, int imageViewWidthPx);
}
