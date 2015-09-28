package projects.morrow.runningsongs;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by anne on 9/28/15.
 */
public class LoadSongsFragment extends Fragment {

    private SongLibrary mSongLibrary;
    private RecyclerView mSongRecyclerView;
    private SongAdapter mAdapter;

    public static LoadSongsFragment newInstance() {
        return new LoadSongsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSongLibrary = SongLibrary.get(getActivity());
        mSongLibrary.findSongs(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_load_songs, container, false);

        mSongRecyclerView = (RecyclerView) view.findViewById(R.id.song_recycler_view);
        mSongRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();

        return view;
    }

    public void updateUI() {
        List<Song> songs = mSongLibrary.getSongs();
        mAdapter = new SongAdapter(songs);
        mSongRecyclerView.setAdapter(mAdapter);
    }


    private class SongHolder extends RecyclerView.ViewHolder {

        public TextView mTitleTextView;

        public SongHolder(View itemView) {
            super(itemView);

            mTitleTextView = (TextView) itemView;
        }
    }

    private class SongAdapter extends RecyclerView.Adapter<SongHolder> {

        private List<Song> mSongs;

        public SongAdapter(List<Song> songs) {
            mSongs = songs;
        }

        @Override
        public SongHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater
                    .inflate(android.R.layout.simple_list_item_1, parent, false);
            return new SongHolder(view);
        }

        @Override
        public void onBindViewHolder(SongHolder holder, int position) {
            Song song = mSongs.get(position);
            holder.mTitleTextView.setText(song.getTitle());
        }

        @Override
        public int getItemCount() {
            return mSongs.size();
        }
    }
}
