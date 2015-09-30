package projects.morrow.runningsongs;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by anne on 9/28/15.
 */
public class LoadSongsFragment extends Fragment {

    private SongLibrary mSongLibrary;
    private RecyclerView mSongRecyclerView;
    private SongAdapter mAdapter;

    private Song mCurrentSong;

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


    private class SongHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView mTitleTextView;
        private Button mPlayButton;
        private Song mSong;

        public SongHolder(View itemView) {
            super(itemView);

            mTitleTextView = (TextView) itemView.findViewById(R.id.list_item_title_view);
            mPlayButton = (Button) itemView.findViewById(R.id.list_item_play_button);
            itemView.setOnClickListener(this);
            mPlayButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mSongLibrary.getMediaPlayer() == null) {
                        switchTo(mSong);
                    } else if (mCurrentSong.equals(mSong)) {
                        if (mSongLibrary.getMediaPlayer().isPlaying()) {
                            pause();
                        } else {
                            mSongLibrary.getMediaPlayer().start();
                        }
                    } else {
                        switchTo(mSong);
                    }
                }
            });
        }

        public void bindSong(Song song) {
            mSong = song;
            mTitleTextView.setText(song.getTitle());
        }

        @Override
        public void onClick(View v) {
            Intent intent = SongInfoFragment.newIntent(getActivity(), mSong.getID());
            startActivity(intent);
        }

        private void pause() {
            mSongLibrary.getMediaPlayer().pause();
        }

        private void switchTo(Song song) {
            mSongLibrary.switchTo(song);
            mCurrentSong = song;
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
                    .inflate(R.layout.list_item_song, parent, false);
            return new SongHolder(view);
        }

        @Override
        public void onBindViewHolder(SongHolder holder, int position) {
            Song song = mSongs.get(position);
            holder.bindSong(song);
        }

        @Override
        public int getItemCount() {
            return mSongs.size();
        }
    }
}
