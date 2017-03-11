package me.elmira.flickster.model.source.remote;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import me.elmira.flickster.model.Movie;
import me.elmira.flickster.model.MovieDetails;
import me.elmira.flickster.model.MovieGenre;
import me.elmira.flickster.model.MovieVideo;

/**
 * Created by elmira on 3/7/17.
 */

public class JsonParser {

    public static List<Movie> parseMovies(JSONObject response) throws JSONException {
        List<Movie> result = new ArrayList<>();
        if (response == null || response.length() == 0) return result;
        JSONArray jsonArray = response.getJSONArray(JsonAttributes.MOVIES_RESULT);
        Movie.Builder movieBuilder = new Movie.Builder();

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            result.add(movieBuilder.id(jsonObject.getInt(JsonAttributes.Movie.ID)).
                    title(jsonObject.getString(JsonAttributes.Movie.TITLE)).
                    overview(jsonObject.getString(JsonAttributes.Movie.OVERVIEW)).
                    backdropPath(jsonObject.getString(JsonAttributes.Movie.BACKDROP_PATH)).
                    posterPath(jsonObject.getString(JsonAttributes.Movie.POSTER_PATH)).
                    voteAverage(jsonObject.getDouble(JsonAttributes.Movie.VOTE_AVERAGE)).build());
            movieBuilder.clear();

        }
        return result;
    }

    public static MovieVideo parseMovieVideo(JSONObject response) throws JSONException {
        if (response == null || response.length() == 0) return null;
        JSONArray jsonArray = response.getJSONArray(JsonAttributes.MOVIES_RESULT);

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            if ("YouTube".equalsIgnoreCase(jsonObject.getString(JsonAttributes.MovieVideo.SITE))) {
                return new MovieVideo(jsonObject.getString(JsonAttributes.MovieVideo.VIDEO_KEY),
                        jsonObject.getString(JsonAttributes.MovieVideo.NAME),
                        jsonObject.getString(JsonAttributes.MovieVideo.SITE),
                        jsonObject.getString(JsonAttributes.MovieVideo.TYPE));
            }

        }
        return null;
    }

    public static MovieDetails parseMovieDetails(JSONObject response) throws JSONException {
        if (response == null || response.length() == 0) return null;

        return new MovieDetails(response.getString(JsonAttributes.MovieDetails.RELEASE_DATE),
                response.getInt(JsonAttributes.MovieDetails.RUNTIME),
                parseMovieGenres(response.getJSONArray(JsonAttributes.MovieDetails.GENRES)));

    }

    public static List<MovieGenre> parseMovieGenres(JSONArray jsonArray) throws JSONException {
        List<MovieGenre> list = new ArrayList<>();
        if (jsonArray == null || jsonArray.length() == 0) return list;

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            list.add(new MovieGenre(jsonObject.getInt(JsonAttributes.MovieGenre.ID),
                    jsonObject.getString(JsonAttributes.MovieGenre.NAME)));
        }
        return list;
    }
}
