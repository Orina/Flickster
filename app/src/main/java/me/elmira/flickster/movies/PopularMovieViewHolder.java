package me.elmira.flickster.movies;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;
import me.elmira.flickster.R;
import me.elmira.flickster.model.Movie;

/**
 * Created by elmira on 3/9/17.
 */

public class PopularMovieViewHolder extends BaseMovieViewHolder {

    @BindView(R.id.movie_image)
    ImageView imageView;

    private Drawable placeholder;

    private TextView titleView;
    private TextView overviewView;

    public PopularMovieViewHolder(View container, Context context, boolean orientationPortrait, int imageViewWidthPx) {
        super(container, context);

        placeholder = context.getResources().getDrawable(R.drawable.ic_image_white_24dp);
        placeholder.mutate().setColorFilter(context.getResources().getColor(R.color.overlay_color), PorterDuff.Mode.SRC_IN);

        if (!orientationPortrait) {
            imageView.getLayoutParams().width = (int) (imageViewWidthPx * 0.65);
            titleView = ButterKnife.findById(container, R.id.movie_title);
            overviewView = ButterKnife.findById(container, R.id.movie_overview);
        }
    }

    @Override
    public void bindView(Movie movie, boolean orientationPortrait, int imageViewWidthPx) {
        if (titleView != null) titleView.setText(movie.getTitle());
        if (overviewView != null) overviewView.setText(movie.getOverview());

        String imageUri = "http://image.tmdb.org/t/p/w780" + movie.getBackdropPath();

        if (!"".equals(imageUri)) {
            Picasso picasso = Picasso.with(contextWeakReference.get());
            picasso.setIndicatorsEnabled(false);
            picasso.load(imageUri)
                    .noPlaceholder()
                    .resize(imageViewWidthPx, 0)
                    .transform(new RoundedCornersTransformation(25, 10))
                    .into(imageView)
            ;
        }
    }
}
