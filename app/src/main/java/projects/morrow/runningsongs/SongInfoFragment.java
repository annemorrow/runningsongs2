package projects.morrow.runningsongs;

import android.support.v4.app.Fragment;

/**
 * Created by anne on 9/28/15.
 */
public class SongInfoFragment extends Fragment {

    Song mSong;

    public static SongInfoFragment newInstance() {
        return new SongInfoFragment();
    }

    public Song getSong() {
        return mSong;
    }

    public void setSong(Song song) {
        mSong = song;
    }
}
