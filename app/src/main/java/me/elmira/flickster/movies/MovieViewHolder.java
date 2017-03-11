package me.elmira.flickster.movies;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.lang.ref.WeakReference;

import butterknife.BindView;
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;
import me.elmira.flickster.R;
import me.elmira.flickster.model.Movie;

/**
 * Created by elmira on 3/7/17.
 */

public class MovieViewHolder extends BaseMovieViewHolder {
    @BindView(R.id.movie_image)
    ImageView imageView;

    @BindView(R.id.movie_title)
    TextView titleView;

    @BindView(R.id.movie_overview)
    TextView overviewView;

    private Drawable placeholder;

    public MovieViewHolder(View container, Context context, int imageViewWidthPx) {
        super(container, context);
        contextWeakReference = new WeakReference<Context>(context);

        imageViewWidthPx *= 0.5;
        imageView.getLayoutParams().width = imageViewWidthPx;

        placeholder = context.getResources().getDrawable(R.drawable.ic_image_white_24dp);
        placeholder.mutate().setColorFilter(context.getResources().getColor(R.color.overlay_color), PorterDuff.Mode.SRC_IN);
    }

    @Override
    public void bindView(Movie movie, boolean orientationPortrait, int imageViewWidthPx) {
        titleView.setText(movie.getTitle());
        overviewView.setText(movie.getOverview());

        String imageUri = "";
        if (orientationPortrait) {
            imageUri = movie.getPosterImageUrl();
        }
        else {
            imageUri = movie.getBackdropImageUrl();
        }

        if (!"".equals(imageUri)) {
            Picasso picasso = Picasso.with(contextWeakReference.get());
            picasso.setIndicatorsEnabled(false);
            picasso.load(imageUri)
                    .resize(imageViewWidthPx, 0)
                    .noPlaceholder()
                    .transform(new RoundedCornersTransformation(20, 10))
                    .into(imageView);
        }
    }
}
