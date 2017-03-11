package me.elmira.flickster.movies;

import android.content.Context;
import android.content.res.Configuration;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;

import java.lang.ref.WeakReference;
import java.util.List;

import me.elmira.flickster.R;
import me.elmira.flickster.model.Movie;

/**
 * Created by elmira on 3/7/17.
 */

public class MovieAdapter extends BaseAdapter implements ListView.OnItemClickListener {
    private static final String LOG_TAG = "MovieAdapter";

    private List<Movie> items;
    private WeakReference<Context> contextWeakReference;
    private boolean orientationPortrait = true;

    private int imageViewWidthPx = 0;

    private final int REGULAR_MOVIE_TYPE = 0;
    private final int POPULAR_MOVIE_TYPE = 1;

    private OnMovieClickListener mOnClickListener;

    public interface OnMovieClickListener {
        void onMovieClick(Movie movie);
    }

    public MovieAdapter(Context context) {
        contextWeakReference = new WeakReference<Context>(context);
        orientationPortrait = context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;

        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        imageViewWidthPx = displayMetrics.widthPixels;
    }

    public void setItems(List<Movie> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        return items.get(position).isPopular() ? POPULAR_MOVIE_TYPE : REGULAR_MOVIE_TYPE;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getCount() {
        return items == null ? 0 : items.size();
    }

    @Override
    public Object getItem(int position) {
        return items == null ? null : items.size();
    }

    @Override
    public long getItemId(int position) {
        return items.get(position).getId();
    }

    public void setOnClickListener(OnMovieClickListener mOnClickListener) {
        this.mOnClickListener = mOnClickListener;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (mOnClickListener != null) {
            mOnClickListener.onMovieClick(items.get(position));
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        int movieType = getItemViewType(position);
        Movie movie = items.get(position);

        BaseMovieViewHolder viewHolder = null;
        if (convertView == null) {
            if (movieType == REGULAR_MOVIE_TYPE) {
                convertView = LayoutInflater.from(contextWeakReference.get()).inflate(R.layout.item_movie, parent, false);
                viewHolder = new MovieViewHolder(convertView, contextWeakReference.get(), imageViewWidthPx);
            }
            else if (movieType == POPULAR_MOVIE_TYPE) {
                convertView = LayoutInflater.from(contextWeakReference.get()).inflate(R.layout.item_popular_movie, parent, false);
                viewHolder = new PopularMovieViewHolder(convertView, contextWeakReference.get(), orientationPortrait, imageViewWidthPx);
            }
            convertView.setTag(viewHolder);
        }
        else viewHolder = (BaseMovieViewHolder) convertView.getTag();

        viewHolder.bindView(movie, orientationPortrait, imageViewWidthPx);
        return convertView;
    }
}