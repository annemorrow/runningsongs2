package projects.morrow.runningsongs;

import android.os.Bundle;
import android.support.v4.app.Fragment;

/**
 * Created by anne on 9/28/15.
 */
public class LoadSongsFragment extends Fragment {

    public static LoadSongsFragment newInstance() {
        return new LoadSongsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SongLibrary songLibrary = new SongLibrary();
        songLibrary.findSongs(getActivity());
    }
}
