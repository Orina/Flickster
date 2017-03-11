package me.elmira.flickster.model;

/**
 * Created by elmira on 3/10/17.
 */

public class MovieGenre {
    private long mId;
    private String mName;

    public MovieGenre(long mId, String mName) {
        this.mId = mId;
        this.mName = mName;
    }

    public long getmId() {
        return mId;
    }

    public String getmName() {
        return mName;
    }
}
