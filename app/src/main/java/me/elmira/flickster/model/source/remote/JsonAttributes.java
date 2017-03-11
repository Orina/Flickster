package me.elmira.flickster.model.source.remote;

/**
 * Created by elmira on 3/7/17.
 */

public interface JsonAttributes {

    String MOVIES_RESULT = "results";

    interface Movie {
        String ID = "id";
        String POSTER_PATH = "poster_path";
        String OVERVIEW = "overview";
        String TITLE = "original_title";
        String BACKDROP_PATH = "backdrop_path";
        String VOTE_AVERAGE = "vote_average";
    }

    interface MovieVideo {
        String VIDEO_KEY = "key";
        String NAME = "name";
        String SITE = "site";
        String TYPE = "type";
    }

    interface MovieDetails {
        String RELEASE_DATE = "release_date";
        String RUNTIME = "runtime";
        String GENRES = "genres";
    }

    interface MovieGenre {
        String ID = "id";
        String NAME = "name";
    }
}