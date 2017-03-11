package me.elmira.flickster.model;

/**
 * Created by elmira on 3/10/17.
 */

public class MovieVideo {
    private String mKey;
    private String mName;
    private String mSite;
    private String mType;

    public MovieVideo(String mKey, String mName, String mSite, String mType) {
        this.mKey = mKey;
        this.mName = mName;
        this.mSite = mSite;
        this.mType = mType;
    }

    public String getKey() {
        return mKey;
    }

    public String getName() {
        return mName;
    }

    public String getSite() {
        return mSite;
    }

    public String getType() {
        return mType;
    }
}
