package projects.morrow.runningsongs;

import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


public class LoadSongsActivity extends SingleFragmentActivity {


    @Override
    protected Fragment createFragment() {
        return LoadSongsFragment.newInstance();
    }
}
