package projects.morrow.runningsongs;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import java.util.UUID;

/**
 * Created by anne on 9/28/15.
 */
public class SongInfoFragment extends Fragment {

    public static final String EXTRA_SONG_ID = "projects.morrow.runningsongs.song_id";

    public static Intent newIntent(Context packageContext, UUID songId) {
        Intent intent = new Intent(packageContext, SongInfoActivity.class);
        intent.putExtra(EXTRA_SONG_ID, songId);
        return intent;
    }

    Song mSong;

    TextView mTitleTextView;
    CheckBox mIncludeCheckbox;
    EditText mBPMEditText;

    public static SongInfoFragment newInstance() {
        return new SongInfoFragment();
    }

    @Override
    public void onPause() {
        super.onPause();

        SongLibrary.get(getActivity()).updateSong(mSong);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UUID songId = (UUID) getActivity().getIntent().getSerializableExtra(SongInfoFragment.EXTRA_SONG_ID);
        mSong = SongLibrary.get(getActivity()).getSong(songId);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.song_info_fragment, parent, false);


        mTitleTextView = (TextView) v.findViewById(R.id.song_title);
        mTitleTextView.setText(mSong.getTitle());

        mIncludeCheckbox = (CheckBox) v.findViewById(R.id.include_in_app);
        mIncludeCheckbox.setChecked(mSong.isUsedInApp());

        mIncludeCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mSong.setUseInApp(isChecked);
            }
        });

        mBPMEditText = (EditText) v.findViewById(R.id.beats_per_minute);
        if (mSong.getBPM() != 0) {
            mBPMEditText.setText(Integer.toString(mSong.getBPM()));
        }
        mBPMEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int newBPM = Integer.parseInt(s.toString());
                mSong.setBPM(newBPM);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        return v;
    }
}
