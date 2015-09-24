package projects.morrow.runningsongs;

import android.media.MediaPlayer;
import android.net.Uri;

/**
 * Created by anne on 9/23/15.
 */
public class Song {
    private String mPath;
    private String mTitle;
    private String mArtist;
    private String mAlbum;
    private int mDuration;
    private int mBPM;

    public String getPath() {
        return mPath;
    }

    public void setPath(String path) {
        mPath = path;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getArtist() {
        return mArtist;
    }

    public void setArtist(String artist) {
        mArtist = artist;
    }

    public String getAlbum() {
        return mAlbum;
    }

    public void setAlbum(String album) {
        mAlbum = album;
    }

    public int getDuration() {
        return mDuration;
    }

    public void setDuration(int duration) {
        mDuration = duration;
    }
}