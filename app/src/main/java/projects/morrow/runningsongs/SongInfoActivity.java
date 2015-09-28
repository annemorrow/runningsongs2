package projects.morrow.runningsongs;

import android.support.v4.app.Fragment;

/**
 * Created by anne on 9/28/15.
 */
public class SongInfoActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return SongInfoFragment.newInstance();
    }
}
