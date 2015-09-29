package projects.morrow.runningsongs;

import android.media.MediaPlayer;
import android.net.Uri;

import java.util.UUID;

/**
 * Created by anne on 9/23/15.
 */
public class Song {
    private UUID mID;
    private String mPath;
    private String mTitle;
    private String mArtist;
    private String mAlbum;
    private int mDuration;
    private int mBPM;
    private boolean mUseInApp;

    public Song() {
        mID = UUID.randomUUID();
    }

    public UUID getID() {
        return mID;
    }

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

    public int getBPM() {
        return mBPM;
    }

    public void setBPM(int BPM) {
        mBPM = BPM;
    }

    public boolean isUseInApp() {
        return mUseInApp;
    }

    public void setUseInApp(boolean useInApp) {
        mUseInApp = useInApp;
    }
}
